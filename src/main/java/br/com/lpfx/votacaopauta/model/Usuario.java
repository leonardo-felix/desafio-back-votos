package br.com.lpfx.votacaopauta.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String cpf;
}
