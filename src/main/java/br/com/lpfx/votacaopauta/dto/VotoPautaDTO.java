package br.com.lpfx.votacaopauta.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class VotoPautaDTO {
    @Min(11)
    @Max(11)
    private String cpf;

    @NotNull
    private Long idPauta;

    @NotNull
    private Boolean voto;
}
