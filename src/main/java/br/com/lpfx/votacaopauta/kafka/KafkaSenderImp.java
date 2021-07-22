package br.com.lpfx.votacaopauta.kafka;

import br.com.lpfx.votacaopauta.model.Pauta;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class KafkaSenderImp implements KafkaSender {
    private final KafkaTemplate<String, Pauta> pautaKafkaTemplate;

    @Override
    public void votacaoFinalizada(final Pauta pauta){
        pautaKafkaTemplate.send(EnumTopicos.VOTACA_FINALIZADA.topico, pauta);
    }
}
