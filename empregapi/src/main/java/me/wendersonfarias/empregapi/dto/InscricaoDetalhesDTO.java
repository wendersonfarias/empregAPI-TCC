package me.wendersonfarias.empregapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wendersonfarias.empregapi.enumeracao.StatusInscricao;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscricaoDetalhesDTO {
  private Long numeroInscricao;
  private LocalDateTime dataInscricao;
  private StatusInscricao status;
  private CandidatoSimplificadoDTO candidato;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
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