package me.wendersonfarias.empregapi.dto;

import java.time.LocalDateTime;

import lombok.Data;
import me.wendersonfarias.empregapi.enumeracao.StatusInscricao;

@Data
public class InscricaoDetalhesDTO {
  private Long numeroInscricao;
  private LocalDateTime dataInscricao;
  private StatusInscricao status;
  private CandidatoSimplificadoDTO candidato;

  @Data
  public static class CandidatoSimplificadoDTO {
    private Long id;
    private String nomeCompleto;
    private String email;
    private String telefone;
    private String habilidades;
    private String experienciaProfissional;
    private String escolaridade;
  }
}