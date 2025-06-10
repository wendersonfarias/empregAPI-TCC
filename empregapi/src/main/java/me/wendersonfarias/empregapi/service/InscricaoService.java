package me.wendersonfarias.empregapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.wendersonfarias.empregapi.dto.InscricaoDetalhesDTO;
import me.wendersonfarias.empregapi.enumeracao.StatusVaga;
import me.wendersonfarias.empregapi.exception.InscricaoJaRealizadaException;
import me.wendersonfarias.empregapi.exception.RecursoNaoEncontradoException;
import me.wendersonfarias.empregapi.model.Candidato;
import me.wendersonfarias.empregapi.model.Inscricao;
import me.wendersonfarias.empregapi.model.Usuario;
import me.wendersonfarias.empregapi.model.Vaga;
import me.wendersonfarias.empregapi.repository.CandidatoRepository;
import me.wendersonfarias.empregapi.repository.InscricaoRepository;
import me.wendersonfarias.empregapi.repository.UsuarioRepository;
import me.wendersonfarias.empregapi.repository.VagaRepository;

@Service
@RequiredArgsConstructor
public class InscricaoService {

  private final InscricaoRepository inscricaoRepository;
  private final VagaRepository vagaRepository;
  private final CandidatoRepository candidatoRepository;
  private final UsuarioRepository usuarioRepository;

  public void inscrever(Long vagaId, String emailDoUsuarioLogado) {

    Usuario usuario = usuarioRepository.findByEmail(emailDoUsuarioLogado)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

    Candidato candidato = candidatoRepository.findByUsuarioId(usuario.getId())
        .orElseThrow(() -> new RecursoNaoEncontradoException("Perfil de candidato não encontrado para o usuário"));

    Vaga vaga = vagaRepository.findById(vagaId)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Vaga não encontrada"));

    if (vaga.getStatus() != StatusVaga.ABERTA) {
      throw new IllegalStateException("Não é possível se inscrever em uma vaga que não está aberta.");
    }

    inscricaoRepository.findByCandidatoIdAndVagaId(candidato.getId(), vaga.getId())
        .ifPresent(i -> {
          throw new InscricaoJaRealizadaException("Candidato já inscrito nesta vaga.");
        });

    Inscricao novaInscricao = new Inscricao();
    novaInscricao.setCandidato(candidato);
    novaInscricao.setVaga(vaga);

    inscricaoRepository.save(novaInscricao);
  }

  public List<InscricaoDetalhesDTO> listarInscricoesPorVaga(Long vagaId, String emailEmpresaLogada) {
    Vaga vaga = vagaRepository.findById(vagaId)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Vaga não encontrada"));

    if (!vaga.getEmpresa().getUsuario().getEmail().equals(emailEmpresaLogada)) {
      throw new org.springframework.security.access.AccessDeniedException(
          "Acesso negado: esta vaga não pertence à sua empresa.");
    }

    return inscricaoRepository.findByVagaId(vagaId)
        .stream()
        .map(this::toDetalhesDTO)
        .collect(Collectors.toList());
  }

  private InscricaoDetalhesDTO toDetalhesDTO(Inscricao inscricao) {
    InscricaoDetalhesDTO dto = new InscricaoDetalhesDTO();
    dto.setNumeroInscricao(inscricao.getId());
    dto.setDataInscricao(inscricao.getDataInscricao());
    dto.setStatus(inscricao.getStatus());

    InscricaoDetalhesDTO.CandidatoSimplificadoDTO candidatoDTO = new InscricaoDetalhesDTO.CandidatoSimplificadoDTO();
    candidatoDTO.setId(inscricao.getCandidato().getId());
    candidatoDTO.setNomeCompleto(inscricao.getCandidato().getNomeCompleto());
    candidatoDTO.setEmail(inscricao.getCandidato().getUsuario().getEmail());
    candidatoDTO.setTelefone(inscricao.getCandidato().getTelefone());
    candidatoDTO.setHabilidades(inscricao.getCandidato().getHabilidades());
    candidatoDTO.setExperienciaProfissional(inscricao.getCandidato().getExperienciaProfissional());
    candidatoDTO.setEscolaridade(inscricao.getCandidato().getEscolaridade());

    dto.setCandidato(candidatoDTO);
    return dto;
  }

}