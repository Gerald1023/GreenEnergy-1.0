package com.example.USUARIOS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.USUARIOS.WebClient.DireccionesClient;
import com.example.USUARIOS.model.Roles;
import com.example.USUARIOS.model.Usuarios;

import com.example.USUARIOS.repository.RolesRepository;
import com.example.USUARIOS.repository.UsuariosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuariosService {
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private DireccionesClient direccionesClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //get
    //metodo para buscar todos usuario 
    public List<Usuarios> TodosLosUsuarios() {
        return usuariosRepository.findAll();
    }   

    //metodo para buscar un usuario por id
    public Usuarios BuscarUsuarioPorId(Long id) {
        return usuariosRepository.findById(id).orElse(null);
    }
    //post
    //metodo para crear un usuario nuevo con su direccion
    public Usuarios crearUsuarios(Usuarios usuario){
        // metodo para crear un nuevo usuario
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre es requerido");
        }
        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            throw new RuntimeException("El apellido es requerido");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new RuntimeException("El correo es requerido");
        }
        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
            throw new RuntimeException("La contraseña es requerida");
        }
        if (usuario.getTelefono() == null || usuario.getTelefono().trim().isEmpty()) {
            throw new RuntimeException("El teléfono es requerido");
        }

        // metodo para validar que el rol exista en la BD
        if (usuario.getRol() == null || usuario.getRol().getId_rol() == null) {
            throw new RuntimeException("El rol es requerido");
        }
        Optional<Roles> rolExistente = rolesRepository.findById(usuario.getRol().getId_rol());
        if (rolExistente.isEmpty()) {
            throw new RuntimeException("El rol con ID " + usuario.getRol().getId_rol() + " no existe.");
        }
        usuario.setRol(rolExistente.get());
        // metodo para validar que la dirección exista usando el microservicio de direcciones
        if (usuario.getId_direccion() == null) {
            throw new RuntimeException("Falta La dirección ");
        }
        if (!direccionesClient.existeDireccion(usuario.getId_direccion())) {
            throw new RuntimeException("La dirección con ID " + usuario.getId_direccion() + " no existe.");
        }
        
        // metodo para encriptar la contraseña antes de guardar
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        
        // metodo para guardar usuario  si cumple con todo 
        return usuariosRepository.save(usuario);
    }
        
    // put
    //metodo para actualizar usuario
    public Usuarios actualizarUsuario(Long id, Usuarios usuarioActualizado) {
        Usuarios usuarioExistente = usuariosRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));

        // metodo para validar que los campos requeridos
        if (usuarioActualizado.getNombre() != null && !usuarioActualizado.getNombre().trim().isEmpty()) {
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
        }
        if (usuarioActualizado.getApellido() != null && !usuarioActualizado.getApellido().trim().isEmpty()) {
            usuarioExistente.setApellido(usuarioActualizado.getApellido());
        }
        if (usuarioActualizado.getEmail() != null && !usuarioActualizado.getEmail().trim().isEmpty()) {
            usuarioExistente.setEmail(usuarioActualizado.getEmail());
        }
        if (usuarioActualizado.getTelefono() != null && !usuarioActualizado.getTelefono().trim().isEmpty()) {
            usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
        }
        if (usuarioActualizado.getContrasena() != null && !usuarioActualizado.getContrasena().trim().isEmpty()) {
            usuarioExistente.setContrasena(passwordEncoder.encode(usuarioActualizado.getContrasena()));
        }

        // metodo para validar que el rol si se proporciona
        if (usuarioActualizado.getRol() != null && usuarioActualizado.getRol().getId_rol() != null) {
            Optional<Roles> rolExistente = rolesRepository.findById(usuarioActualizado.getRol().getId_rol());
            if (rolExistente.isEmpty()) {
                throw new RuntimeException("El rol con ID " + usuarioActualizado.getRol().getId_rol() + " no existe.");
            }
            usuarioExistente.setRol(rolExistente.get());
        }

        // metodo para validar que la dirección si se proporciona
        if (usuarioActualizado.getId_direccion() != null) {
            if (!direccionesClient.existeDireccion(usuarioActualizado.getId_direccion())) {
                throw new RuntimeException("La dirección con ID " + usuarioActualizado.getId_direccion() + " no existe.");
            }
            usuarioExistente.setId_direccion(usuarioActualizado.getId_direccion());
        }

        return usuariosRepository.save(usuarioExistente);
    }

    // delete
    // metodo para eliminar usuario
    public void eliminarUsuario(Long id) {
        Usuarios usuario = usuariosRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));

        usuariosRepository.delete(usuario);
    }



    // post
    //metodos para validar el usuario
    public Usuarios login(String email, String contrasena) {
        Usuarios usuario = usuariosRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Correo no registrado")); 

        boolean coincide = passwordEncoder.matches(contrasena, usuario.getContrasena()); 

        if (!coincide) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }
}



