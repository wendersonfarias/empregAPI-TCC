package me.wendersonfarias.empregapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmpresaResponse {

  private Long id;
  private String nome;
  private String cnpj;
  private String description;
  private String website;
  private String email;
}