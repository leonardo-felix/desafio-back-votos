package br.com.lpfx.votacaopauta.dto;

import lombok.Data;

@Data
public class RetornoValidacaoCPF {
    public static String PODE_VOTAR = "ABLE_TO_VOTE";

    private String status;
}
