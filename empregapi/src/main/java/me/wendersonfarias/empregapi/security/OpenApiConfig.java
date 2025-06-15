package me.wendersonfarias.empregapi.security;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "EmpregAPI - Plataforma de Vagas", version = "v1", description = "<h3>Estudo de Caso para Trabalho de Conclusão de Curso (TCC)</h3>"
    + "<p>Bem-vindo à <b>EmpregAPI</b>, a API por trás de uma plataforma completa de gerenciamento de vagas. "
    + "Este projeto foi desenvolvido como um estudo de caso para demonstrar a criação de um sistema robusto e funcional, "
    + "com o objetivo de otimizar e simplificar o processo de recrutamento.</p>"
    + "<p>Nossa missão é criar uma ponte direta entre <b>talentos</b> em busca de novas oportunidades e <b>empresas</b> que procuram os melhores profissionais do mercado.</p>"
    + "<h4>Funcionalidades Principais por Perfil:</h4>"
    + "<ul>"
    + "<li><b>Candidatos:</b> Podem criar seus perfis, buscar vagas e se candidatar às oportunidades que mais se encaixam em suas habilidades.</li>"
    + "<li><b>Empresas:</b> Podem se cadastrar, publicar novas vagas, gerenciar as candidaturas recebidas e selecionar os melhores talentos.</li>"
    + "<li><b>Administradores:</b> Têm a visão geral do sistema, gerenciando usuários e garantindo a qualidade e segurança da plataforma para todos.</li>"
    + "</ul>", contact = @Contact(name = "Wenderson Farias", email = "wendersonpfarias@gmail.com")),
    // Adiciona o requisito de segurança (cadeado) a TODOS os endpoints da API
    security = {
        @SecurityRequirement(name = "bearerAuth")
    })
@SecurityScheme(name = "bearerAuth", description = "Para acessar os endpoints protegidos, insira o token JWT obtido no login, precedido por 'Bearer '. Exemplo: Bearer eyJhbGciOiJI...", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

}