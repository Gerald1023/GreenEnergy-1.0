package com.example.CONTRATACIONES.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CONTRATACIONES.model.Contrataciones;

@Repository
public interface ContratacionesRepository extends JpaRepository<Contrataciones, Long> {

}
