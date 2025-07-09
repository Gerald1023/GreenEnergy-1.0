package com.example.RESENA.service;

import com.example.RESENA.model.Resena;
import com.example.RESENA.repository.ResenaRepository;
import com.example.RESENA.WebClient.UsuariosClient;
import com.example.RESENA.WebClient.ServiciosClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResenaServiceTest {
    @Mock
    private ResenaRepository resenaRepository;
    @Mock
    private UsuariosClient usuariosClient;
    @Mock
    private ServiciosClient serviciosClient;
    @InjectMocks
    private ResenaService resenaService;

    @Test
    void crearResena_returnsSavedResena() {
        Resena r = new Resena();
        r.setComentario("Muy bueno");
        r.setId_usuario(1L);
        r.setId_servicio(1L);
        r.setCalificacion(5);
        when(usuariosClient.existeUsuario(1L)).thenReturn(true);
        when(serviciosClient.existeServicio(1L)).thenReturn(true);
        when(resenaRepository.save(any(Resena.class))).thenReturn(r);
        Resena result = resenaService.crearResena(r);
        assertThat(result.getComentario()).isEqualTo("Muy bueno");
    }

    @Test
    void obtenerTodasLasResenas_returnsList() {
        Resena r = new Resena();
        when(resenaRepository.findAll()).thenReturn(Arrays.asList(r));
        List<Resena> result = resenaService.obtenerTodasLasResenas();
        assertThat(result).hasSize(1).contains(r);
    }

    @Test
    void obtenerResenaPorId_returnsResena() {
        Resena r = new Resena();
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(r));
        Resena result = resenaService.obtenerResenaPorId(1L);
        assertThat(result).isEqualTo(r);
    }

    @Test
    void obtenerResenasPorUsuario_returnsList() {
        Resena r = new Resena();
        r.setId_usuario(1L);
        when(resenaRepository.findAll()).thenReturn(Arrays.asList(r));
        List<Resena> result = resenaService.obtenerResenasPorUsuario(1L);
        assertThat(result).hasSize(1).contains(r);
    }

    @Test
    void obtenerResenasPorServicio_returnsList() {
        Resena r = new Resena();
        r.setId_servicio(1L);
        when(resenaRepository.findAll()).thenReturn(Arrays.asList(r));
        List<Resena> result = resenaService.obtenerResenasPorServicio(1L);
        assertThat(result).hasSize(1).contains(r);
    }

    @Test
    void eliminarResena_executesWithoutException() {
        when(resenaRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(resenaRepository).deleteById(1L);
        resenaService.eliminarResena(1L);
        Mockito.verify(resenaRepository).deleteById(1L);
    }
} 