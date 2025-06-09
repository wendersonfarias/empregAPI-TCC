package me.wendersonfarias.empregapi.model;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wendersonfarias.empregapi.validacao.CNPJValido;

@Entity
@Table(name = "empresa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O nome da empresa é obrigatório")
  @Size(max = 100, message = "O nome da empresa deve ter no máximo 100 caracteres")
  @Column(nullable = false, length = 100)
  private String nome;

  @NotBlank(message = "O CNPJ é obrigatório")
  @CNPJValido
  @Column(unique = true, nullable = false, length = 18)
  private String cnpj;

  @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
  @Column(columnDefinition = "TEXT")
  private String description;

  @URL(message = "O formato do website é inválido")
  private String website;

  @NotBlank(message = "O e-mail é obrigatório")
  @Email(message = "O formato do e-mail é inválido")
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank(message = "A senha é obrigatória")
  @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
  @Column(nullable = false)
  private String senha;

  @Column(name = "data_cadastro", nullable = false)
  private LocalDate dataCadastro;

  @PrePersist
  protected void onCreate() {
    this.dataCadastro = LocalDate.now();
  }
}
