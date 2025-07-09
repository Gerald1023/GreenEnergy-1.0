package com.example.USUARIOS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.USUARIOS.model.Roles;
import com.example.USUARIOS.model.Usuarios;
import com.example.USUARIOS.repository.RolesRepository;
import com.example.USUARIOS.repository.UsuariosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LoadDataConfig {

    @Bean
    CommandLineRunner initDatabase(RolesRepository rolesRepository, UsuariosRepository usuariosRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (rolesRepository.count() == 0 && usuariosRepository.count() == 0) {
                
                // Crear los roles
                Roles admin = new Roles();
                admin.setNombre("Administrador");
                rolesRepository.save(admin);

                Roles cliente = new Roles();
                cliente.setNombre("Cliente");
                rolesRepository.save(cliente);

                Roles tecnico = new Roles();
                tecnico.setNombre("Técnico");
                rolesRepository.save(tecnico);

                Roles Soporte = new Roles();
                Soporte.setNombre("Soporte");
                rolesRepository.save(Soporte);

                Roles Coordinador = new Roles();
                Coordinador.setNombre("Coordinador");
                rolesRepository.save(Coordinador);

                // Creamos un usuario administrador de ejemplo
                Usuarios usuarioAdmin = new Usuarios();
                usuarioAdmin.setNombre("Admin");
                usuarioAdmin.setApellido("Sistema");
                usuarioAdmin.setEmail("adminSistem@greenenergy.com");
                usuarioAdmin.setContrasena(passwordEncoder.encode("admin123"));
                usuarioAdmin.setTelefono("923456789");
                usuarioAdmin.setRol(admin);
                usuarioAdmin.setId_direccion(1L); // direcciones
                usuariosRepository.save(usuarioAdmin);

                //Creamos un usuario cliente de ejemplo
                Usuarios usuarioCliente = new Usuarios();
                usuarioCliente.setNombre("Francisco");
                usuarioCliente.setApellido("Quintana");
                usuarioCliente.setEmail("FraQuintana@gmail.com");
                usuarioCliente.setContrasena(passwordEncoder.encode("cliente123"));
                usuarioCliente.setTelefono("987654321");
                usuarioCliente.setRol(cliente);
                usuarioCliente.setId_direccion(2L); 
                usuariosRepository.save(usuarioCliente);
                
                System.out.println("Datos iniciales cargados");
            } else {
                System.out.println("Los datos ya existen. No se cargaron nuevos datos.");
            }
        };
    }
}
