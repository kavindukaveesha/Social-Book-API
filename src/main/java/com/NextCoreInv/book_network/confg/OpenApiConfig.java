package com.NextCoreInv.book_network.confg;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Kavindu",
                        email = "kavindukaveesha16@gmail.com",
                        url = "https://kavindukaveesha.com"
                ),
                description = "OpenAPI documentation for Social Book Network API with Spring Security",
                title = "Social Book Network API - Kavindu",
                version = "1.0",
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "Terms of service available at https://kavindukaveesha.com/terms"
        ),
        servers = {
                @Server(description = "Local ENV", url = "http://localhost:8088/api/v1"),
                @Server(description = "Production ENV", url = "https://kavindu.com/api/v1")
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Authentication: Enter your token (e.g., 'Bearer <token>') received from the login endpoint",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP, // Changed to HTTP for JWT Bearer
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}