package com.example.USUARIOS.service;

import com.example.USUARIOS.WebClient.DireccionesClient;
import com.example.USUARIOS.model.Roles;
import com.example.USUARIOS.model.Usuarios;
import com.example.USUARIOS.repository.RolesRepository;
import com.example.USUARIOS.repository.UsuariosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuariosServiceTest {
    @Mock
    private UsuariosRepository usuariosRepository;
    @Mock
    private RolesRepository rolesRepository;
    @Mock
    private DireccionesClient direccionesClient;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UsuariosService usuariosService;

    @Test
    void TodosLosUsuarios_returnsList() {
        Usuarios u = new Usuarios();
        when(usuariosRepository.findAll()).thenReturn(Arrays.asList(u));
        List<Usuarios> result = usuariosService.TodosLosUsuarios();
        assertThat(result).hasSize(1).contains(u);
    }

    @Test
    void BuscarUsuarioPorId_returnsUsuario() {
        Usuarios u = new Usuarios();
        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(u));
        Usuarios result = usuariosService.BuscarUsuarioPorId(1L);
        assertThat(result).isEqualTo(u);
    }

    @Test
    void crearUsuarios_returnsSavedUsuario() {
        Usuarios u = new Usuarios();
        u.setNombre("Juan");
        u.setApellido("Pérez");
        u.setEmail("juan@correo.com");
        u.setContrasena("1234");
        u.setTelefono("123456789");
        u.setId_direccion(1L);
        Roles rol = new Roles();
        rol.setId_rol(1L);
        u.setRol(rol);
        when(rolesRepository.findById(1L)).thenReturn(Optional.of(rol));
        when(direccionesClient.existeDireccion(1L)).thenReturn(true);
        when(passwordEncoder.encode("1234")).thenReturn("hashed");
        when(usuariosRepository.save(any(Usuarios.class))).thenReturn(u);
        Usuarios result = usuariosService.crearUsuarios(u);
        assertThat(result).isEqualTo(u);
    }

    @Test
    void actualizarUsuario_returnsUpdatedUsuario() {
        Usuarios existente = new Usuarios();
        existente.setId_usuario(1L);
        existente.setNombre("Juan");
        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuariosRepository.save(any(Usuarios.class))).thenReturn(existente);
        Usuarios actualizado = new Usuarios();
        actualizado.setNombre("Pedro");
        Usuarios result = usuariosService.actualizarUsuario(1L, actualizado);
        assertThat(result.getNombre()).isEqualTo("Pedro");
    }

    @Test
    void eliminarUsuario_executesWithoutException() {
        Usuarios u = new Usuarios();
        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(u));
        Mockito.doNothing().when(usuariosRepository).delete(u);
        usuariosService.eliminarUsuario(1L);
        Mockito.verify(usuariosRepository).delete(u);
    }

    @Test
    void login_returnsUsuario() {
        Usuarios u = new Usuarios();
        u.setEmail("juan@correo.com");
        u.setContrasena("hashed");
        when(usuariosRepository.findByEmail("juan@correo.com")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("1234", "hashed")).thenReturn(true);
        Usuarios result = usuariosService.login("juan@correo.com", "1234");
        assertThat(result).isEqualTo(u);
    }
} 