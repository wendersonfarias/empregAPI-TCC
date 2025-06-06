package me.wendersonfarias.empregapi.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;
import lombok.Data;

@Data
@Entity
@Table(name = "candidato")
public class Candidato {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome_completo", nullable = false, length = 50)
  @NotBlank(message = "Nome é obrigatório")
  private String nomeCompleto;

  @Column(name = "senha", nullable = false)
  private String senha;

  @Column(name = "email", nullable = false, unique = true, length = 100)
  @NotBlank(message = "Email é obrigatório")
  @Email(message = "Email inválido")
  private String email;

  @Column(name = "telefone", nullable = false, length = 20)
  @NotBlank(message = "Telefone é obrigatório")
  private String telefone;

  @Column(name = "endereco", length = 150)
  private String endereco;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @Column(name = "data_nascimento", nullable = false)
  @NotNull(message = "Data de nascimento é obrigatória")
  private LocalDate dataNascimento;

  @Column(name = "escolaridade", nullable = false)
  private String escolaridade;

  @Column(name = "experiencia_profissional", nullable = false)
  private String experienciaProfissional;

  @Column(name = "habilidades", nullable = false)
  private String habilidades;

  @Column(name = "data_cadastro", nullable = false)
  private LocalDate dataCadastro;

  @PrePersist
  protected void onCreate() {
    this.dataCadastro = LocalDate.now();
  }

}
