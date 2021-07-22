package br.com.lpfx.votacaopauta.kafka;

import br.com.lpfx.votacaopauta.model.Pauta;

public interface KafkaSender {
    void votacaoFinalizada(Pauta pauta);
}
