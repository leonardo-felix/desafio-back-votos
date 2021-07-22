package br.com.lpfx.votacaopauta;

import br.com.lpfx.votacaopauta.dto.RetornoValidacaoCPF;
import br.com.lpfx.votacaopauta.kafka.KafkaSender;
import br.com.lpfx.votacaopauta.service.ContabilizaVotosPautaService;
import br.com.lpfx.votacaopauta.service.SistemaExterno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ConfigAdicional {
    Logger logger = LoggerFactory.getLogger(ContabilizaVotosPautaService.class);

    @Bean
    public KafkaSender createKafkaSender(){
        return pauta -> logger.debug("Enviando evento kafka teste " + pauta.toString());
    }

    @Bean
    SistemaExterno createSistemaExterno(){
        return cpf -> {
            RetornoValidacaoCPF retornoValidacaoCPF = new RetornoValidacaoCPF();
            retornoValidacaoCPF.setStatus(RetornoValidacaoCPF.PODE_VOTAR);
            return retornoValidacaoCPF;
        };
    }
}
