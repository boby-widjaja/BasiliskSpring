package com.basiliskSB;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {

    //Buka dengan link: http://localhost:7070/basiliskSB/swagger-ui/index.html

    @Bean
    public OpenAPI basiliskOpenAPI(){

        String schemeName = "bearerAuth";

        Info info = new Info().title("Basilisk API Documentation")
                .description("Contoh Open API untuk BC24 Training mengenal Swagger")
                .version("v 1.0.0")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));

        ExternalDocumentation externalDocumentation = new ExternalDocumentation()
                .description("Basilisk MVC UI")
                .url("/");

        SecurityRequirement requirement = new SecurityRequirement().addList(schemeName);

        SecurityScheme scheme = new SecurityScheme()
                .name(schemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        Components components = new Components().addSecuritySchemes(schemeName, scheme);

        OpenAPI openAPI = new OpenAPI()
                .info(info)
                .externalDocs(externalDocumentation)
                .addSecurityItem(requirement)
                .components(components);

        return openAPI;
    }

}
