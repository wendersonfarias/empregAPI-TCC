package me.wendersonfarias.empregapi.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidatoResponse {

  private Long id;
  private String nomeCompleto;
  private String email;
  private String telefone;
  private String endereco;
  private LocalDate dataNascimento;
  private String escolaridade;
  private String experienciaProfissional;
  private String habilidades;
  private LocalDate dataCadastro;
}
