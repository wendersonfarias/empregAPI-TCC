package me.wendersonfarias.empregapi.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.wendersonfarias.empregapi.dto.InscricaoDetalhesDTO;
import me.wendersonfarias.empregapi.dto.VagaRequest;
import me.wendersonfarias.empregapi.dto.VagaResponse;

@Tag(name = "Vagas", description = "Endpoints para gerenciamento de vagas")
public interface VagaControllerDocs {

        @Operation(summary = "Criar nova vaga", description = "Cria uma nova vaga associada a uma empresa. Requer autenticação de EMPRESA ou ADMIN.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Vaga criada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
                        @ApiResponse(responseCode = "403", description = "Acesso negado - O usuário não tem permissão para criar vagas")
        })
        @SecurityRequirement(name = "bearerAuth")
        @PreAuthorize("hasRole('EMPRESA') or hasRole('ADMIN')")
        VagaResponse criarVaga(@Valid @RequestBody VagaRequest requestDTO);

        @Operation(summary = "Listar todas as vagas", description = "Retorna uma lista de todas as vagas abertas.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de vagas retornada com sucesso")
        })

        List<VagaResponse> listarTodasVagas();

        @Operation(summary = "Buscar vaga por ID", description = "Retorna os detalhes de uma vaga específica pelo seu ID. Requer autenticação.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Vaga encontrada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
        })
        @SecurityRequirement(name = "bearerAuth")
        VagaResponse buscarVagaPorId(@PathVariable Long id);

        @Operation(summary = "Atualizar uma vaga", description = "Atualiza os dados de uma vaga existente. Requer autenticação de EMPRESA ou ADMIN que seja dona da vaga.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Vaga atualizada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
                        @ApiResponse(responseCode = "403", description = "Acesso negado"),
                        @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
        })
        @SecurityRequirement(name = "bearerAuth")
        VagaResponse atualizarVaga(@PathVariable Long id, @Valid @RequestBody VagaRequest requestDTO);

        @Operation(summary = "Excluir uma vaga", description = "Exclui uma vaga existente. Requer autenticação de EMPRESA ou ADMIN que seja dona da vaga.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Vaga excluída com sucesso"),
                        @ApiResponse(responseCode = "403", description = "Acesso negado"),
                        @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
        })
        @SecurityRequirement(name = "bearerAuth")
        void excluirVaga(@PathVariable Long id);

        @Operation(summary = "Candidato se inscreve em uma vaga", description = "Permite que um candidato autenticado se inscreva em uma vaga. Requer autenticação de CANDIDATO.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Inscrição realizada com sucesso"),
                        @ApiResponse(responseCode = "403", description = "Acesso negado - Apenas candidatos podem se inscrever"),
                        @ApiResponse(responseCode = "404", description = "Vaga ou candidato não encontrado"),
                        @ApiResponse(responseCode = "409", description = "Conflito - Candidato já inscrito nesta vaga")
        })
        @SecurityRequirement(name = "bearerAuth")
        ResponseEntity<Void> inscreverEmVaga(@PathVariable Long id, UserDetails userDetails);

        @Operation(summary = "Empresa visualiza inscritos em uma vaga", description = "Permite que uma empresa autenticada veja os candidatos inscritos em uma de suas vagas. Requer autenticação de EMPRESA.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Lista de inscrições retornada com sucesso"),
                        @ApiResponse(responseCode = "403", description = "Acesso negado - Apenas a empresa dona da vaga pode ver os inscritos"),
                        @ApiResponse(responseCode = "404", description = "Vaga não encontrada")
        })
        @SecurityRequirement(name = "bearerAuth")
        ResponseEntity<List<InscricaoDetalhesDTO>> getInscricoesDaVaga(@PathVariable Long id, UserDetails userDetails);
}