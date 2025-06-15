package me.wendersonfarias.empregapi.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.time.LocalDate;

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
import me.wendersonfarias.empregapi.dto.CandidatoRequest;
import me.wendersonfarias.empregapi.dto.EmpresaRequest;
import me.wendersonfarias.empregapi.dto.LoginRequest;
import me.wendersonfarias.empregapi.dto.VagaRequest;
import me.wendersonfarias.empregapi.enumeracao.TipoContratacao;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class VagaControllerIT {

  @LocalServerPort
  private int port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Nested
  @DisplayName("Testes para Endpoints de Vaga que precisem dados ja armazenados")
  class EndpointsProtegidosVaga {
    private String token;
    private String tokenCandidato;
    private Long empresaId;
    private Long vagaId;

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

      CandidatoRequest candidatoRequest = criarCandidatoRequestValida();

      given()
          .contentType(ContentType.JSON)
          .body(candidatoRequest)
          .when()
          .post("/api/candidato")
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .body("id", notNullValue())
          .extract().path("id");

      tokenCandidato = obterTokenDeAcesso(candidatoRequest.getEmail(), candidatoRequest.getSenha());

      VagaRequest vagaRequest = criarVagaRequestPreenchida(empresaId);

      vagaId = ((Number) given()
          .header("Authorization", "Bearer " + token)
          .contentType(ContentType.JSON)
          .body(vagaRequest)
          .when()
          .post("/api/vagas")
          .then()
          .statusCode(HttpStatus.CREATED.value())
          .body("id", notNullValue())
          .extract().path("id")).longValue();
    }

    @Test
    @DisplayName("Dado um empresa existente e token válido, quando criar uma vaga, então deve retornar Status 200 OK")
    void dadoEmpresaExistenteETokenValido_quandoCriarVaga_entaoRetornaStatus200() {
      VagaRequest vagaRequest = criarVagaRequestPreenchida(empresaId);

      given()
          .header("Authorization", "Bearer " + token)
          .contentType(ContentType.JSON)
          .body(vagaRequest)
          .when()
          .post("/api/vagas")
          .then()
          .log().all()
          .statusCode(HttpStatus.CREATED.value())
          .body("id", notNullValue())
          .body("titulo", equalTo(vagaRequest.getTitulo()));
    }

    @Test
    @DisplayName("Dado uma empresa existente e vaga criada, deve retornar Status 200 e lista com a vaga")
    void dadoEmpresaExistente_VagaExistente_quandoBuscarVagas_entaoRetornaVaga() {
      given()
          .when()
          .get("/api/vagas")
          .then()
          .log().all()
          .statusCode(HttpStatus.OK.value())
          .body("[0].titulo", equalTo("Desenvolvedor Backend Pleno"));
    }

    @Test
    @DisplayName("Dado um ID existente e token válido, quando buscar por ID, então deve retornar Status 200 OK")
    void dadoIdExistenteETokenValido_quandoBuscarPorId_entaoRetornaVaga() {

      given()
          .header("Authorization", "Bearer " + token)
          .when()
          .get("/api/vagas/" + vagaId)
          .then()
          .log().all()
          .statusCode(HttpStatus.OK.value())
          .body("id", equalTo(vagaId.intValue()))
          .body("status", equalTo("ABERTA"));
    }

    @Test
    @DisplayName("Dado um token válido, quando atualizar vaga, então deve retornar Status 200 OK")
    void dadoTokenValido_quandoAtualizarVaga_entaoRetornaVagaAtualizada() {

      VagaRequest requestAtualizado = new VagaRequest();
      requestAtualizado.setTitulo("Título Atualizado");
      requestAtualizado.setDescricao("Descrição Atualizada");
      requestAtualizado.setLocalizacao("Remoto");
      requestAtualizado.setTipoContratacao(TipoContratacao.valueOf("CLT"));
      requestAtualizado.setRequisitos("Java, Spring Boot, REST.");
      requestAtualizado.setBeneficios("Plano de saúde, VR.");
      requestAtualizado.setSalario(BigDecimal.valueOf(9000.00));
      requestAtualizado.setEmpresaId(empresaId);

      given()
          .header("Authorization", "Bearer " + token)
          .contentType(ContentType.JSON)
          .body(requestAtualizado)
          .when()
          .put("/api/vagas/" + vagaId)
          .then()
          .log().all()
          .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Dado um token válido, quando atualizar vaga com dados inválidos, então deve retornar Status 400 Bad Request")
    void dadoTokenValido_quandoAtualizarVagaComDadosInvalidos_entaoRetornaBadRequest() {
      VagaRequest requestAtualizado = new VagaRequest();

      requestAtualizado.setTitulo(null);
      requestAtualizado.setDescricao(null);
      requestAtualizado.setLocalizacao(null);
      requestAtualizado.setTipoContratacao(null);
      requestAtualizado.setRequisitos(null);
      requestAtualizado.setBeneficios(null);
      requestAtualizado.setSalario(null);
      requestAtualizado.setEmpresaId(null);

      given()
          .header("Authorization", "Bearer " + token)
          .contentType(ContentType.JSON)
          .body(requestAtualizado)
          .when()
          .put("/api/vagas/" + vagaId)
          .then()
          .log().all()
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .body("errors", notNullValue())
          .body("errors.find { it.campo == 'titulo' }.erro", equalTo("O título é obrigatório"))
          .body("errors.find { it.campo == 'descricao' }.erro", equalTo("A descrição é obrigatória"));
    }

    @Test
    @DisplayName("Dado um token válido, quando excluir vaga, então deve retornar Status 204 No Content")
    void dadoTokenValido_quandoExcluirVaga_entaoRetorna204() {

      given()
          .header("Authorization", "Bearer " + token)
          .when()
          .delete("/api/vagas/" + vagaId)
          .then()
          .log().all()
          .statusCode(HttpStatus.NO_CONTENT.value());

      given()
          .header("Authorization", "Bearer " + token)
          .when()
          .get("/api/vagas/" + vagaId)
          .then()
          .log().all()
          .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Dado um Candidato valido, quando se inscrever em uma vaga, então deve retornar Status 200 OK")
    void dadoCandidatoValido_quandoSeInscreverEmUmaVaga_entaoRetorna200() {

      given()
          .header("Authorization", "Bearer " + tokenCandidato)
          .contentType(ContentType.JSON)
          .post("/api/vagas/" + vagaId + "/inscrever")
          .then()
          .log().all()
          .statusCode(HttpStatus.OK.value());

      given()
          .header("Authorization", "Bearer " + tokenCandidato)
          .when()
          .post("/api/vagas/" + vagaId + "/inscrever")
          .then()
          .log().all()
          .statusCode(HttpStatus.CONFLICT.value())
          .body("erro", equalTo("Candidato já inscrito nesta vaga."));

    }

    @DisplayName("Dado um Candidato valido que se inscreveu em uma vaga, empresa deve visualizar inscrição de candidato")
    @Test
    void dadoCandidatoValido_quandoVisualizarInscricaoDeCandidato_entaoRetornaStatus200() {

      given()
          .header("Authorization", "Bearer " + tokenCandidato)
          .contentType(ContentType.JSON)
          .post("/api/vagas/" + vagaId + "/inscrever")
          .then()
          .log().all()
          .statusCode(HttpStatus.OK.value());

      given()
          .header("Authorization", "Bearer " + token)
          .when()
          .get("/api/vagas/" + vagaId + "/inscricoes")
          .then()
          .log().all()
          .statusCode(HttpStatus.OK.value())
          .body("[0].numeroInscricao", equalTo(1))
          .body("[0].status", equalTo("INSCRITO"))
          .body("[0].candidato.nomeCompleto", equalTo("Candidato Válido"))
          .body("[0].candidato.email", equalTo("candidato@teste.com"));

    }

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

  private EmpresaRequest criarEmpresaRequestValida(String email, String cnpj) {
    EmpresaRequest empresaRequest = new EmpresaRequest();
    empresaRequest.setNome("Empresa Valida");
    empresaRequest.setCnpj(cnpj);
    empresaRequest.setEmail(email);
    empresaRequest.setSenha("senhaForte123");
    return empresaRequest;
  }

  private VagaRequest criarVagaRequestValida(String titulo,
      String descricao,
      String localizacao,
      String tipoContratacao,
      Long empresaId,
      String requisitos,
      String beneficios,
      Double salario) {

    VagaRequest vagaRequest = new VagaRequest();
    vagaRequest.setTitulo(titulo);
    vagaRequest.setDescricao(descricao);
    vagaRequest.setLocalizacao(localizacao);
    vagaRequest.setTipoContratacao(TipoContratacao.valueOf(tipoContratacao));
    vagaRequest.setEmpresaId(empresaId);
    vagaRequest.setRequisitos(requisitos);
    vagaRequest.setBeneficios(beneficios);
    vagaRequest.setSalario(BigDecimal.valueOf(salario));
    return vagaRequest;
  }

  private VagaRequest criarVagaRequestPreenchida(Long empresaID) {
    VagaRequest vagaRequest = criarVagaRequestValida(
        "Desenvolvedor Backend Pleno",
        "Vaga para desenvolvedor com experiência em Java.",
        "Remoto",
        "CLT",
        empresaID,
        "Java, Spring Boot, REST.",
        "Plano de saúde, VR.", 8000.00);

    return vagaRequest;
  }

  private CandidatoRequest criarCandidatoRequestValida() {
    CandidatoRequest candidatoRequest = new CandidatoRequest();
    candidatoRequest.setNomeCompleto("Candidato Válido");
    candidatoRequest.setEmail("candidato@teste.com");
    candidatoRequest.setSenha("senhaForte123");
    candidatoRequest.setTelefone("11987654321");
    candidatoRequest.setDataNascimento(LocalDate.of(2000, 1, 1));
    candidatoRequest.setEscolaridade("Superior Completo");
    candidatoRequest.setExperienciaProfissional("Nenhuma");
    candidatoRequest.setHabilidades("Nenhuma");
    return candidatoRequest;
  }

}