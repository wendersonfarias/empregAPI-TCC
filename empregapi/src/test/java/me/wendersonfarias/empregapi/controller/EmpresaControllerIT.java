package me.wendersonfarias.empregapi.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

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

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import me.wendersonfarias.empregapi.dto.EmpresaRequest;
import me.wendersonfarias.empregapi.dto.LoginRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class EmpresaControllerIT {

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Nested
  @DisplayName("Testes para Criação de Empresa (POST /api/empresa)")
  class CriarEmpresaTests {

    @Test
    @DisplayName("Dado um request válido, quando criar empresa, então deve retornar Status 201 Created")
    void dadoRequestValido_quandoCriarEmpresa_entaoRetorna201() {
      EmpresaRequest empresaRequest = criarEmpresaRequestValida("contato@techsolucoes.com", "51790600000131");

      given()
          .contentType(ContentType.JSON)
          .body(empresaRequest)
          .when()
          .post("/api/empresa")
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .body("id", notNullValue())
          .body("nome", equalTo(empresaRequest.getNome()));
    }

    @Test
    @DisplayName("Dado um CNPJ inválido, quando criar empresa, então deve retornar Status 400 Bad Request")
    void dadoCnpjInvalido_quandoCriarEmpresa_entaoRetorna400() {
      EmpresaRequest empresaRequest = criarEmpresaRequestValida("contato@cnpjruim.com", "11111111111111");

      given()
          .contentType(ContentType.JSON)
          .body(empresaRequest)
          .when()
          .post("/api/empresa")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .body("errors[0].campo", equalTo("cnpj"));
    }
  }

  @Nested
  @DisplayName("Testes para Endpoints Protegidos de Empresa")
  class EndpointsProtegidosTests {

    // Variáveis para armazenar o estado criado antes de cada teste
    private String token;
    private Long empresaId;

    @BeforeEach
    void setupProtegido() {

      final String cnpjDaEmpresa = "27865757000102";
      final String emailDaEmpresa = "empresa.protegida@email.com";

      EmpresaRequest empresaRequest = criarEmpresaRequestValida(emailDaEmpresa, cnpjDaEmpresa);

      empresaId = ((Number) given()
          .contentType(ContentType.JSON)
          .body(empresaRequest)
          .when()
          .post("/api/empresa")
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .extract()
          .path("id")).longValue();

      token = obterTokenDeAcesso(emailDaEmpresa, "senhaForte123");
    }

    @Test
    @DisplayName("Dado um ID existente e token válido, quando buscar por ID, então deve retornar Status 200 OK")
    void dadoIdExistenteETokenValido_quandoBuscarPorId_entaoRetornaEmpresa() {

      given()
          .header("Authorization", "Bearer " + token)
          .when()
          .get("/api/empresa/" + empresaId)
          .then()
          .statusCode(HttpStatus.OK.value())
          .body("id", equalTo(empresaId.intValue()))
          .body("email", equalTo("empresa.protegida@email.com"));
    }

    @Test
    @DisplayName("Dado um token válido, quando atualizar empresa, então deve retornar Status 200 OK")
    void dadoTokenValido_quandoAtualizarEmpresa_entaoRetornaEmpresaAtualizada() {

      EmpresaRequest requestAtualizado = new EmpresaRequest();
      requestAtualizado.setNome("Nome Super Atualizado");
      requestAtualizado.setCnpj("27865757000102");
      requestAtualizado.setEmail("empresa.protegida@email.com");
      requestAtualizado.setSenha("senhaForte123");

      given()
          .header("Authorization", "Bearer " + token)
          .contentType(ContentType.JSON)
          .body(requestAtualizado)
          .when()
          .put("/api/empresa/" + empresaId)
          .then()
          .log().all() // Adiciona isso para exibir o conteúdo da resposta
          .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Dado um token válido, quando excluir empresa, então deve retornar Status 204 No Content")
    void dadoTokenValido_quandoExcluirEmpresa_entaoRetorna204() {

      given()
          .header("Authorization", "Bearer " + token)
          .when()
          .delete("/api/empresa/" + empresaId)
          .then()
          .statusCode(HttpStatus.NO_CONTENT.value());

      given()
          .header("Authorization", "Bearer " + token)
          .when()
          .get("/api/empresa/" + empresaId)
          .then()
          .statusCode(HttpStatus.FORBIDDEN.value());
    }
  }

  private EmpresaRequest criarEmpresaRequestValida(String email, String cnpj) {
    EmpresaRequest empresaRequest = new EmpresaRequest();
    empresaRequest.setNome("Empresa Valida");
    empresaRequest.setCnpj(cnpj);
    empresaRequest.setEmail(email);
    empresaRequest.setSenha("senhaForte123");
    return empresaRequest;
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
}