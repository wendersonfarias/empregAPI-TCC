package me.wendersonfarias.empregapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.wendersonfarias.empregapi.dto.CandidatoRequest;
import me.wendersonfarias.empregapi.dto.CandidatoResponse;
import me.wendersonfarias.empregapi.enumeracao.Role;
import me.wendersonfarias.empregapi.exception.RecursoNaoEncontradoException;
import me.wendersonfarias.empregapi.model.Candidato;
import me.wendersonfarias.empregapi.model.Usuario;
import me.wendersonfarias.empregapi.repository.CandidatoRepository;

@Service
@RequiredArgsConstructor
public class CandidatoService {

  private final CandidatoRepository candidatoRepository;
  private final UsuarioService usuarioService;

  @Transactional
  public CandidatoResponse salvarCandidato(CandidatoRequest request) {

    Usuario usuarioCriado = usuarioService.criarUsuario(request.getEmail(), request.getSenha(), Role.ROLE_CANDIDATO);

    Candidato novoCandidato = toEntity(request);
    novoCandidato.setUsuario(usuarioCriado);
    Candidato candidatoSalvo = candidatoRepository.save(novoCandidato);

    return toResponse(candidatoSalvo);
  }

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

  public CandidatoResponse atualizarCandidato(Long id, CandidatoRequest request) {
    Candidato candidato = candidatoRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Candidato não encontrado"));

    candidato.setNomeCompleto(request.getNomeCompleto());
    candidato.setTelefone(request.getTelefone());
    candidato.setEndereco(request.getEndereco());
    candidato.setDataNascimento(request.getDataNascimento());
    candidato.setEscolaridade(request.getEscolaridade());
    candidato.setExperienciaProfissional(request.getExperienciaProfissional());
    candidato.setHabilidades(request.getHabilidades());

    Candidato atualizado = candidatoRepository.save(candidato);
    return toResponse(atualizado);
  }

  public void excluirCandidato(Long id) {
    if (!candidatoRepository.existsById(id)) {
      throw new RecursoNaoEncontradoException("Candidato não encontrado");
    }
    candidatoRepository.deleteById(id);
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
        candidato.getUsuario().getEmail(),
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
    candidato.setTelefone(request.getTelefone());
    candidato.setEndereco(request.getEndereco());
    candidato.setDataNascimento(request.getDataNascimento());
    candidato.setEscolaridade(request.getEscolaridade());
    candidato.setExperienciaProfissional(request.getExperienciaProfissional());
    candidato.setHabilidades(request.getHabilidades());
    return candidato;
  }
}