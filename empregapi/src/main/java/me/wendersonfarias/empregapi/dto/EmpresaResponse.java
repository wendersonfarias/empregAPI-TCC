package me.wendersonfarias.empregapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaResponse {

  private Long id;
  private String nome;
  private String cnpj;
  private String descricao;
  private String website;
  private String email;
}