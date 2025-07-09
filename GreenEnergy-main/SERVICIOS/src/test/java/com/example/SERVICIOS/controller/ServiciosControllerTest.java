package com.example.SERVICIOS.controller;

import com.example.SERVICIOS.model.Servicios;
import com.example.SERVICIOS.service.ServiciosService;
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

@WebMvcTest(ServiciosController.class)
public class ServiciosControllerTest {
    @MockBean
    private ServiciosService serviciosService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTodosLosServicios_returnsOKAndJson() throws Exception {
        List<Servicios> lista = Arrays.asList(new Servicios());
        when(serviciosService.obtenerTodosLosServicios()).thenReturn(lista);
        mockMvc.perform(get("/servicios").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getServicioPorId_returnsOKAndJson() throws Exception {
        Servicios servicio = new Servicios();
        servicio.setId_servicio(1L);
        when(serviciosService.obtenerServicioPorId(1L)).thenReturn(servicio);
        mockMvc.perform(get("/servicios/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBuscarPorNombre_returnsOKAndJson() throws Exception {
        List<Servicios> lista = Arrays.asList(new Servicios());
        when(serviciosService.buscarPorNombre("Luz")).thenReturn(lista);
        mockMvc.perform(get("/servicios/buscar").param("nombre", "Luz").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void postCrearServicio_returnsCreated() throws Exception {
        Servicios servicio = new Servicios();
        servicio.setNombre("Luz");
        servicio.setDescripcion("Servicio de luz");
        servicio.setPrecio(100.0);
        when(serviciosService.crearServicio(any(Servicios.class))).thenReturn(servicio);
        String json = "{\"nombre\":\"Luz\",\"descripcion\":\"Servicio de luz\",\"precio\":100.0}";
        mockMvc.perform(post("/servicios/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void putActualizarServicio_returnsOK() throws Exception {
        Servicios servicio = new Servicios();
        servicio.setNombre("Agua");
        when(serviciosService.actualizarServicio(Mockito.eq(1L), any(Servicios.class))).thenReturn(servicio);
        String json = "{\"nombre\":\"Agua\"}";
        mockMvc.perform(put("/servicios/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEliminarServicio_returnsNoContent() throws Exception {
        Mockito.doNothing().when(serviciosService).eliminarServicio(1L);
        mockMvc.perform(delete("/servicios/eliminar/1"))
                .andExpect(status().isNoContent());
    }
} 