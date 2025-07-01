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
import me.wendersonfarias.empregapi.docs.EmpresaControllerDocs;
import me.wendersonfarias.empregapi.dto.EmpresaRequest;
import me.wendersonfarias.empregapi.dto.EmpresaResponse;
import me.wendersonfarias.empregapi.service.EmpresaService;

@RestController
@RequestMapping("/api/empresa")
@RequiredArgsConstructor
public class EmpresaController implements EmpresaControllerDocs {

  private final EmpresaService empresaService;

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EmpresaResponse criarEmpresa(@RequestBody @Valid EmpresaRequest request) {
    return empresaService.create(request);
  }

  @Override
  @GetMapping
  public List<EmpresaResponse> listarEmpresas() {
    return empresaService.listarEmpresas();
  }

  @Override
  @GetMapping("/{id}")
  public EmpresaResponse buscarEmpresaPorId(@PathVariable Long id) {
    return empresaService.buscarEmpresaPorId(id);
  }

  @Override
  @PutMapping("/{id}")
  public EmpresaResponse atualizarEmpresa(@PathVariable Long id, @RequestBody @Valid EmpresaRequest request) {
    return empresaService.atualizarEmpresa(id, request);
  }

  @Override
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluirEmpresa(@PathVariable Long id) {
    empresaService.excluirEmpresa(id);
  }
}