package br.com.lpfx.votacaopauta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VotacaoPautaApplication {

    public static void main(String[] args) {
        SpringApplication.run(VotacaoPautaApplication.class, args);
    }

}
