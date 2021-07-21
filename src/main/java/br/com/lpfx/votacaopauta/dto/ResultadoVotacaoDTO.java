package br.com.lpfx.votacaopauta.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResultadoVotacaoDTO {
    public final Long idPauta;
    public final Long qtdSim;
    public final Long qtdNao;
}
