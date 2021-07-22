package br.com.lpfx.votacaopauta.service;

import br.com.lpfx.votacaopauta.dto.ResultadoVotacaoDTO;
import br.com.lpfx.votacaopauta.kafka.KafkaSender;
import br.com.lpfx.votacaopauta.model.Pauta;
import br.com.lpfx.votacaopauta.repository.PautaRepository;
import br.com.lpfx.votacaopauta.repository.VotoPautaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContabilizaVotosPautaService {
    Logger logger = LoggerFactory.getLogger(ContabilizaVotosPautaService.class);

    private final PautaRepository pautaRepository;
    private final VotoPautaRepository votoPautaRepository;
    private final KafkaSender kafkaSender;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Pauta contabilizaVotos(Long idPauta){
        logger.info("Contabilizando pauta de ID " + idPauta);

        Pauta pauta = pautaRepository.getById(idPauta);

        if (pauta.getFim().isAfter(LocalDateTime.now()))
            throw new IllegalStateException("Pauta ainda permite votos");

        if (pauta.getContabilizadoVotos())
            throw new IllegalStateException("Pauta já contabilizada");

        ResultadoVotacaoDTO resultado = votoPautaRepository.obterResultado(pauta.getId());

        pauta.setContabilizadoVotos(true);
        pauta.setVotosSim(resultado.qtdSim);
        pauta.setVotosNao(resultado.qtdNao);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                kafkaSender.votacaoFinalizada(pauta);
            }
        });

        return pautaRepository.save(pauta);
    }
}
