package me.wendersonfarias.empregapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import me.wendersonfarias.empregapi.dto.VagaRequest;
import me.wendersonfarias.empregapi.service.VagaService;

@RestController
@RequestMapping("/api/vagas")
@RequiredArgsConstructor
public class VagaController {

  private final VagaService vagaService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public VagaResponse criarVaga(@Valid @RequestBody VagaRequest requestDTO) {
    return vagaService.create(requestDTO);
  }

  @GetMapping
  public List<VagaResponse> listarTodasVagas() {
    return vagaService.listarTodas();
  }

  @GetMapping("/{id}")
  public VagaResponse buscarVagaPorId(@PathVariable Long id) {
    return vagaService.buscarPorId(id);
  }

  @PutMapping("/{id}")
  public VagaResponse atualizarVaga(@PathVariable Long id, @Valid @RequestBody VagaRequest requestDTO) {
    return vagaService.atualizar(id, requestDTO);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluirVaga(@PathVariable Long id) {
    vagaService.excluir(id);
  }
}