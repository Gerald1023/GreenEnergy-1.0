package com.example.DIRECCIONES.controller;

import com.example.DIRECCIONES.model.Comunas;
import com.example.DIRECCIONES.service.ComunasService;
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

@WebMvcTest(ComunasController.class)
public class ComunasControllerTest {
    @MockBean
    private ComunasService comunasService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllComunas_returnsOKAndJson() throws Exception {
        List<Comunas> lista = Arrays.asList(new Comunas());
        when(comunasService.verTodasLasComunas()).thenReturn(lista);
        mockMvc.perform(get("/comunas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getComunaById_returnsOKAndJson() throws Exception {
        Comunas comuna = new Comunas();
        comuna.setId_comuna(1L);
        when(comunasService.buscarComunaPorId(1L)).thenReturn(comuna);
        mockMvc.perform(get("/comunas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void postCrearComuna_returnsCreated() throws Exception {
        Comunas comuna = new Comunas();
        comuna.setNombre("Santiago");
        when(comunasService.crearComuna(any(String.class))).thenReturn(comuna);
        String json = "{\"nombre\":\"Santiago\"}";
        mockMvc.perform(post("/comunas/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void putActualizarComuna_returnsOK() throws Exception {
        Comunas comuna = new Comunas();
        comuna.setNombre("Providencia");
        when(comunasService.actualizarComuna(Mockito.eq(1L), any(String.class))).thenReturn(comuna);
        String json = "{\"nombre\":\"Providencia\"}";
        mockMvc.perform(put("/comunas/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEliminarComuna_returnsNoContent() throws Exception {
        Mockito.doNothing().when(comunasService).eliminarComuna(1L);
        mockMvc.perform(delete("/comunas/eliminar/1"))
                .andExpect(status().isNoContent());
    }
} 