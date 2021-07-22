package br.com.lpfx.votacaopauta.service;

import br.com.lpfx.votacaopauta.dto.RetornoValidacaoCPF;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public interface SistemaExterno {
    RetornoValidacaoCPF obterSituacao(String cpf) throws WebClientResponseException;
}
