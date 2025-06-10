package me.wendersonfarias.empregapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.wendersonfarias.empregapi.dto.VagaRequest;
import me.wendersonfarias.empregapi.dto.VagaResponse;
import me.wendersonfarias.empregapi.dto.VagaResponse.EmpresaSimplificadoDTO;
import me.wendersonfarias.empregapi.exception.RecursoNaoEncontradoException;
import me.wendersonfarias.empregapi.model.Empresa;
import me.wendersonfarias.empregapi.model.Vaga;
import me.wendersonfarias.empregapi.repository.EmpresaRepository;
import me.wendersonfarias.empregapi.repository.VagaRepository;

@Service
@RequiredArgsConstructor
public class VagaService {

  private final VagaRepository vagaRepository;
  private final EmpresaRepository empresaRepository;

  public VagaResponse create(VagaRequest request) {

    Empresa empresa = empresaRepository.findById(request.getEmpresaId())
        .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa não encontrada: " + request.getEmpresaId()));

    Vaga vaga = toEntity(request);
    vaga.setEmpresa(empresa);

    Vaga vagaSalva = vagaRepository.save(vaga);
    return toResponse(vagaSalva);
  }

  public List<VagaResponse> listarTodas() {
    return vagaRepository.findAll().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  public VagaResponse buscarPorId(Long id) {
    Vaga vaga = vagaRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Vaga não encontrada " + id));
    return toResponse(vaga);
  }

  public VagaResponse atualizar(Long id, VagaRequest request) {
    Vaga vaga = vagaRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Vaga não encontrada: " + id));

    if (!vaga.getEmpresa().getId().equals(request.getEmpresaId())) {
      throw new IllegalArgumentException("Não é possível alterar a empresa de uma vaga.");
    }

    vaga.setTitulo(request.getTitulo());
    vaga.setDescricao(request.getDescricao());
    vaga.setLocalizacao(request.getLocalizacao());
    vaga.setTipoContratacao(request.getTipoContratacao());
    vaga.setRequisitos(request.getRequisitos());
    vaga.setBeneficios(request.getBeneficios());
    vaga.setSalario(request.getSalario());

    return toResponse(vagaRepository.save(vaga));
  }

  public void excluir(Long id) {
    if (!vagaRepository.existsById(id)) {
      throw new RecursoNaoEncontradoException("Vaga não encontrada: " + id);
    }
    vagaRepository.deleteById(id);
  }

  private VagaResponse toResponse(Vaga vaga) {
    VagaResponse response = new VagaResponse();
    response.setId(vaga.getId());
    response.setTitulo(vaga.getTitulo());
    response.setDescricao(vaga.getDescricao());
    response.setLocalizacao(vaga.getLocalizacao());
    response.setTipoContratacao(vaga.getTipoContratacao());
    response.setStatus(vaga.getStatus());
    response.setDataPublicacao(vaga.getDataPublicacao());
    response.setRequisitos(vaga.getRequisitos());
    response.setBeneficios(vaga.getBeneficios());
    response.setSalario(vaga.getSalario());

    EmpresaSimplificadoDTO empresaDTO = new EmpresaSimplificadoDTO();
    empresaDTO.setId(vaga.getEmpresa().getId());
    empresaDTO.setNome(vaga.getEmpresa().getNome());
    response.setEmpresa(empresaDTO);

    return response;
  }

  private Vaga toEntity(VagaRequest request) {
    Vaga vaga = new Vaga();
    vaga.setTitulo(request.getTitulo());
    vaga.setDescricao(request.getDescricao());
    vaga.setLocalizacao(request.getLocalizacao());
    vaga.setTipoContratacao(request.getTipoContratacao());
    vaga.setRequisitos(request.getRequisitos());
    vaga.setBeneficios(request.getBeneficios());
    vaga.setSalario(request.getSalario());

    return vaga;
  }
}