package me.wendersonfarias.empregapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import me.wendersonfarias.empregapi.enumeracao.StatusInscricao;

@Entity
@Table(name = "inscricao", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "candidato_id", "vaga_id" })
})
@Data
public class Inscricao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "candidato_id", nullable = false)
  private Candidato candidato;

  @ManyToOne
  @JoinColumn(name = "vaga_id", nullable = false)
  private Vaga vaga;

  @Column(name = "data_inscricao", nullable = false, updatable = false)
  private LocalDateTime dataInscricao;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StatusInscricao status;

  @PrePersist
  protected void onCreate() {
    this.dataInscricao = LocalDateTime.now();
    this.status = StatusInscricao.INSCRITO;
  }
}