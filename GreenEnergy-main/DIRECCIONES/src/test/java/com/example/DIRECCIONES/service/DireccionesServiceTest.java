package com.example.DIRECCIONES.service;

import com.example.DIRECCIONES.model.Comunas;
import com.example.DIRECCIONES.model.Direcciones;
import com.example.DIRECCIONES.model.Regiones;
import com.example.DIRECCIONES.repository.ComunasRepository;
import com.example.DIRECCIONES.repository.DireccionesRepository;
import com.example.DIRECCIONES.repository.RegionesRepository;
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
public class DireccionesServiceTest {
    @Mock
    private DireccionesRepository direccionesRepository;
    @Mock
    private RegionesRepository regionesRepository;
    @Mock
    private ComunasRepository comunasRepository;
    @InjectMocks
    private DireccionesService direccionesService;

    @Test
    void verTodasLasDirecciones_returnsList() {
        Direcciones d = new Direcciones();
        when(direccionesRepository.findAll()).thenReturn(Arrays.asList(d));
        List<Direcciones> result = direccionesService.verTodasLasDirecciones();
        assertThat(result).hasSize(1).contains(d);
    }

    @Test
    void buscarDireccionPorId_returnsDireccion() {
        Direcciones d = new Direcciones();
        when(direccionesRepository.findById(1L)).thenReturn(Optional.of(d));
        Direcciones result = direccionesService.buscarDireccionPorId(1L);
        assertThat(result).isEqualTo(d);
    }

    @Test
    void crearDireccion_returnsSavedDireccion() {
        Direcciones d = new Direcciones();
        Comunas comuna = new Comunas();
        comuna.setId_comuna(1L);
        Regiones region = new Regiones();
        region.setId_region(1L);
        d.setComuna(comuna);
        d.setRegion(region);
        when(comunasRepository.findById(1L)).thenReturn(Optional.of(comuna));
        when(regionesRepository.findById(1L)).thenReturn(Optional.of(region));
        when(direccionesRepository.save(any(Direcciones.class))).thenReturn(d);
        Direcciones result = direccionesService.crearDireccion(d);
        assertThat(result).isEqualTo(d);
    }

    @Test
    void actualizarDireccion_returnsUpdatedDireccion() {
        Direcciones existente = new Direcciones();
        existente.setId_direccion(1L);
        Comunas comuna = new Comunas();
        comuna.setId_comuna(1L);
        Regiones region = new Regiones();
        region.setId_region(1L);
        when(direccionesRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(comunasRepository.findById(1L)).thenReturn(Optional.of(comuna));
        when(regionesRepository.findById(1L)).thenReturn(Optional.of(region));
        when(direccionesRepository.save(any(Direcciones.class))).thenReturn(existente);
        Direcciones actualizado = new Direcciones();
        actualizado.setComuna(comuna);
        actualizado.setRegion(region);
        Direcciones result = direccionesService.actualizarDireccion(1L, actualizado);
        assertThat(result).isEqualTo(existente);
    }

    @Test
    void eliminarDireccion_executesWithoutException() {
        Direcciones d = new Direcciones();
        when(direccionesRepository.findById(1L)).thenReturn(Optional.of(d));
        Mockito.doNothing().when(direccionesRepository).delete(d);
        direccionesService.eliminarDireccion(1L);
        Mockito.verify(direccionesRepository).delete(d);
    }
} 