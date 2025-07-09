package com.example.PROYECTOS.controller;

import com.example.PROYECTOS.model.Proyectos;
import com.example.PROYECTOS.service.ProyectosService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProyectosController.class)
public class ProyectosControllerTest {
    @MockBean
    private ProyectosService proyectosService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTodosLosProyectos_returnsOKAndJson() throws Exception {
        List<Proyectos> lista = Arrays.asList(new Proyectos());
        when(proyectosService.obtenerTodosLosProyectos()).thenReturn(lista);
        mockMvc.perform(get("/proyectos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getProyectoPorId_returnsOKAndJson() throws Exception {
        Proyectos proyecto = new Proyectos();
        proyecto.setId_proyecto(1L);
        when(proyectosService.obtenerProyectoPorId(1L)).thenReturn(proyecto);
        mockMvc.perform(get("/proyectos/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void postCrearProyecto_returnsCreated() throws Exception {
        Proyectos proyecto = new Proyectos();
        proyecto.setNombre("Nuevo Proyecto");
        proyecto.setFecha_inicio(LocalDate.now());
        proyecto.setFecha_fin(LocalDate.now().plusDays(1));
        proyecto.setId_contratacion(1L);
        proyecto.setId_estado(1L);
        when(proyectosService.crearProyecto(any(Proyectos.class))).thenReturn(proyecto);
        String json = "{\"nombre\":\"Nuevo Proyecto\",\"fecha_inicio\":\"2024-01-01\",\"fecha_fin\":\"2024-01-02\",\"id_contratacion\":1,\"id_estado\":1}";
        mockMvc.perform(post("/proyectos/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void putActualizarProyecto_returnsOK() throws Exception {
        Proyectos proyecto = new Proyectos();
        proyecto.setNombre("Actualizado");
        when(proyectosService.actualizarProyecto(Mockito.eq(1L), any(Proyectos.class))).thenReturn(proyecto);
        String json = "{\"nombre\":\"Actualizado\"}";
        mockMvc.perform(put("/proyectos/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void putActualizarEstadoProyecto_returnsOK() throws Exception {
        Proyectos proyecto = new Proyectos();
        proyecto.setId_estado(2L);
        when(proyectosService.actualizarEstadoProyecto(1L, 2L)).thenReturn(proyecto);
        mockMvc.perform(put("/proyectos/1/estado")
                .param("idEstado", "2"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEliminarProyecto_returnsNoContent() throws Exception {
        Mockito.doNothing().when(proyectosService).eliminarProyecto(1L);
        mockMvc.perform(delete("/proyectos/eliminar/1"))
                .andExpect(status().isNoContent());
    }
} 