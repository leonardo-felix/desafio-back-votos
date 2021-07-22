package br.com.lpfx.votacaopauta.schedule;

import br.com.lpfx.votacaopauta.repository.PautaRepository;
import br.com.lpfx.votacaopauta.service.ContabilizaVotosPautaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Agendamentos {
    Logger logger = LoggerFactory.getLogger(Agendamentos.class);

    private final PautaRepository pautaRepository;

    private final ContabilizaVotosPautaService contabilizaVotosPautaService;

    //rodar a cada minuto
    @Scheduled(fixedDelay = 60_000)
    public void contabilizarPauta(){
        logger.debug("Efetuando consulta se há Pauta para contabilizar");

        pautaRepository.idsPautasFinalizadasParaContabilizar()
                .forEach(contabilizaVotosPautaService::contabilizaVotos);
    }
}
