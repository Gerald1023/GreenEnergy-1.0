package com.example.PROYECTOS.service;

import com.example.PROYECTOS.model.Proyectos;
import com.example.PROYECTOS.repository.ProyectosRepository;
import com.example.PROYECTOS.WebClient.ContratacionesClient;
import com.example.PROYECTOS.WebClient.EstadosClient;
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
public class ProyectosServiceTest {
    @Mock
    private ProyectosRepository proyectosRepository;
    @Mock
    private ContratacionesClient contratacionesClient;
    @Mock
    private EstadosClient estadosClient;
    @InjectMocks
    private ProyectosService proyectosService;

    @Test
    void crearProyecto_returnsSavedProyecto() {
        Proyectos p = new Proyectos();
        p.setNombre("Nuevo Proyecto");
        p.setFecha_inicio(LocalDate.now());
        p.setFecha_fin(LocalDate.now().plusDays(1));
        p.setId_contratacion(1L);
        p.setId_estado(1L);
        when(contratacionesClient.existeContratacion(1L)).thenReturn(true);
        when(estadosClient.existeEstado(1L)).thenReturn(true);
        when(proyectosRepository.save(any(Proyectos.class))).thenReturn(p);
        Proyectos result = proyectosService.crearProyecto(p);
        assertThat(result.getNombre()).isEqualTo("Nuevo Proyecto");
    }

    @Test
    void obtenerTodosLosProyectos_returnsList() {
        Proyectos p = new Proyectos();
        when(proyectosRepository.findAll()).thenReturn(Arrays.asList(p));
        List<Proyectos> result = proyectosService.obtenerTodosLosProyectos();
        assertThat(result).hasSize(1).contains(p);
    }

    @Test
    void obtenerProyectoPorId_returnsProyecto() {
        Proyectos p = new Proyectos();
        when(proyectosRepository.findById(1L)).thenReturn(Optional.of(p));
        Proyectos result = proyectosService.obtenerProyectoPorId(1L);
        assertThat(result).isEqualTo(p);
    }

    @Test
    void actualizarProyecto_returnsUpdatedProyecto() {
        Proyectos existente = new Proyectos();
        existente.setId_proyecto(1L);
        existente.setNombre("Antiguo");
        existente.setFecha_inicio(LocalDate.now());
        existente.setFecha_fin(LocalDate.now().plusDays(1));
        when(proyectosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(proyectosRepository.save(any(Proyectos.class))).thenReturn(existente);
        Proyectos actualizado = new Proyectos();
        actualizado.setNombre("Nuevo");
        actualizado.setFecha_inicio(LocalDate.now());
        actualizado.setFecha_fin(LocalDate.now().plusDays(1));
        Proyectos result = proyectosService.actualizarProyecto(1L, actualizado);
        assertThat(result.getNombre()).isEqualTo("Nuevo");
    }

    @Test
    void actualizarEstadoProyecto_returnsUpdatedProyecto() {
        Proyectos existente = new Proyectos();
        existente.setId_proyecto(1L);
        existente.setId_estado(1L);
        when(proyectosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(estadosClient.existeEstado(2L)).thenReturn(true);
        when(proyectosRepository.save(any(Proyectos.class))).thenReturn(existente);
        Proyectos result = proyectosService.actualizarEstadoProyecto(1L, 2L);
        assertThat(result.getId_estado()).isEqualTo(2L);
    }

    @Test
    void eliminarProyecto_executesWithoutException() {
        when(proyectosRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(proyectosRepository).deleteById(1L);
        proyectosService.eliminarProyecto(1L);
        Mockito.verify(proyectosRepository).deleteById(1L);
    }
} 