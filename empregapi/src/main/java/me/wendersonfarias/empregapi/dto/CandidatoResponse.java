package me.wendersonfarias.empregapi.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoResponse {

  private Long id;
  private String nomeCompleto;
  private String email;
  private String telefone;
  private String endereco;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataNascimento;
  private String escolaridade;
  private String experienciaProfissional;
  private String habilidades;
  private LocalDate dataCadastro;
}
