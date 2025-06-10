package me.wendersonfarias.empregapi.security;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "EmpregAPI", version = "v1", description = "API para gerenciamento de vagas de emprego."),
    // Adiciona o requisito de segurança a TODOS os endpoints da API
    security = {
        @SecurityRequirement(name = "bearerAuth")
    })
@SecurityScheme(name = "bearerAuth", description = "Token JWT de Autenticação", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

}