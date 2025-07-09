package com.example.SERVICIOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SERVICIOS.model.Servicios;

@Repository
public interface ServiciosRepository extends JpaRepository<Servicios, Long> {
}
