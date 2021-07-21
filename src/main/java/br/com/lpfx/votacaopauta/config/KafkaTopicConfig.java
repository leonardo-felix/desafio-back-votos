package br.com.lpfx.votacaopauta.config;

import br.com.lpfx.votacaopauta.kafka.EnumTopicos;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {

    @Bean
    public NewTopic torcedorCadastrado() {
        return TopicBuilder.name(EnumTopicos.VOTACA_FINALIZADA.topico).build();
    }

    @Bean
    public NewTopic torcedorDesligado() {
        return TopicBuilder.name(EnumTopicos.VOTACA_FINALIZADA.topico).build();
    }
}
