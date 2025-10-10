package com.nexus.nexus.config; // Coloque no seu pacote de configuração

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Nexus - Gerenciamento de Transporte") // Seu Título
                        .version("v0.6.7") // Versão da sua API
                        .description("Documentação da API para operações de CRUD e Autenticação de Usuários."));
    }
}