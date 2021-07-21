package br.com.lpfx.votacaopauta.kafka;

import br.com.lpfx.votacaopauta.model.Pauta;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSender {
    private final KafkaTemplate<String, Pauta> pautaKafkaTemplate;

    public void votacaoFinalizada(final Pauta pauta){
        pautaKafkaTemplate.send(EnumTopicos.VOTACA_FINALIZADA.topico, pauta);
    }

}
