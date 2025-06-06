package me.wendersonfarias.empregapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.wendersonfarias.empregapi.exception.RecursoNaoEncontradoException;
import me.wendersonfarias.empregapi.model.Candidato;
import me.wendersonfarias.empregapi.repository.CandidatoRepository;

@Service
@RequiredArgsConstructor
public class CandidatoService {

  private final CandidatoRepository candidatoRepository;

  public List<Candidato> listarCandidatos() {
    return candidatoRepository.findAll();
  }

  public Candidato buscarCandidatoPorId(Long id) {
    return candidatoRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Candidato não encontrado com o ID: " + id));

  }

  public Candidato salvarCandidato(Candidato candidato) {
    return candidatoRepository.save(candidato);
  }

  public void excluirCandidato(Long id) {
    if (!candidatoRepository.existsById(id)) {
      throw new RuntimeException("Candidato não encontrado com o ID: " + id);
    }
    candidatoRepository.deleteById(id);
  }

  public Candidato atualizarCandidato(Long id, Candidato candidatoAtualizado) {
    return candidatoRepository.findById(id).map(candidatoExistente -> {
      candidatoExistente.setNomeCompleto(candidatoAtualizado.getNomeCompleto());
      candidatoExistente.setEmail(candidatoAtualizado.getEmail());
      candidatoExistente.setTelefone(candidatoAtualizado.getTelefone());
      candidatoExistente.setEndereco(candidatoAtualizado.getEndereco());
      candidatoExistente.setDataNascimento(candidatoAtualizado.getDataNascimento());
      candidatoExistente.setEscolaridade(candidatoAtualizado.getEscolaridade());
      candidatoExistente.setExperienciaProfissional(candidatoAtualizado.getExperienciaProfissional());
      candidatoExistente.setHabilidades(candidatoAtualizado.getHabilidades());
      return candidatoRepository.save(candidatoExistente);
    }).orElseThrow(() -> new RecursoNaoEncontradoException("Candidato não encontrado com o ID: " + id));
  }

  public List<Candidato> buscarCandidatosPorNome(String nome) {
    return candidatoRepository.findByNomeContainingIgnoreCase(nome);
  }

}