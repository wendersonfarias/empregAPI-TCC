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
import me.wendersonfarias.empregapi.docs.CandidatoControllerDocs;
import me.wendersonfarias.empregapi.dto.CandidatoRequest;
import me.wendersonfarias.empregapi.dto.CandidatoResponse;
import me.wendersonfarias.empregapi.service.CandidatoService;

@RestController
@RequestMapping("/api/candidato")
@RequiredArgsConstructor
public class CandidatoController implements CandidatoControllerDocs {

  private final CandidatoService candidatoService;

  @Override
  @GetMapping
  public List<CandidatoResponse> listarCandidatos() {
    return candidatoService.listarCandidatos();
  }

  @Override
  @GetMapping("/{id}")
  public CandidatoResponse buscarCandidatoPorId(@PathVariable Long id) {
    return candidatoService.buscarCandidatoPorId(id);
  }

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CandidatoResponse criarCandidato(@RequestBody @Valid CandidatoRequest request) {
    return candidatoService.salvarCandidato(request);
  }

  @Override
  @PutMapping("/{id}")
  public CandidatoResponse atualizarCandidato(@PathVariable Long id, @RequestBody @Valid CandidatoRequest request) {
    return candidatoService.atualizarCandidato(id, request);
  }

  @Override
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluirCandidato(@PathVariable Long id) {
    candidatoService.excluirCandidato(id);
  }
}
