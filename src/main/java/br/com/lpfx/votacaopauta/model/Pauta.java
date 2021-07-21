package br.com.lpfx.votacaopauta.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;


@Entity
@Data
public class Pauta {
    public static final Duration PADRAO_TEMPO_FIM_NULO = Duration.ofMinutes(1);

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false) private String descricao;
    @Column(nullable = false) private LocalDateTime inicio;
    @Column(nullable = false) private LocalDateTime fim;

    @Column(nullable = false) private Boolean contabilizadoVotos = false;

    private Long votosSim;
    private Long votosNao;
}
