package com.example.USUARIOS.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.USUARIOS.model.Roles;


@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

}
