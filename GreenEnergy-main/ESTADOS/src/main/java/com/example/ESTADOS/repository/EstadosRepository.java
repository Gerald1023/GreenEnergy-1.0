package com.example.ESTADOS.repository;

import com.example.ESTADOS.model.Estados;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EstadosRepository extends JpaRepository<Estados, Long> {
    List<Estados> findByActivoTrue();
    Optional<Estados> findByNombreAndActivoTrue(String nombre);
    List<Estados> findByNombreContainingAndActivoTrue(String nombre);
    boolean existsByNombreAndActivoTrue(String nombre);
}
