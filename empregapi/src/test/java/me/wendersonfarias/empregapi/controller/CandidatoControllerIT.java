package me.wendersonfarias.empregapi.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import me.wendersonfarias.empregapi.dto.CandidatoRequest;
import me.wendersonfarias.empregapi.dto.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class CandidatoControllerIT {

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Nested
  @DisplayName("Testes para Criação de Candidato (POST /api/candidato)")
  class CriarCandidatoIT {

    @Test
    @DisplayName("Dado um request válido, quando criar candidato, então deve retornar Status 201 Created")
    void dadoRequestValido_quandoCriarCandidato_entaoRetorna201() {
      CandidatoRequest candidatoRequest = criarCandidatoRequestValida("candidato.valido@email.com");

      given()
          .contentType(ContentType.JSON)
          .body(candidatoRequest)
          .when()
          .post("/api/candidato")
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .body("id", notNullValue())
          .body("nomeCompleto", equalTo("Candidato Válido"));
    }

    @Test
    @DisplayName("Dado um e-mail já existente, quando criar candidato, então deve retornar Status 400 Bad Request")
    void dadoEmailExistente_quandoCriarCandidato_entaoRetorna400() {
      CandidatoRequest primeiroRequest = criarCandidatoRequestValida("candidato.duplicado@email.com");

      given()
          .contentType(ContentType.JSON)
          .body(primeiroRequest)
          .when()
          .post("/api/candidato")
          .then()
          .statusCode(HttpStatus.CREATED.value());

      CandidatoRequest segundoRequest = criarCandidatoRequestValida("candidato.duplicado@email.com");

      given()
          .contentType(ContentType.JSON)
          .body(segundoRequest)
          .when()
          .post("/api/candidato")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .body("erro", equalTo("Este e-mail já está em uso: candidato.duplicado@email.com"));
    }
  }

  @Nested
  @DisplayName("Testes para Endpoints Protegidos de Candidato")
  class EndpointsProtegidosTests {

    private String token;
    private Long candidatoId;
    private String candidatoEmail;
    private String adminToken;

    @BeforeEach
    void setupProtegido() {
      candidatoEmail = "candidato.protegido@email.com";
      CandidatoRequest candidatoRequest = criarCandidatoRequestValida(candidatoEmail);

      candidatoId = ((Number) given()
          .contentType(ContentType.JSON)
          .body(candidatoRequest)
          .when()
          .post("/api/candidato")
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .extract()
          .path("id")).longValue();

      token = obterTokenDeAcesso(candidatoEmail, "senhaForte123");
      adminToken = obterTokenDeAcessoAdmin();

      CandidatoRequest outroCandidatoRequest = criarCandidatoRequestValida("outro.candidato@email.com");

      given()
          .contentType(ContentType.JSON)
          .body(outroCandidatoRequest)
          .when()
          .post("/api/candidato")
          .then()
          .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Dado um ID existente e token válido, quando buscar por ID, então deve retornar Status 200 OK")
    void dadoIdExistenteETokenValido_quandoBuscarPorId_entaoRetornaCandidato() {
      given()
          .header("Authorization", "Bearer " + token)
          .when()
          .get("/api/candidato/" + candidatoId)
          .then()
          .statusCode(HttpStatus.OK.value())
          .body("id", equalTo(candidatoId.intValue()))
          .body("email", equalTo(candidatoEmail));
    }

    @Test
    @DisplayName("Dado um token válido, quando atualizar candidato, então deve retornar Status 200 OK")
    void dadoTokenValido_quandoAtualizarCandidato_entaoRetornaCandidatoAtualizado() {
      CandidatoRequest requestAtualizado = criarCandidatoRequestValida(candidatoEmail);
      requestAtualizado.setNomeCompleto("Candidato Nome Atualizado");
      requestAtualizado.setTelefone("99999999999");

      given()
          .header("Authorization", "Bearer " + token)
          .contentType(ContentType.JSON)
          .body(requestAtualizado)
          .when()
          .put("/api/candidato/" + candidatoId)
          .then()
          .statusCode(HttpStatus.OK.value())
          .body("nomeCompleto", equalTo("Candidato Nome Atualizado"))
          .body("telefone", equalTo("99999999999"));
    }

    @Test
    @DisplayName("Dado um token válido, quando excluir candidato, então deve retornar Status 204 No Content")
    void dadoTokenValido_quandoExcluirCandidato_entaoRetorna204() {
      given()
          .header("Authorization", "Bearer " + token)
          .when()
          .delete("/api/candidato/" + candidatoId)
          .then()
          .statusCode(HttpStatus.NO_CONTENT.value());

      given()
          .header("Authorization", "Bearer " + token)
          .when()
          .get("/api/candidato/" + candidatoId)
          .then()
          .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Dado um candidato autenticado, quando tentar listar todos os candidatos, então deve retornar Status 403 Forbidden")
    void dadoCandidatoAutenticado_quandoListarCandidatos_entaoRetorna403Forbidden() {
      given()
          .header("Authorization", "Bearer " + token)
          .when()
          .get("/api/candidato")
          .then()
          .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Dado um ADMIN autenticado, quando listar todos os candidatos, então deve retornar Status 200 OK com a lista")
    void dadoAdminAutenticado_quandoListarCandidatos_entaoRetorna200OkComLista() {
      String outroCandidatoEmail = "outro.candidato@email.com";
      criarCandidatoRequestValida(outroCandidatoEmail);

      given()
          .header("Authorization", "Bearer " + adminToken)
          .when()
          .get("/api/candidato")
          .then()
          .statusCode(HttpStatus.OK.value())
          .body("$", hasSize(2))
          .body("[0].email", equalTo(candidatoEmail))
          .body("[1].email", equalTo(outroCandidatoEmail));
    }
  }

  private CandidatoRequest criarCandidatoRequestValida(String email) {
    CandidatoRequest candidatoRequest = new CandidatoRequest();
    candidatoRequest.setNomeCompleto("Candidato Válido");
    candidatoRequest.setEmail(email);
    candidatoRequest.setSenha("senhaForte123");
    candidatoRequest.setTelefone("11987654321");
    candidatoRequest.setDataNascimento(LocalDate.of(2000, 1, 1));
    candidatoRequest.setEscolaridade("Superior Completo");
    candidatoRequest.setExperienciaProfissional("Nenhuma");
    candidatoRequest.setHabilidades("Nenhuma");
    return candidatoRequest;
  }

  private String obterTokenDeAcesso(String email, String senha) {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail(email);
    loginRequest.setSenha(senha);

    return given()
        .contentType(ContentType.JSON)
        .body(loginRequest)
        .when()
        .post("/api/auth/login")
        .then()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .path("token");
  }

  private String obterTokenDeAcessoAdmin() {
    return obterTokenDeAcesso("admin@empregapi.com", "admin123");
  }
}
