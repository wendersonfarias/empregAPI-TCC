package me.wendersonfarias.empregapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import me.wendersonfarias.empregapi.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

  Optional<Empresa> findByCnpj(String cnpj);

}