package me.wendersonfarias.empregapi.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.wendersonfarias.empregapi.dto.LoginRequest;
import me.wendersonfarias.empregapi.dto.LoginResponse;

@Tag(name = "Autenticação", description = "Endpoint para autenticação de usuários")
public interface AuthControllerDocs {

  @Operation(summary = "Autenticar usuário", description = "Realiza a autenticação de um usuário (Candidato ou Empresa) e retorna um token JWT em caso de sucesso.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida, token JWT retornado"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição (ex: email mal formatado)"),
      @ApiResponse(responseCode = "403", description = "Acesso negado - Credenciais inválidas (email ou senha incorretos)")
  })
  ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request);
}