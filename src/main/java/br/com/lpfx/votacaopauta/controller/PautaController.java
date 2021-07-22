package br.com.lpfx.votacaopauta.controller;

import br.com.lpfx.votacaopauta.dto.RetornoValidacaoCPF;
import br.com.lpfx.votacaopauta.dto.VotoPautaDTO;
import br.com.lpfx.votacaopauta.exception.BadRequestException;
import br.com.lpfx.votacaopauta.exception.InternalServerErrorException;
import br.com.lpfx.votacaopauta.model.Pauta;
import br.com.lpfx.votacaopauta.model.VotoPauta;
import br.com.lpfx.votacaopauta.repository.PautaRepository;
import br.com.lpfx.votacaopauta.repository.UsuarioRepository;
import br.com.lpfx.votacaopauta.repository.VotoPautaRepository;
import br.com.lpfx.votacaopauta.service.SistemaExterno;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("Pauta")
@Transactional
public class PautaController {
    private final Logger logger = LoggerFactory.getLogger(PautaController.class);

    private final PautaRepository pautaRepository;
    private final UsuarioRepository usuarioRepository;
    private final VotoPautaRepository votoPautaRepository;
    private final SistemaExterno sistemaExterno;

    @GetMapping("/pautas")
    public List<Pauta> todas(){
        logger.info("Listando todas as pautas");
        return pautaRepository.findAll();
    }

    @PostMapping("/pautas")
    public Pauta novaPauta(@RequestBody Pauta pauta){
        logger.info("Inserindo nova pauta");

        // Se não tiver fim, utilizar padrão
        if(pauta.getFim() == null)
            pauta.setFim(pauta.getInicio().plus(Pauta.PADRAO_TEMPO_FIM_NULO));

        return pautaRepository.save(pauta);
    }

    @PutMapping("/pautas/{id}")
    public Pauta atualizaPauta(@RequestBody Pauta novaPauta, @PathVariable Long id){
        logger.info("Efetuando consulta da pauta " + id);
        return pautaRepository.findById(id)
                .map(pauta -> {
                    pauta.setDescricao(novaPauta.getDescricao());
                    pauta.setInicio(novaPauta.getInicio());
                    pauta.setFim(novaPauta.getFim());
                    return pautaRepository.save(pauta);
                }).orElseThrow();
    }

    @PostMapping("pautas/{id}/voto")
    public ResponseEntity<VotoPauta> votarPauta(@Valid @RequestBody VotoPautaDTO votoPautaDTO){
        try {
            RetornoValidacaoCPF retornoValidacaoCPF = sistemaExterno.obterSituacao(votoPautaDTO.getCpf());

            if(!RetornoValidacaoCPF.PODE_VOTAR.equals(retornoValidacaoCPF.getStatus())){
                logger.error("Erro na requisição do serviço externo %s");
                throw new BadRequestException(retornoValidacaoCPF.getStatus());
            }
        } catch (WebClientResponseException webClientResponseException){
            if(webClientResponseException.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new BadRequestException("CPF inválido");

            throw new InternalServerErrorException("Erro genérico na consulta externa");
        }

        final Pauta pauta = pautaRepository.getById(votoPautaDTO.getIdPauta());

        if(pauta.getFim().isBefore(LocalDateTime.now()))
            throw new BadRequestException("Pauta já encerrada");

        VotoPauta votoPauta = new VotoPauta();

        votoPauta.setVoto(votoPautaDTO.getVoto());
        votoPauta.setPauta(pauta);
        votoPauta.setUsuario(usuarioRepository.buscaOuCria(votoPautaDTO.getCpf()));

        return ResponseEntity.ok(votoPautaRepository.save(votoPauta));
    }

}
