package com.example.USUARIOS.controller;

import com.example.USUARIOS.model.Roles;
import com.example.USUARIOS.service.RolesService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(RolesController.class)
public class RolesControllerTest {

    @MockBean
    private RolesService rolesService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllRoles_returnsOKAndJson() throws Exception {
        Roles rolEjemplo = new Roles();
        rolEjemplo.setId_rol(1L);
        rolEjemplo.setNombre("ADMIN");

        List<Roles> listaRoles = Arrays.asList(rolEjemplo);
        when(rolesService.TodosLosRoles()).thenReturn(listaRoles);

        mockMvc.perform(get("/roles"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id_rol").value(1L))
            .andExpect(jsonPath("$[0].nombre").value("ADMIN"));
    }

    @Test
    void getAllRoles_returnsNoContent_whenEmpty() throws Exception {
        when(rolesService.TodosLosRoles()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/roles"))
            .andExpect(status().isNoContent())
            .andExpect(content().string("No hay roles registrados en el sistema"));
    }

    @Test
    void getRoleById_returnsRole_whenRoleExists() throws Exception {
        Roles rolEjemplo = new Roles();
        rolEjemplo.setId_rol(1L);
        rolEjemplo.setNombre("ADMIN");

        when(rolesService.BuscarRolPorId(1L)).thenReturn(rolEjemplo);

        mockMvc.perform(get("/roles/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id_rol").value(1L))
            .andExpect(jsonPath("$.nombre").value("ADMIN"));
    }

    @Test
    void getRoleById_returnsNotFound_whenRoleDoesNotExist() throws Exception {
        when(rolesService.BuscarRolPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/roles/99"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Rol con ID 99 no encontrado"));
    }

    @Test
    void createRole_returnsCreated_whenRoleIsValid() throws Exception {
        Roles nuevoRol = new Roles();
        nuevoRol.setId_rol(2L);
        nuevoRol.setNombre("USER");

        when(rolesService.CrearRol("USER")).thenReturn(nuevoRol);

        String jsonRequest = "{\"nombre\": \"USER\"}";

        mockMvc.perform(post("/roles/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
            .andExpect(status().isCreated())
            .andExpect(content().string("Rol creado exitosamente: USER"));
    }

    @Test
    void createRole_returnsBadRequest_whenNombreIsEmpty() throws Exception {
        String jsonRequest = "{\"nombre\": \"\"}";

        mockMvc.perform(post("/roles/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("El nombre del rol es requerido"));
    }

    @Test
    void updateRole_returnsOk_whenRoleIsUpdated() throws Exception {
        Roles updatedRol = new Roles();
        updatedRol.setId_rol(1L);
        updatedRol.setNombre("MODIFIED");

        when(rolesService.ActualizarRol(eq(1L), eq("MODIFIED"))).thenReturn(updatedRol);

        String jsonRequest = "{\"nombre\": \"MODIFIED\"}";

        mockMvc.perform(put("/roles/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
            .andExpect(status().isOk())
            .andExpect(content().string("Rol actualizado exitosamente: MODIFIED"));
    }

    @Test
    void updateRole_returnsNotFound_whenRoleDoesNotExist() throws Exception {
        when(rolesService.ActualizarRol(eq(99L), anyString()))
            .thenThrow(new RuntimeException("Rol no encontrado99"));

        String jsonRequest = "{\"nombre\": \"MODIFIED\"}";

        mockMvc.perform(put("/roles/actualizar/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Rol con ID 99 no encontrado"));
    }

    @Test
    void deleteRole_returnsOk_whenRoleIsDeleted() throws Exception {
        mockMvc.perform(delete("/roles/eliminar/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Rol con ID 1 eliminado exitosamente"));
    }

    @Test
    void deleteRole_returnsNotFound_whenRoleDoesNotExist() throws Exception {
        doThrow(new RuntimeException("Rol no encontrado1")).when(rolesService).EliminarRol(1L);

        mockMvc.perform(delete("/roles/eliminar/1"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Rol con ID 1 no encontrado"));
    }

}
