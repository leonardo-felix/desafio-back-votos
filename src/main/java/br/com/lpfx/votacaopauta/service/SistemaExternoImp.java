package br.com.lpfx.votacaopauta.service;

import br.com.lpfx.votacaopauta.dto.RetornoValidacaoCPF;
import br.com.lpfx.votacaopauta.exception.BadRequestException;
import br.com.lpfx.votacaopauta.exception.InternalServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Profile("!test")
@Component
public class SistemaExternoImp implements SistemaExterno{
    private final Logger logger = LoggerFactory.getLogger(SistemaExternoImp.class);

    private final WebClient client = WebClient.create("https://user-info.herokuapp.com");

    @Override
    public RetornoValidacaoCPF obterSituacao(String cpf) throws WebClientResponseException {
        RetornoValidacaoCPF retornoValidacaoCPF = this.client.get()
                .uri("users/{CPF}", cpf)
                .retrieve()
                .bodyToMono(RetornoValidacaoCPF.class)
                .block();

        assert retornoValidacaoCPF != null;

        if(!RetornoValidacaoCPF.PODE_VOTAR.equals(retornoValidacaoCPF.getStatus())){
            logger.error("Erro na requisição do serviço externo %s");
            throw new BadRequestException(retornoValidacaoCPF.getStatus());
        }

        return retornoValidacaoCPF;
    }
}
