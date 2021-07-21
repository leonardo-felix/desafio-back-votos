package br.com.lpfx.votacaopauta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    private static final ApiInfo INFORMACAO_API = new ApiInfoBuilder()
            .title("Votação pautas REST API")
            .description("Serviço para implementar votações de pautas para cooperativas")
            .contact(new Contact("Leonardo Pires Felix", "https://lpfx.com.br", "leonardo@piresfelix.com"))
            .build();

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(INFORMACAO_API);
    }
}
