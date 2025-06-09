package me.wendersonfarias.empregapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.wendersonfarias.empregapi.dto.EmpresaRequest;
import me.wendersonfarias.empregapi.dto.EmpresaResponse;
import me.wendersonfarias.empregapi.exception.CnpjJaCadastradoException;
import me.wendersonfarias.empregapi.exception.RecursoNaoEncontradoException;
import me.wendersonfarias.empregapi.model.Empresa;
import me.wendersonfarias.empregapi.repository.EmpresaRepository;

@Service
@RequiredArgsConstructor
public class EmpresaService {

  private final EmpresaRepository empresaRepository;

  public EmpresaResponse create(EmpresaRequest request) {
    this.empresaRepository.findByCnpj(request.getCnpj()).ifPresent(empresa -> {
      throw new CnpjJaCadastradoException("CNPJ já cadastrado: " + request.getCnpj());
    });

    Empresa empresa = toEntity(request);

    Empresa empresaSalva = this.empresaRepository.save(empresa);
    return toResponse(empresaSalva);
  }

  public List<EmpresaResponse> listarEmpresas() {
    return empresaRepository.findAll()
        .stream()
        .map(this::toResponse)
        .toList();
  }

  public EmpresaResponse buscarEmpresaPorId(Long id) {
    Empresa empresa = empresaRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa não encontrada com o ID: " + id));
    return toResponse(empresa);
  }

  public EmpresaResponse atualizarEmpresa(Long id, EmpresaRequest request) {
    Empresa empresa = empresaRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa não encontrada com o ID: " + id));

    empresa.setNome(request.getNome());
    empresa.setDescription(request.getDescription());
    empresa.setWebsite(request.getWebsite());
    empresa.setEmail(request.getEmail());

    Empresa empresaAtualizada = empresaRepository.save(empresa);
    return toResponse(empresaAtualizada);
  }

  public void excluirEmpresa(Long id) {
    if (!empresaRepository.existsById(id)) {
      throw new RecursoNaoEncontradoException("Empresa não encontrada com o ID: " + id);
    }
    empresaRepository.deleteById(id);
  }

  private EmpresaResponse toResponse(Empresa empresa) {
    return new EmpresaResponse(
        empresa.getId(),
        empresa.getNome(),
        empresa.getCnpj(),
        empresa.getDescription(),
        empresa.getWebsite(),
        empresa.getEmail());
  }

  private Empresa toEntity(EmpresaRequest request) {
    Empresa empresa = new Empresa();
    empresa.setNome(request.getNome());
    empresa.setCnpj(request.getCnpj());
    empresa.setDescription(request.getDescription());
    empresa.setWebsite(request.getWebsite());
    empresa.setEmail(request.getEmail());
    empresa.setSenha(request.getSenha());
    return empresa;
  }
}