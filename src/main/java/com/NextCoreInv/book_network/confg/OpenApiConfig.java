package com.NextCoreInv.book_network.confg;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private int serverPort;

    @Value("${PRODUCTION_URL:https://kavindu.com}")
    private String productionUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Social Book Network API - Kavindu")
                        .version("1.0")
                        .description("OpenAPI documentation for Social Book Network API with Spring Security")
                        .termsOfService("Terms of service available at https://kavindukaveesha.com/terms")
                        .contact(new Contact()
                                .name("Kavindu")
                                .email("kavindukaveesha16@gmail.com")
                                .url("https://kavindukaveesha.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://www.opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort + "/api/v1")
                                .description("Local ENV"),
                        new Server()
                                .url(productionUrl + "/api/v1")
                                .description("Production ENV")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT Authentication: Enter your token received from the login endpoint")));
    }
}