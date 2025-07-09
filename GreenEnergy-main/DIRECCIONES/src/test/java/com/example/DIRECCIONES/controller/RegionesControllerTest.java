package com.example.DIRECCIONES.controller;

import com.example.DIRECCIONES.model.Regiones;
import com.example.DIRECCIONES.service.RegionesService;
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

@WebMvcTest(RegionesController.class)
public class RegionesControllerTest {
    @MockBean
    private RegionesService regionesService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getverTodasLasRegiones_returnsOKAndJson() throws Exception {
        List<Regiones> lista = Arrays.asList(new Regiones());
        when(regionesService.verTodasLasRegiones()).thenReturn(lista);
        mockMvc.perform(get("/regiones").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBuscarRegionPorId_returnsOKAndJson() throws Exception {
        Regiones region = new Regiones();
        region.setId_region(1L);
        when(regionesService.buscarRegionPorId(1L)).thenReturn(region);
        mockMvc.perform(get("/regiones/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void postCrearRegion_returnsCreated() throws Exception {
        Regiones region = new Regiones();
        region.setNombre("Metropolitana");
        when(regionesService.crearRegion(any(String.class))).thenReturn(region);
        String json = "{\"nombre\":\"Metropolitana\"}";
        mockMvc.perform(post("/regiones/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void putActualizarRegion_returnsOK() throws Exception {
        Regiones region = new Regiones();
        region.setNombre("Valparaiso");
        when(regionesService.actualizarRegion(Mockito.eq(1L), any(String.class))).thenReturn(region);
        String json = "{\"nombre\":\"Valparaiso\"}";
        mockMvc.perform(put("/regiones/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEliminarRegion_returnsNoContent() throws Exception {
        Mockito.doNothing().when(regionesService).eliminarRegion(1L);
        mockMvc.perform(delete("/regiones/eliminar/1"))
                .andExpect(status().isNoContent());
    }
} 