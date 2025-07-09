package com.example.USUARIOS.controller;

import com.example.USUARIOS.model.Usuarios;
import com.example.USUARIOS.model.Roles;
import com.example.USUARIOS.service.UsuariosService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc(addFilters = false) 
@WebMvcTest(UsuariosController.class)
public class UsuariosControllerTest {

    @MockBean
    private UsuariosService usuariosService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllUsuarios_returnsOKAndJson() throws Exception {
        List<Usuarios> lista = Arrays.asList(new Usuarios());
        Mockito.when(usuariosService.TodosLosUsuarios()).thenReturn(lista);

        mockMvc.perform(get("/usuarios").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getUsuarioById_returnsOKAndJson() throws Exception {
        Usuarios usuario = new Usuarios();
        usuario.setId_usuario(1L);
        Mockito.when(usuariosService.BuscarUsuarioPorId(1L)).thenReturn(usuario);

        mockMvc.perform(get("/usuarios/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createUsuario_returnsCreated() throws Exception {
        Usuarios usuario = new Usuarios();
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan@correo.com");
        usuario.setContrasena("1234");
        usuario.setTelefono("123456789");
        usuario.setId_direccion(1L);
        Roles rol = new Roles();
        rol.setId_rol(1L);
        usuario.setRol(rol);

        Mockito.when(usuariosService.crearUsuarios(any(Usuarios.class))).thenReturn(usuario);

        String json = "{" +
                "\"nombre\":\"Juan\"," +
                "\"apellido\":\"Pérez\"," +
                "\"email\":\"juan@correo.com\"," +
                "\"contrasena\":\"1234\"," +
                "\"telefono\":\"123456789\"," +
                "\"id_rol\":1," +
                "\"id_direccion\":1}";

        mockMvc.perform(post("/usuarios/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void updateUsuario_returnsOK() throws Exception {
        Usuarios usuario = new Usuarios();
        usuario.setNombre("Juan");

        Mockito.when(usuariosService.actualizarUsuario(eq(1L), any(Usuarios.class))).thenReturn(usuario);

        String json = "{\"nombre\":\"Juan\"}";

        mockMvc.perform(put("/usuarios/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUsuario_returnsNoContent() throws Exception {
        Mockito.doNothing().when(usuariosService).eliminarUsuario(1L);

        mockMvc.perform(delete("/usuarios/eliminar/1"))
                .andExpect(status().isOk());
    }

    @Test
    void login_returnsOK() throws Exception {
        Usuarios usuario = new Usuarios();
        usuario.setEmail("juan@correo.com");
        usuario.setContrasena("1234");

        Mockito.when(usuariosService.login("juan@correo.com", "1234")).thenReturn(usuario);

        String json = "{\"email\":\"juan@correo.com\",\"contrasena\":\"1234\"}";

        mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}