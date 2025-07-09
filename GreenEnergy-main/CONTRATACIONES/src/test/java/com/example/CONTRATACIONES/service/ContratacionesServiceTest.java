package com.example.CONTRATACIONES.service;

import com.example.CONTRATACIONES.model.Contrataciones;
import com.example.CONTRATACIONES.repository.ContratacionesRepository;
import com.example.CONTRATACIONES.WebClient.ProyectosClient;
import com.example.CONTRATACIONES.WebClient.UsuariosClient;
import com.example.CONTRATACIONES.WebClient.ServiciosClient;
import com.example.CONTRATACIONES.WebClient.EstadosClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContratacionesServiceTest {
    @Mock
    private ContratacionesRepository repository;

    @Mock
    private UsuariosClient usuariosClient;

    @Mock
    private ServiciosClient serviciosClient;

    @Mock
    private ProyectosClient proyectosClient;

    @Mock
    private EstadosClient estadosClient;


    @InjectMocks
    private ContratacionesService service;

    @Test
    void obtenerTodasLasContrataciones_returnsListFromRepository() {
        List<Contrataciones> mockList = Arrays.asList(
                new Contrataciones(1L, LocalDate.now(), "desc", 1L, 1L, 1L, 1L)
        );
        when(repository.findAll()).thenReturn(mockList);
        List<Contrataciones> result = service.obtenerTodasLasContrataciones();
        assertThat(result).isEqualTo(mockList);
    }

    @Test
    void crearContratacion_returnsSavedContratacion() {
        Contrataciones nueva = new Contrataciones(null, null, "desc", 1L, 1L, null, null);
        Contrataciones guardada = new Contrataciones(1L, LocalDate.now(), "desc", 1L, 1L, null, 1L);
        when(repository.save(Mockito.any(Contrataciones.class))).thenReturn(guardada);
        when(usuariosClient.existeUsuario(1L)).thenReturn(true);
        when(serviciosClient.existeServicio(1L)).thenReturn(true);
        Contrataciones result = service.crearContratacion(nueva);
        assertThat(result).isEqualTo(guardada);
    }

    @Test
    void obtenerContratacionPorId_returnsContratacion() {
        Contrataciones contratacion = new Contrataciones(2L, LocalDate.now(), "desc", 2L, 1L, null, 1L);
        when(repository.findById(2L)).thenReturn(java.util.Optional.of(contratacion));
        Contrataciones result = service.obtenerContratacionPorId(2L);
        assertThat(result).isEqualTo(contratacion);
    }

    @Test
    void obtenerContratacionesPorUsuario_returnsList() {
        Contrataciones c = new Contrataciones(3L, LocalDate.now(), "desc", 3L, 1L, null, 1L);
        when(repository.findAll()).thenReturn(Arrays.asList(c));
        List<Contrataciones> result = service.obtenerContratacionesPorUsuario(3L);
        assertThat(result).hasSize(1).contains(c);
    }

    @Test
    void obtenerContratacionesPorProyecto_returnsList() {
        Contrataciones c = new Contrataciones(4L, LocalDate.now(), "desc", 1L, 1L, 4L, 1L);
        when(repository.findAll()).thenReturn(Arrays.asList(c));
        List<Contrataciones> result = service.obtenerContratacionesPorProyecto(4L);
        assertThat(result).hasSize(1).contains(c);
    }

    @Test
    void actualizarEstadoContratacion_returnsUpdatedContratacion() {
        Contrataciones c = new Contrataciones(5L, LocalDate.now(), "desc", 1L, 1L, null, 1L);
        when(repository.findById(5L)).thenReturn(java.util.Optional.of(c));
        when(estadosClient.existeEstado(2L)).thenReturn(true);
        when(repository.save(Mockito.any(Contrataciones.class))).thenReturn(c);
        Contrataciones result = service.actualizarEstadoContratacion(5L, 2L);
        assertThat(result.getId_estado()).isEqualTo(2L);
    }

    @Test
    void asignarProyectoAContratacion_returnsUpdatedContratacion() {
        Contrataciones c = new Contrataciones(6L, LocalDate.now(), "desc", 1L, 1L, null, 1L);
        when(repository.findById(6L)).thenReturn(java.util.Optional.of(c));
        when(proyectosClient.existeProyecto(10L)).thenReturn(true);
        when(repository.save(Mockito.any(Contrataciones.class))).thenReturn(c);
        Contrataciones result = service.asignarProyectoAContratacion(6L, 10L);
        assertThat(result.getId_proyecto()).isEqualTo(10L);
        assertThat(result.getId_estado()).isEqualTo(2L);
    }

    @Test
    void eliminarContratacion_executesWithoutException() {
        when(repository.existsById(7L)).thenReturn(true);
        Mockito.doNothing().when(repository).deleteById(7L);
        service.eliminarContratacion(7L);
        Mockito.verify(repository).deleteById(7L);
    }
} 