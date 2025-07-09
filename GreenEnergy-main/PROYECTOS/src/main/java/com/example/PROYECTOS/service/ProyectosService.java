package com.example.PROYECTOS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.PROYECTOS.model.Proyectos;
import com.example.PROYECTOS.repository.ProyectosRepository;
import com.example.PROYECTOS.WebClient.ContratacionesClient;
import com.example.PROYECTOS.WebClient.EstadosClient;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProyectosService {
    @Autowired
    private ProyectosRepository proyectosRepository;
    
    @Autowired
    private ContratacionesClient contratacionesClient;
    
    @Autowired
    private EstadosClient estadosClient;

    //metodo para crear un nuevo proyecto de instalación asociado a una contratación
    public Proyectos crearProyecto(Proyectos proyecto) {
        if (proyecto.getNombre() == null || proyecto.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del proyecto es requerido");
        }
        if (proyecto.getFecha_inicio() == null) {
            throw new RuntimeException("La fecha de inicio es requerida");
        }
        if (proyecto.getFecha_fin() == null) {
            throw new RuntimeException("La fecha de fin es requerida");
        }
        if (proyecto.getId_contratacion() == null) {
            throw new RuntimeException("El ID de la contratación es requerido");
        }
        if (proyecto.getId_estado() == null) {
            throw new RuntimeException("El ID del estado es requerido");
        }
        
        // metodo para validar que la fecha de fin sea posterior a la fecha de inicio
        if (proyecto.getFecha_fin().isBefore(proyecto.getFecha_inicio())) {
            throw new RuntimeException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        
        // metodo para validar que la contratación existe
        if (!contratacionesClient.existeContratacion(proyecto.getId_contratacion())) {
            throw new RuntimeException("La contratación con ID " + proyecto.getId_contratacion() + " no existe");
        }
        
        // metodo para validar que el estado existe
        if (!estadosClient.existeEstado(proyecto.getId_estado())) {
            throw new RuntimeException("El estado con ID " + proyecto.getId_estado() + " no existe");
        }
        
        // metodo para establecer avance inicial si no se proporciona
        if (proyecto.getAvance() == null || proyecto.getAvance().trim().isEmpty()) {
            proyecto.setAvance("0%");
        }
        
        return proyectosRepository.save(proyecto);
    }

    //metodo para obtener todos los proyectos para el administrador o coordinador
    public List<Proyectos> obtenerTodosLosProyectos() {
        return proyectosRepository.findAll();
    }

    //metodo para consultar un proyecto específico por ID
    public Proyectos obtenerProyectoPorId(Long id) {
        return proyectosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + id));
    }

    //metodo para actualizar información como nombre, fechas o avance
    public Proyectos actualizarProyecto(Long id, Proyectos actualizado) {
        Proyectos proyecto = proyectosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + id));
        
        // metodo para actualizar campos si no son null
        if (actualizado.getNombre() != null && !actualizado.getNombre().trim().isEmpty()) {
            proyecto.setNombre(actualizado.getNombre());
        }
        if (actualizado.getFecha_inicio() != null) {
            proyecto.setFecha_inicio(actualizado.getFecha_inicio());
        }
        if (actualizado.getFecha_fin() != null) {
            proyecto.setFecha_fin(actualizado.getFecha_fin());
        }
        if (actualizado.getAvance() != null && !actualizado.getAvance().trim().isEmpty()) {
            proyecto.setAvance(actualizado.getAvance());
        }
        
        // metodo para validar que la fecha de fin sea posterior a la fecha de inicio
        if (proyecto.getFecha_fin().isBefore(proyecto.getFecha_inicio())) {
            throw new RuntimeException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        
        return proyectosRepository.save(proyecto);
    }

    //metodo para cambiar el estado del proyecto
    public Proyectos actualizarEstadoProyecto(Long idProyecto, Long idEstado) {
        Proyectos proyecto = proyectosRepository.findById(idProyecto)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + idProyecto));
        
        if (idEstado == null) {
            throw new RuntimeException("El ID del estado es requerido");
        }
        
        // metodo para validar que el estado existe
        if (!estadosClient.existeEstado(idEstado)) {
            throw new RuntimeException("El estado con ID " + idEstado + " no existe");
        }
        
        proyecto.setId_estado(idEstado);
        return proyectosRepository.save(proyecto);
    }

    //metodo para eliminar un proyecto por ID (opcional)
    public void eliminarProyecto(Long id) {
        if (!proyectosRepository.existsById(id)) {
            throw new RuntimeException("Proyecto no encontrado con ID: " + id);
        }
        proyectosRepository.deleteById(id);
    }
} 