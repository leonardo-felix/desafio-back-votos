package br.com.lpfx.votacaopauta.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"pauta_id", "usuario_id"})
})
@Data
public class VotoPauta {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Pauta pauta;

    @ManyToOne(optional = false)
    private Usuario usuario;

    @Column(nullable = false)
    private Boolean voto;
}
