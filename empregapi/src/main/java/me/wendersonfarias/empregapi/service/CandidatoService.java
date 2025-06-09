package me.wendersonfarias.empregapi.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.wendersonfarias.empregapi.dto.CandidatoRequest;
import me.wendersonfarias.empregapi.dto.CandidatoResponse;
import me.wendersonfarias.empregapi.exception.RecursoNaoEncontradoException;
import me.wendersonfarias.empregapi.model.Candidato;
import me.wendersonfarias.empregapi.repository.CandidatoRepository;

@Service
@RequiredArgsConstructor
public class CandidatoService {

  private final CandidatoRepository candidatoRepository;
  private final PasswordEncoder passwordEncoder;

  public List<CandidatoResponse> listarCandidatos() {
    return candidatoRepository.findAll()
        .stream()
        .map(this::toResponse)
        .toList();
  }

  public CandidatoResponse buscarCandidatoPorId(Long id) {
    Candidato candidato = candidatoRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Candidato não encontrado"));
    return toResponse(candidato);
  }

  public CandidatoResponse salvarCandidato(CandidatoRequest request) {
    Candidato candidato = toEntity(request);
    candidato.setDataCadastro(LocalDate.now());
    Candidato salvo = candidatoRepository.save(candidato);
    return toResponse(salvo);
  }

  public void excluirCandidato(Long id) {
    if (!candidatoRepository.existsById(id)) {
      throw new RecursoNaoEncontradoException("Candidato não encontrado");
    }
    candidatoRepository.deleteById(id);
  }

  public CandidatoResponse atualizarCandidato(Long id, CandidatoRequest request) {
    Candidato candidato = candidatoRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Candidato não encontrado"));

    candidato.setNomeCompleto(request.getNomeCompleto());
    candidato.setEmail(request.getEmail());
    candidato.setTelefone(request.getTelefone());
    candidato.setEndereco(request.getEndereco());
    candidato.setDataNascimento(request.getDataNascimento());
    candidato.setEscolaridade(request.getEscolaridade());
    candidato.setExperienciaProfissional(request.getExperienciaProfissional());
    candidato.setHabilidades(request.getHabilidades());

    Candidato atualizado = candidatoRepository.save(candidato);
    return toResponse(atualizado);
  }

  public List<CandidatoResponse> buscarCandidatosPorNome(String nome) {
    return candidatoRepository.findByNomeCompletoContainingIgnoreCase(nome)
        .stream()
        .map(this::toResponse)
        .toList();
  }

  private CandidatoResponse toResponse(Candidato candidato) {
    return new CandidatoResponse(
        candidato.getId(),
        candidato.getNomeCompleto(),
        candidato.getEmail(),
        candidato.getTelefone(),
        candidato.getEndereco(),
        candidato.getDataNascimento(),
        candidato.getEscolaridade(),
        candidato.getExperienciaProfissional(),
        candidato.getHabilidades(),
        candidato.getDataCadastro());
  }

  private Candidato toEntity(CandidatoRequest request) {
    Candidato candidato = new Candidato();
    candidato.setNomeCompleto(request.getNomeCompleto());
    candidato.setEmail(request.getEmail());
    candidato.setSenha(passwordEncoder.encode(request.getSenha()));
    candidato.setTelefone(request.getTelefone());
    candidato.setEndereco(request.getEndereco());
    candidato.setDataNascimento(request.getDataNascimento());
    candidato.setEscolaridade(request.getEscolaridade());
    candidato.setExperienciaProfissional(request.getExperienciaProfissional());
    candidato.setHabilidades(request.getHabilidades());
    return candidato;
  }

}