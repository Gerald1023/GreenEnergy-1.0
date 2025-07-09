package com.example.DIRECCIONES.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.DIRECCIONES.model.Regiones;

@Repository
public interface RegionesRepository extends JpaRepository<Regiones, Long> {
    
    Optional<Regiones> findByNombre(String nombre);

}
