package br.com.lpfx.votacaopauta.schedule;

import br.com.lpfx.votacaopauta.repository.PautaRepository;
import br.com.lpfx.votacaopauta.service.ContabilizaVotosPautaService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Agendamentos {
    private final PautaRepository pautaRepository;

    private final ContabilizaVotosPautaService contabilizaVotosPautaService;

    @Scheduled(fixedDelay = 1000)
    public void contabilizarPauta(){
        pautaRepository.idsPautasFinalizadasParaContabilizar()
                .forEach(contabilizaVotosPautaService::contabilizaVotos);
    }
}
