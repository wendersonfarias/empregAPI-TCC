package me.wendersonfarias.empregapi.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CandidatoRequest {

  @NotBlank
  private String nomeCompleto;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String telefone;

  private String endereco;

  @NotNull
  private LocalDate dataNascimento;

  @NotBlank
  private String escolaridade;

  @NotBlank
  private String experienciaProfissional;

  @NotBlank
  private String habilidades;
}
