package me.wendersonfarias.empregapi.security;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "EmpregAPI - Plataforma de Vagas", version = "v1", description = "<h2>API de uma Plataforma de Empregos (Estudo de Caso de TCC)</h2>"
        + "<p>A <b>EmpregAPI</b> é a infraestrutura de backend para uma plataforma de recrutamento que conecta empresas a candidatos. "
        + "Este projeto serve como um estudo de caso prático para um Trabalho de Conclusão de Curso (TCC), demonstrando a construção de uma API RESTful segura, escalável e bem documentada utilizando Java e Spring Boot.</p>"
        + "<h3>O Problema Resolvido</h3>"
        + "<p>O objetivo é simplificar e otimizar o processo de recrutamento, oferecendo um ambiente digital onde empresas podem divulgar vagas e gerenciar candidaturas, e profissionais podem encontrar oportunidades de forma centralizada.</p>"
        + "<h3>Como a API Funciona (Visão Geral)</h3>"
        + "<p>A API opera com base em três perfis de usuários, cada um com permissões e funcionalidades específicas:</p>"
        + "<ul>"
        + "<li><b>Candidatos:</b> Podem se cadastrar, buscar por vagas abertas e se candidatar àquelas que se alinham com seu perfil.</li>"
        + "<li><b>Empresas:</b> Após o cadastro, podem publicar e gerenciar suas vagas, visualizar a lista de candidatos inscritos e acompanhar o processo seletivo.</li>"
        + "<li><b>Administradores:</b> Possuem acesso total para gerenciar usuários e garantir a integridade da plataforma.</li>"
        + "</ul>"
        + "<h3>Como Usar a Documentação</h3>"
        + "<p>Para interagir com os endpoints protegidos (rotas com cadeado 🔒), primeiro utilize o endpoint <code>/api/auth/login</code> com as credenciais de um usuário (candidato ou empresa) para obter um token de acesso. "
        + "Em seguida, clique no botão <b>Authorize</b> no canto superior direito e insira o token no formato <code>Bearer SEU_TOKEN_AQUI</code>.</p>", contact = @Contact(name = "Wenderson Farias", email = "wendersonpfarias@gmail.com", url = "https://www.linkedin.com/in/dev-wenderson-farias"), license = @License(name = "Repositório no GitHub", url = "https://github.com/wendersonfarias/empregAPI-TCC")), security = {
                @SecurityRequirement(name = "bearerAuth")
        })
@SecurityScheme(name = "bearerAuth", description = "Token de autenticação JWT necessário para acessar os endpoints protegidos. Obtenha o token no endpoint de login e insira aqui no formato: Bearer eyJhbGciOi...", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

}