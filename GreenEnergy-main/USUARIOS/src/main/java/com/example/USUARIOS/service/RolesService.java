package com.example.USUARIOS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.USUARIOS.model.Roles;
import com.example.USUARIOS.repository.RolesRepository;

@Service
@Transactional
public class RolesService {
    @Autowired
    private RolesRepository rolesRepository;
    //get
    //metodo para buscar todos los roles
    public List<Roles> TodosLosRoles() {
        return rolesRepository.findAll();
    }
    //metodo para buscar un rol por id
    public Roles BuscarRolPorId(Long id) {
        return rolesRepository.findById(id).orElse(null);
    }
    //post
    //metodo para crear un rol nuevo
    public Roles CrearRol(String nombre) {
        Roles nuevoRol = new Roles();
        nuevoRol.setNombre(nombre);
        return rolesRepository.save(nuevoRol);
    }
    //put
    //metodo para actualizar un rol
    public Roles ActualizarRol(Long id, String nombre) {
        Roles rolExistente = rolesRepository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado" + id));
        rolExistente.setNombre(nombre);
        return rolesRepository.save(rolExistente);
    }
    //delete
    //metodo para eliminar un rol
    public void EliminarRol(Long id) {
        Roles rol = rolesRepository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado" + id));
        rolesRepository.delete(rol);
    }
    
}
