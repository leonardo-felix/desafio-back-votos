package br.com.lpfx.votacaopauta.kafka;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EnumTopicos {
    VOTACA_FINALIZADA("VOTACAO_FINALIZADA");

    public final String topico;
}
