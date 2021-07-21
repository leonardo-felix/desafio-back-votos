package br.com.lpfx.votacaopauta.controller;

import br.com.lpfx.votacaopauta.dto.VotoPautaDTO;
import br.com.lpfx.votacaopauta.exception.BadRequestException;
import br.com.lpfx.votacaopauta.model.Pauta;
import br.com.lpfx.votacaopauta.model.VotoPauta;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PautaControllerTest {

    @Autowired
    private PautaController pautaController;

    private static Pauta pautaTeste(){
        final LocalDateTime agora = LocalDateTime.now();
        final LocalDateTime fim = agora.plusHours(12);

        Pauta pauta = new Pauta();
        pauta.setDescricao("Teste pauta");
        pauta.setInicio(agora);
        pauta.setFim(fim);

        return pauta;
    }

    @Test
    void todas_resultadoLista() {
        List<Pauta> resultado = pautaController.todas();

        assertNotNull(resultado, "Não deve ficar nulo, no mínimo trazer uma lista vazia");
    }

    @Test
    void novaPauta_Inserir() {
        Pauta pauta = pautaTeste();
        pauta.setDescricao("Teste pauta cadastrada corretamente");

        Pauta pautaCriada = pautaController.novaPauta(pauta);

        assertNotNull(pautaCriada);
        assertNotNull(pautaCriada.getId());
        assertEquals(pautaCriada.getDescricao(), pauta.getDescricao());
        assertEquals(pautaCriada.getInicio(),    pauta.getInicio());
        assertEquals(pautaCriada.getFim(),       pauta.getFim());
    }

    @Test
    void novaPauta_padraoSeFimNaoEspecificado() {
        Pauta pauta = pautaTeste();
        pauta.setDescricao("Teste pauta cadastrada corretamente se o fim for nulo");
        pauta.setFim(null);

        LocalDateTime fim = pauta.getInicio().plus(Pauta.PADRAO_TEMPO_FIM_NULO);

        Pauta pautaCriada = pautaController.novaPauta(pauta);

        assertNotNull(pautaCriada);
        assertNotNull(pautaCriada.getId());
        assertEquals(pautaCriada.getDescricao(), pauta.getDescricao());
        assertEquals(pautaCriada.getInicio(),    pauta.getInicio());
        assertEquals(pautaCriada.getFim(),       fim, "Não está sendo respeitado o tempo padrão se nulo");
    }

    @Test
    void atualizaPauta() {
        Pauta pauta = pautaTeste();

        final Long idCriado = pautaController.novaPauta(pauta).getId();

        pauta.setDescricao("Teste edição");

        Pauta pautaAtualizada = pautaController.atualizaPauta(pauta, idCriado);
        assertEquals(pautaAtualizada.getDescricao(), pauta.getDescricao());
    }

    @Test
    void doVotarPautaCPFInvalido(){
        assertThrows(BadRequestException.class, () -> {
            Pauta pauta = pautaTeste();
            pauta.setDescricao("Teste voto CPF Invalido");

            final Long idCriado = pautaController.novaPauta(pauta).getId();

            VotoPautaDTO votoPautaDTO = new VotoPautaDTO();
            votoPautaDTO.setCpf("02104110156");
            votoPautaDTO.setVoto(true);
            votoPautaDTO.setIdPauta(idCriado);

            pautaController.votarPauta(votoPautaDTO);
        });
    }

    @Test
    void votarPautaOK(){
        Pauta pauta = pautaTeste();
        pauta.setDescricao("Teste voto");

        final Long idCriado = pautaController.novaPauta(pauta).getId();

        VotoPautaDTO votoPautaDTO = new VotoPautaDTO();
        votoPautaDTO.setCpf("02104110157");
        votoPautaDTO.setVoto(true);
        votoPautaDTO.setIdPauta(idCriado);

        // resultado esperado para um CPF válido é:... voto ok
        try {
            ResponseEntity<VotoPauta> respostaVotarPauta = pautaController.votarPauta(votoPautaDTO);

            //voto ok
            assertEquals(HttpStatus.OK, respostaVotarPauta.getStatusCode());

            VotoPauta votoPauta = respostaVotarPauta.getBody();

            assertNotNull(votoPauta);
            assertEquals(votoPauta.getUsuario().getCpf(), votoPautaDTO.getCpf());
            assertEquals(votoPauta.getPauta().getId(), votoPautaDTO.getIdPauta());
            assertEquals(votoPauta.getVoto(), votoPauta.getVoto());
        } catch (BadRequestException exception){
            //Erro externo por não poder votar
            assert exception.getMessage().contains("UNABLE_TO_VOTE");
        }
    }
}