package com.example.DIRECCIONES.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.DIRECCIONES.model.Direcciones;

@Repository
public interface DireccionesRepository extends JpaRepository<Direcciones, Long> {

}
