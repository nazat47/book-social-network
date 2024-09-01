package com.nazat.book_network.config;

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
                        name = "nazat",
                        email = "nazat.mf@gmail.com",
                        url = "https://github.com/nazat47"
                ),
                description = "Open api documentation for spring boot project (BSN)",
                title = "OpenApi specification - Nazat",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://someurl.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:9000/api/v1"
                ),
                @Server(
                        description = "Prod ENV",
                        url = "https://production-url.com"
                )
        },
        security = {
               @SecurityRequirement(
                       name = "bearerAuth"
               )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
