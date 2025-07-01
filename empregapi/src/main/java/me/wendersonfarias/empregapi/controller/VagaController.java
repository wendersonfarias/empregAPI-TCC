package me.wendersonfarias.empregapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.wendersonfarias.empregapi.dto.VagaResponse;
import me.wendersonfarias.empregapi.docs.VagaControllerDocs;
import me.wendersonfarias.empregapi.dto.InscricaoDetalhesDTO;
import me.wendersonfarias.empregapi.dto.VagaRequest;
import me.wendersonfarias.empregapi.service.InscricaoService;
import me.wendersonfarias.empregapi.service.VagaService;

@RestController
@RequestMapping("/api/vagas")
@RequiredArgsConstructor
public class VagaController implements VagaControllerDocs {

  private final VagaService vagaService;
  private final InscricaoService inscricaoService;

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public VagaResponse criarVaga(@Valid @RequestBody VagaRequest requestDTO) {
    return vagaService.create(requestDTO);
  }

  @Override
  @GetMapping
  public List<VagaResponse> listarTodasVagas() {
    return vagaService.listarTodas();
  }

  @Override
  @GetMapping("/{id}")
  public VagaResponse buscarVagaPorId(@PathVariable Long id) {
    return vagaService.buscarPorId(id);
  }

  @Override
  @PutMapping("/{id}")
  public VagaResponse atualizarVaga(@PathVariable Long id, @Valid @RequestBody VagaRequest requestDTO) {
    return vagaService.atualizar(id, requestDTO);
  }

  @Override
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluirVaga(@PathVariable Long id) {
    vagaService.excluir(id);
  }

  @Override
  @PostMapping("/{id}/inscrever")
  @PreAuthorize("hasRole('CANDIDATO')")
  public ResponseEntity<Void> inscreverEmVaga(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {

    if (userDetails == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    inscricaoService.inscrever(id, userDetails.getUsername());

    return ResponseEntity.ok().build();
  }

  @Override
  @GetMapping("/{id}/inscricoes")
  @PreAuthorize("hasRole('EMPRESA') or hasRole('ADMIN')")
  public ResponseEntity<List<InscricaoDetalhesDTO>> getInscricoesDaVaga(@PathVariable Long id,
      @AuthenticationPrincipal UserDetails userDetails) {
    List<InscricaoDetalhesDTO> inscricoes = inscricaoService.listarInscricoesPorVaga(id, userDetails.getUsername());
    return ResponseEntity.ok(inscricoes);
  }
}