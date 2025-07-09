package com.example.RESENA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RESENA.model.Resena;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {

} 