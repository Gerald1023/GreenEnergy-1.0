package com.example.RESENA.controller;

import com.example.RESENA.model.Resena;
import com.example.RESENA.service.ResenaService;
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

@WebMvcTest(ResenaController.class)
public class ResenaControllerTest {
    @MockBean
    private ResenaService resenaService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTodasLasResenas_returnsOKAndJson() throws Exception {
        List<Resena> lista = Arrays.asList(new Resena());
        when(resenaService.obtenerTodasLasResenas()).thenReturn(lista);
        mockMvc.perform(get("/resenas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getResenaPorId_returnsOKAndJson() throws Exception {
        Resena resena = new Resena();
        resena.setId_resena(1L);
        when(resenaService.obtenerResenaPorId(1L)).thenReturn(resena);
        mockMvc.perform(get("/resenas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getResenasPorUsuario_returnsOKAndJson() throws Exception {
        List<Resena> lista = Arrays.asList(new Resena());
        when(resenaService.obtenerResenasPorUsuario(1L)).thenReturn(lista);
        mockMvc.perform(get("/resenas/usuario/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getResenasPorServicio_returnsOKAndJson() throws Exception {
        List<Resena> lista = Arrays.asList(new Resena());
        when(resenaService.obtenerResenasPorServicio(1L)).thenReturn(lista);
        mockMvc.perform(get("/resenas/servicio/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void postCrearResena_returnsCreated() throws Exception {
        Resena resena = new Resena();
        resena.setComentario("Muy bueno");
        resena.setId_usuario(1L);
        resena.setId_servicio(1L);
        resena.setCalificacion(5);
        when(resenaService.crearResena(any(Resena.class))).thenReturn(resena);
        String json = "{\"comentario\":\"Muy bueno\",\"id_usuario\":1,\"id_servicio\":1,\"calificacion\":5}";
        mockMvc.perform(post("/resenas/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteEliminarResena_returnsNoContent() throws Exception {
        Mockito.doNothing().when(resenaService).eliminarResena(1L);
        mockMvc.perform(delete("/resenas/eliminar/1"))
                .andExpect(status().isNoContent());
    }
} 