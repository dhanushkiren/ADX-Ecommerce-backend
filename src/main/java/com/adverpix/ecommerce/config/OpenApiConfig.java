package com.adverpix.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI myOpenAPI () {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("server URL for the Development process.");

        Contact contact = new Contact();
        contact.setName("Adverpix");
        License license = new License().name("MIT License");


        Info info = new Info().title("E-Commerce API")
                .version("1.0.0")
                .contact(contact)
                .description("API development for E-Commerce Application. Test the Endpoint here and manage Endpoints.");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
