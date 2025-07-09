package com.example.SERVICIOS.service;

import com.example.SERVICIOS.model.Servicios;
import com.example.SERVICIOS.repository.ServiciosRepository;
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
public class ServiciosServiceTest {
    @Mock
    private ServiciosRepository serviciosRepository;
    @InjectMocks
    private ServiciosService serviciosService;

    @Test
    void crearServicio_returnsSavedServicio() {
        Servicios s = new Servicios();
        s.setNombre("Luz");
        s.setDescripcion("Servicio de luz");
        s.setPrecio(100.0);
        when(serviciosRepository.save(any(Servicios.class))).thenReturn(s);
        Servicios result = serviciosService.crearServicio(s);
        assertThat(result.getNombre()).isEqualTo("Luz");
    }

    @Test
    void obtenerTodosLosServicios_returnsList() {
        Servicios s = new Servicios();
        when(serviciosRepository.findAll()).thenReturn(Arrays.asList(s));
        List<Servicios> result = serviciosService.obtenerTodosLosServicios();
        assertThat(result).hasSize(1).contains(s);
    }

    @Test
    void obtenerServicioPorId_returnsServicio() {
        Servicios s = new Servicios();
        when(serviciosRepository.findById(1L)).thenReturn(Optional.of(s));
        Servicios result = serviciosService.obtenerServicioPorId(1L);
        assertThat(result).isEqualTo(s);
    }

    @Test
    void actualizarServicio_returnsUpdatedServicio() {
        Servicios existente = new Servicios();
        existente.setId_servicio(1L);
        existente.setNombre("Luz");
        when(serviciosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(serviciosRepository.save(any(Servicios.class))).thenReturn(existente);
        Servicios actualizado = new Servicios();
        actualizado.setNombre("Agua");
        Servicios result = serviciosService.actualizarServicio(1L, actualizado);
        assertThat(result.getNombre()).isEqualTo("Agua");
    }

    @Test
    void eliminarServicio_executesWithoutException() {
        when(serviciosRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(serviciosRepository).deleteById(1L);
        serviciosService.eliminarServicio(1L);
        Mockito.verify(serviciosRepository).deleteById(1L);
    }

    @Test
    void buscarPorNombre_returnsList() {
        Servicios s = new Servicios();
        s.setNombre("Luz");
        when(serviciosRepository.findAll()).thenReturn(Arrays.asList(s));
        List<Servicios> result = serviciosService.buscarPorNombre("Luz");
        assertThat(result).hasSize(1).contains(s);
    }
} 