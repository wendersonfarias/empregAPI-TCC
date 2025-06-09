package me.wendersonfarias.empregapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import me.wendersonfarias.empregapi.enumeracao.StatusVaga;
import me.wendersonfarias.empregapi.enumeracao.TipoContratacao;

@Entity
@Table(name = "vaga")
@Data
public class Vaga {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O título da vaga é obrigatório")
  @Column(nullable = false)
  private String titulo;

  @NotBlank(message = "A descrição da vaga é obrigatória")
  @Column(columnDefinition = "TEXT", nullable = false)
  private String descricao;

  @NotBlank(message = "A localização da vaga é obrigatória")
  private String localizacao;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TipoContratacao tipoContratacao;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StatusVaga status;

  @Column(name = "data_publicacao", nullable = false, updatable = false)
  private LocalDate dataPublicacao;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "empresa_id", nullable = false)
  private Empresa empresa;

  @NotBlank(message = "Os requisitos são obrigatórios")
  private String requisitos;

  @NotBlank(message = "Os benefícios são obrigatórios")
  private String beneficios;

  @NotNull(message = "O salário é obrigatório")
  private BigDecimal salario;

  @PrePersist
  protected void onCreate() {
    this.dataPublicacao = LocalDate.now();
    this.status = StatusVaga.ABERTA;
  }
}