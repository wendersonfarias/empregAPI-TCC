package me.wendersonfarias.empregapi.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import me.wendersonfarias.empregapi.enumeracao.TipoContratacao;

@Data
public class VagaRequest {

  @NotBlank(message = "O título é obrigatório")
  private String titulo;

  @NotBlank(message = "A descrição é obrigatória")
  private String descricao;

  @NotBlank(message = "A localização é obrigatória")
  private String localizacao;

  @NotNull(message = "O tipo de contratação é obrigatório")
  private TipoContratacao tipoContratacao;

  @NotNull(message = "O ID da empresa é obrigatório")
  private Long empresaId;

  @NotBlank(message = "Os requisitos são obrigatórios")
  private String requisitos;

  @NotBlank(message = "Os benefícios são obrigatórios")
  private String beneficios;

  @NotNull(message = "O salário é obrigatório")
  private BigDecimal salario;
}