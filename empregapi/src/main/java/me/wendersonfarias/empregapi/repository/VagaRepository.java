package me.wendersonfarias.empregapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.wendersonfarias.empregapi.model.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

  List<Vaga> findByEmpresaId(Long empresaId);
}
