package me.wendersonfarias.empregapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.wendersonfarias.empregapi.model.Inscricao;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

  Optional<Inscricao> findByCandidatoIdAndVagaId(Long candidatoId, Long vagaId);

  List<Inscricao> findByVagaId(Long vagaId);
}