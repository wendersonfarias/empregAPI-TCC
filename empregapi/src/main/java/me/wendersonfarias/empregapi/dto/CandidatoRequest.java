package me.wendersonfarias.empregapi.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoRequest {

  @NotBlank(message = "O nome completo é obrigatório.")
  private String nomeCompleto;

  @NotBlank(message = "O e-mail é obrigatório.")
  @Email(message = "O formato do e-mail é inválido.")
  private String email;

  @NotBlank(message = "O telefone é obrigatório.")
  private String telefone;

  private String endereco; // Campo opcional, sem validação

  @NotNull(message = "A data de nascimento é obrigatória.")
  @Past(message = "A data de nascimento deve ser uma data no passado.")
  private LocalDate dataNascimento;

  @NotBlank(message = "A escolaridade é obrigatória.")
  private String escolaridade;

  @NotBlank(message = "A experiência profissional é obrigatória.")
  private String experienciaProfissional;

  @NotBlank(message = "As habilidades são obrigatórias.")
  private String habilidades;

  @NotBlank(message = "A senha é obrigatória.")
  @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
  private String senha;

}