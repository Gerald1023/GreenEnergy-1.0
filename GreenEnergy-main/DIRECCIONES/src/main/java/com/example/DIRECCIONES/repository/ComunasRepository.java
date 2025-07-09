package com.example.DIRECCIONES.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.DIRECCIONES.model.Comunas;

@Repository
public interface ComunasRepository extends JpaRepository<Comunas, Long> {

    Optional<Comunas> findByNombre(String nombre);

}
