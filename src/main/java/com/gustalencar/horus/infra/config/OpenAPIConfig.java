package com.gustalencar.horus.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Configuration
    public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI(
                @Value("${springdoc.openapi.title}") String title,
                @Value("${springdoc.openapi.description}") String description,
                @Value("${springdoc.openapi.version}") String version) {

            final String securitySchemeName = "BearerAuth";

            return new OpenAPI()
                    .info(new Info()
                            .title(title)
                            .description(description)
                            .version(version))
                    .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                    .components(new Components().addSecuritySchemes(securitySchemeName,
                            new SecurityScheme()
                                    .name("Authorization")
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")
                                    .in(SecurityScheme.In.HEADER)));
        }
    }

}
