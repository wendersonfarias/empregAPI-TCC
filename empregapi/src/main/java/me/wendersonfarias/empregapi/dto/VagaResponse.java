package me.wendersonfarias.empregapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wendersonfarias.empregapi.enumeracao.StatusVaga;
import me.wendersonfarias.empregapi.enumeracao.TipoContratacao;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaResponse {
  private Long id;
  private String titulo;
  private String descricao;
  private String localizacao;
  private TipoContratacao tipoContratacao;
  private StatusVaga status;
  private LocalDate dataPublicacao;
  private EmpresaSimplificadoDTO empresa;
  private String requisitos;
  private String beneficios;
  private BigDecimal salario;

  @Data
  public static class EmpresaSimplificadoDTO {
    private Long id;
    private String nome;
  }
}