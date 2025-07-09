package com.example.ESTADOS.controller;

import com.example.ESTADOS.model.Estados;
import com.example.ESTADOS.service.EstadosService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstadosController.class)
public class EstadosControllerTest {
    @MockBean
    private EstadosService estadosService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTodosLosEstados_returnsOKAndJson() throws Exception {
        List<Estados> lista = Arrays.asList(new Estados());
        when(estadosService.obtenerTodosLosEstados()).thenReturn(lista);
        mockMvc.perform(get("/estados").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getEstadoPorId_returnsOKAndJson() throws Exception {
        Estados estado = new Estados();
        estado.setId(1L);
        when(estadosService.obtenerEstadoPorId(1L)).thenReturn(estado);
        mockMvc.perform(get("/estados/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBuscarPorNombre_returnsOKAndJson() throws Exception {
        List<Estados> lista = Arrays.asList(new Estados());
        when(estadosService.buscarPorNombre("Activo")).thenReturn(lista);
        mockMvc.perform(get("/estados/buscar").param("nombre", "Activo").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void postCrearEstado_returnsCreated() throws Exception {
        Estados estado = new Estados();
        estado.setNombre("Nuevo Estado");
        when(estadosService.crearEstado(any(Estados.class))).thenReturn(estado);
        String json = "{\"nombre\":\"Nuevo Estado\"}";
        mockMvc.perform(post("/estados/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void putActualizarEstado_returnsOK() throws Exception {
        Estados estado = new Estados();
        estado.setNombre("Actualizado");
        when(estadosService.actualizarEstado(Mockito.eq(1L), any(Estados.class))).thenReturn(estado);
        String json = "{\"nombre\":\"Actualizado\"}";
        mockMvc.perform(put("/estados/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEliminarEstado_returnsNoContent() throws Exception {
        Mockito.doNothing().when(estadosService).eliminarEstado(1L);
        mockMvc.perform(delete("/estados/eliminar/1"))
                .andExpect(status().isNoContent());
    }
} 