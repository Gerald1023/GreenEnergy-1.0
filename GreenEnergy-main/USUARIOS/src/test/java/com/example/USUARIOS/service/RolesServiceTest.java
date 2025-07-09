package com.example.USUARIOS.service;

import com.example.USUARIOS.model.Roles;
import com.example.USUARIOS.repository.RolesRepository;
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
public class RolesServiceTest {
    @Mock
    private RolesRepository rolesRepository;
    @InjectMocks
    private RolesService rolesService;

    @Test
    void TodosLosRoles_returnsList() {
        Roles r = new Roles();
        when(rolesRepository.findAll()).thenReturn(Arrays.asList(r));
        List<Roles> result = rolesService.TodosLosRoles();
        assertThat(result).hasSize(1).contains(r);
    }

    @Test
    void BuscarRolPorId_returnsRol() {
        Roles r = new Roles();
        when(rolesRepository.findById(1L)).thenReturn(Optional.of(r));
        Roles result = rolesService.BuscarRolPorId(1L);
        assertThat(result).isEqualTo(r);
    }

    @Test
    void CrearRol_returnsSavedRol() {
        Roles r = new Roles();
        r.setNombre("ADMIN");
        when(rolesRepository.save(any(Roles.class))).thenReturn(r);
        Roles result = rolesService.CrearRol("ADMIN");
        assertThat(result.getNombre()).isEqualTo("ADMIN");
    }

    @Test
    void ActualizarRol_returnsUpdatedRol() {
        Roles existente = new Roles();
        existente.setId_rol(1L);
        existente.setNombre("ADMIN");
        when(rolesRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(rolesRepository.save(any(Roles.class))).thenReturn(existente);
        Roles result = rolesService.ActualizarRol(1L, "USER");
        assertThat(result.getNombre()).isEqualTo("USER");
    }

    @Test
    void EliminarRol_executesWithoutException() {
        Roles r = new Roles();
        when(rolesRepository.findById(1L)).thenReturn(Optional.of(r));
        Mockito.doNothing().when(rolesRepository).delete(r);
        rolesService.EliminarRol(1L);
        Mockito.verify(rolesRepository).delete(r);
    }
} 