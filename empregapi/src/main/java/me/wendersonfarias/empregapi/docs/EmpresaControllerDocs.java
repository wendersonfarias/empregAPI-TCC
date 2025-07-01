package me.wendersonfarias.empregapi.docs;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.wendersonfarias.empregapi.dto.EmpresaRequest;
import me.wendersonfarias.empregapi.dto.EmpresaResponse;

@Tag(name = "Empresas", description = "Endpoints para gerenciamento de empresas")
public interface EmpresaControllerDocs {

  @Operation(summary = "Criar nova empresa", description = "Cria um novo perfil de empresa e seu usuário associado. Este é um endpoint público.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos ou e-mail já em uso"),
      @ApiResponse(responseCode = "409", description = "Conflito - CNPJ já cadastrado")
  })
  EmpresaResponse criarEmpresa(@Valid @RequestBody EmpresaRequest request);

  @Operation(summary = "Listar todas as empresas", description = "Retorna uma lista de todas as empresas cadastradas. Requer autenticação.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de empresas retornada com sucesso")
  })
  @SecurityRequirement(name = "bearerAuth")
  List<EmpresaResponse> listarEmpresas();

  @Operation(summary = "Buscar empresa por ID", description = "Retorna os detalhes de uma empresa específica pelo seu ID. Requer autenticação.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Empresa encontrada com sucesso"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  @SecurityRequirement(name = "bearerAuth")
  EmpresaResponse buscarEmpresaPorId(@PathVariable Long id);

  @Operation(summary = "Atualizar uma empresa", description = "Atualiza os dados de perfil de uma empresa existente. Requer autenticação de EMPRESA ou ADMIN.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
      @ApiResponse(responseCode = "403", description = "Acesso negado"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  @SecurityRequirement(name = "bearerAuth")
  EmpresaResponse atualizarEmpresa(@PathVariable Long id, @Valid @RequestBody EmpresaRequest request);

  @Operation(summary = "Excluir uma empresa", description = "Exclui uma empresa e seu usuário associado. Requer autenticação de EMPRESA ou ADMIN.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Empresa excluída com sucesso"),
      @ApiResponse(responseCode = "403", description = "Acesso negado"),
      @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
  })
  @SecurityRequirement(name = "bearerAuth")
  void excluirEmpresa(@PathVariable Long id);
}