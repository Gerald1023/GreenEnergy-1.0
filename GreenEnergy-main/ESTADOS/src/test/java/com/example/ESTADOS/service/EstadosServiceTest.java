package com.example.ESTADOS.service;

import com.example.ESTADOS.model.Estados;
import com.example.ESTADOS.repository.EstadosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EstadosServiceTest {
    @Mock
    private EstadosRepository estadosRepository;
    @InjectMocks
    private EstadosService estadosService;

    @Test
    void crearEstado_returnsSavedEstado() {
        Estados e = new Estados();
        e.setNombre("Activo");
        when(estadosRepository.existsByNombreAndActivoTrue("Activo")).thenReturn(false);
        when(estadosRepository.save(any(Estados.class))).thenReturn(e);
        Estados result = estadosService.crearEstado(e);
        assertThat(result.getNombre()).isEqualTo("Activo");
    }

    @Test
    void obtenerTodosLosEstados_returnsList() {
        Estados e = new Estados();
        when(estadosRepository.findByActivoTrue()).thenReturn(Arrays.asList(e));
        List<Estados> result = estadosService.obtenerTodosLosEstados();
        assertThat(result).hasSize(1).contains(e);
    }

    @Test
    void obtenerEstadoPorId_returnsEstado() {
        Estados e = new Estados();
        e.setActivo(true);
        when(estadosRepository.findById(1L)).thenReturn(Optional.of(e));
        Estados result = estadosService.obtenerEstadoPorId(1L);
        assertThat(result).isEqualTo(e);
    }

    @Test
    void actualizarEstado_returnsUpdatedEstado() {
        Estados existente = new Estados();
        existente.setId(1L);
        existente.setNombre("Antiguo");
        existente.setActivo(true);
        when(estadosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(estadosRepository.existsByNombreAndActivoTrue("Nuevo")).thenReturn(false);
        when(estadosRepository.save(any(Estados.class))).thenReturn(existente);
        Estados actualizado = new Estados();
        actualizado.setNombre("Nuevo");
        Estados result = estadosService.actualizarEstado(1L, actualizado);
        assertThat(result.getNombre()).isEqualTo("Nuevo");
    }

    @Test
    void eliminarEstado_executesWithoutException() {
        Estados e = new Estados();
        e.setActivo(true);
        when(estadosRepository.findById(1L)).thenReturn(Optional.of(e));
        when(estadosRepository.save(any(Estados.class))).thenReturn(e);
        estadosService.eliminarEstado(1L);
        assertThat(e.getActivo()).isFalse();
    }

    @Test
    void existeEstado_returnsTrue() {
        Estados e = new Estados();
        e.setActivo(true);
        when(estadosRepository.findById(1L)).thenReturn(Optional.of(e));
        boolean result = estadosService.existeEstado(1L);
        assertThat(result).isTrue();
    }

    @Test
    void buscarPorNombre_returnsList() {
        Estados e = new Estados();
        when(estadosRepository.findByNombreContainingAndActivoTrue("Activo")).thenReturn(Arrays.asList(e));
        List<Estados> result = estadosService.buscarPorNombre("Activo");
        assertThat(result).hasSize(1).contains(e);
    }
} 