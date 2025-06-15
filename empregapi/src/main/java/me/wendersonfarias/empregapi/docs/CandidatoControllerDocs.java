package me.wendersonfarias.empregapi.docs;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.wendersonfarias.empregapi.dto.CandidatoRequest;
import me.wendersonfarias.empregapi.dto.CandidatoResponse;

@Tag(name = "Candidatos", description = "Endpoints para gerenciamento de candidatos")
public interface CandidatoControllerDocs {

    @Operation(summary = "Listar todos os candidatos", description = "Retorna uma lista de todos os candidatos cadastrados. Requer autenticação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de candidatos retornada com sucesso")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    List<CandidatoResponse> listarCandidatos();

    @Operation(summary = "Buscar candidato por ID", description = "Retorna os detalhes de um candidato específico pelo seu ID. Requer autenticação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    CandidatoResponse buscarCandidatoPorId(@PathVariable Long id);

    @Operation(summary = "Criar novo candidato", description = "Cria um novo perfil de candidato e seu usuário associado. Este é um endpoint público.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Candidato criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos ou e-mail já em uso")
    })
    CandidatoResponse criarCandidato(@Valid @RequestBody CandidatoRequest request);

    @Operation(summary = "Atualizar um candidato", description = "Atualiza os dados de perfil de um candidato existente. Requer autenticação do próprio candidato ou de um ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidato atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    CandidatoResponse atualizarCandidato(@PathVariable Long id, @Valid @RequestBody CandidatoRequest request);

    @Operation(summary = "Excluir um candidato", description = "Exclui um candidato e seu usuário associado. Requer autenticação do próprio candidato ou de um ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Candidato excluído com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    void excluirCandidato(@PathVariable Long id);
}