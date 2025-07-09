package com.example.CONTRATACIONES.controller;

import com.example.CONTRATACIONES.model.Contrataciones;
import com.example.CONTRATACIONES.service.ContratacionesService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContratacionesController.class)
public class ContratacionesControllerTest {
    @MockBean
    private ContratacionesService contratacionesService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTodasLasContrataciones_returnsOKAndJson() throws Exception {
        List<Contrataciones> lista = Arrays.asList(
                new Contrataciones(1L, LocalDate.now(), "desc", 1L, 1L, 1L, 1L)
        );
        when(contratacionesService.obtenerTodasLasContrataciones()).thenReturn(lista);
        mockMvc.perform(get("/contrataciones").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_contratacion").value(1L));
    }

    @Test
    void getContratacionPorId_returnsOKAndJson() throws Exception {
        Contrataciones contratacion = new Contrataciones(1L, LocalDate.now(), "desc", 1L, 1L, 1L, 1L);
        when(contratacionesService.obtenerContratacionPorId(1L)).thenReturn(contratacion);
        mockMvc.perform(get("/contrataciones/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_contratacion").value(1L));
    }

    @Test
    void getContratacionesPorUsuario_returnsOKAndJson() throws Exception {
        List<Contrataciones> lista = Arrays.asList(
                new Contrataciones(2L, LocalDate.now(), "desc", 2L, 1L, 1L, 1L)
        );
        when(contratacionesService.obtenerContratacionesPorUsuario(2L)).thenReturn(lista);
        mockMvc.perform(get("/contrataciones/usuario/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_contratacion").value(2L));
    }

    @Test
    void getContratacionesPorProyecto_returnsOKAndJson() throws Exception {
        List<Contrataciones> lista = Arrays.asList(
                new Contrataciones(3L, LocalDate.now(), "desc", 3L, 1L, 3L, 1L)
        );
        when(contratacionesService.obtenerContratacionesPorProyecto(3L)).thenReturn(lista);
        mockMvc.perform(get("/contrataciones/proyecto/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_contratacion").value(3L));
    }

    @Test
    void postCrearContratacion_returnsCreatedAndJson() throws Exception {
        Contrataciones nueva = new Contrataciones(4L, LocalDate.now(), "desc", 4L, 1L, null, 1L);
        when(contratacionesService.crearContratacion(Mockito.any(Contrataciones.class))).thenReturn(nueva);
        String json = "{\"descripcion\":\"desc\",\"id_usuario\":4,\"id_servicio\":1}";
        mockMvc.perform(post("/contrataciones/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_contratacion").value(4L));
    }

    @Test
    void putActualizarEstadoContratacion_returnsOKAndJson() throws Exception {
        Contrataciones actualizada = new Contrataciones(5L, LocalDate.now(), "desc", 5L, 1L, null, 2L);
        when(contratacionesService.actualizarEstadoContratacion(5L, 2L)).thenReturn(actualizada);
        mockMvc.perform(put("/contrataciones/5/estado")
                .param("nuevoEstadoId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_contratacion").value(5L));
    }

    @Test
    void putAsignarProyectoAContratacion_returnsOKAndJson() throws Exception {
        Contrataciones actualizada = new Contrataciones(6L, LocalDate.now(), "desc", 6L, 1L, 10L, 2L);
        when(contratacionesService.asignarProyectoAContratacion(6L, 10L)).thenReturn(actualizada);
        mockMvc.perform(put("/contrataciones/6/asignarP")
                .param("idProyecto", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_contratacion").value(6L));
    }

    @Test
    void deleteEliminarContratacion_returnsNoContent() throws Exception {
        Mockito.doNothing().when(contratacionesService).eliminarContratacion(7L);
        mockMvc.perform(delete("/contrataciones/eliminar/7"))
                .andExpect(status().isNoContent());
    }
} 