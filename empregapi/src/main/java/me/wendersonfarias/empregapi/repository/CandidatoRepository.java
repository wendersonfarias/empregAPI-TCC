package me.wendersonfarias.empregapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import me.wendersonfarias.empregapi.model.Candidato;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

  List<Candidato> findByNomeCompletoContainingIgnoreCase(String nome);

  Optional<Candidato> findByUsuarioId(Long usuarioId);

}
