package com.example.DIRECCIONES.controller;

import com.example.DIRECCIONES.model.Direcciones;
import com.example.DIRECCIONES.model.Comunas;
import com.example.DIRECCIONES.model.Regiones;
import com.example.DIRECCIONES.service.DireccionesService;
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

@WebMvcTest(DireccionesController.class)
public class DireccionesControllerTest {
    @MockBean
    private DireccionesService direccionesService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getverTodasLasDirecciones_returnsOKAndJson() throws Exception {
        List<Direcciones> lista = Arrays.asList(new Direcciones());
        when(direccionesService.verTodasLasDirecciones()).thenReturn(lista);
        mockMvc.perform(get("/direcciones").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBuscarDireccionPorId_returnsOKAndJson() throws Exception {
        Direcciones direccion = new Direcciones();
        direccion.setId_direccion(1L);
        when(direccionesService.buscarDireccionPorId(1L)).thenReturn(direccion);
        mockMvc.perform(get("/direcciones/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void postCrearDireccion_returnsCreated() throws Exception {
        Direcciones direccion = new Direcciones();
        direccion.setCalle("Calle Nueva");
        direccion.setNumero("123");
        direccion.setDetalle("Depto 1");
        Comunas comuna = new Comunas();
        comuna.setId_comuna(1L);
        direccion.setComuna(comuna);
        Regiones region = new Regiones();
        region.setId_region(1L);
        direccion.setRegion(region);
        when(direccionesService.crearDireccion(any(Direcciones.class))).thenReturn(direccion);
        String json = "{\"calle\":\"Calle Nueva\",\"numero\":\"123\",\"detalle\":\"Depto 1\",\"comuna\":{\"id_comuna\":1},\"region\":{\"id_region\":1}}";
        mockMvc.perform(post("/direcciones/crear")
                .contentType(MediaType.APPLICATION_JSON) 
                .accept(MediaType.APPLICATION_JSON)     
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void putActualizarDireccion_returnsOK() throws Exception {
        Direcciones direccion = new Direcciones();
        direccion.setCalle("Calle Actualizada");
        direccion.setNumero("123");
        direccion.setDetalle("Depto 1");
        Comunas comuna = new Comunas();
        comuna.setId_comuna(1L);
        direccion.setComuna(comuna);
        Regiones region = new Regiones();
        region.setId_region(1L);
        direccion.setRegion(region);
        when(direccionesService.actualizarDireccion(Mockito.eq(1L), any(Direcciones.class))).thenReturn(direccion);
        String json = "{\"calle\":\"Calle Actualizada\",\"numero\":\"123\",\"detalle\":\"Depto 1\",\"comuna\":{\"id_comuna\":1},\"region\":{\"id_region\":1}}";
        mockMvc.perform(put("/direcciones/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEliminarDireccion_returnsNoContent() throws Exception {
        Mockito.doNothing().when(direccionesService).eliminarDireccion(1L);
        mockMvc.perform(delete("/direcciones/eliminar/1"))
                .andExpect(status().isNoContent());
    }
} 