package com.example.PROYECTOS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PROYECTOS.model.Proyectos;

@Repository
public interface ProyectosRepository extends JpaRepository<Proyectos, Long> {

} 