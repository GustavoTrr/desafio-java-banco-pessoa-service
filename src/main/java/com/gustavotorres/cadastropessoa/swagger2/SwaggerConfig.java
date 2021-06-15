package com.gustavotorres.cadastropessoa.swagger2;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("Serviço de Cadastro de Pessoa", "Criado para atender o Desafio Bank SouthSystem.", "V1", "Terms of service", new Contact("Gustavo Torres", "https://github.com/GustavoTrr", "gustavotrr@gmail.com"), "License of API", "API license URL", Collections.emptyList());
        return apiInfo;
    }
}