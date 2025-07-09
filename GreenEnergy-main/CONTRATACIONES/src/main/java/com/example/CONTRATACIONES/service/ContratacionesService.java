package com.example.CONTRATACIONES.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CONTRATACIONES.model.Contrataciones;
import com.example.CONTRATACIONES.repository.ContratacionesRepository;
import com.example.CONTRATACIONES.WebClient.UsuariosClient;
import com.example.CONTRATACIONES.WebClient.ServiciosClient;
import com.example.CONTRATACIONES.WebClient.ProyectosClient;
import com.example.CONTRATACIONES.WebClient.EstadosClient;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ContratacionesService {
    @Autowired
    private ContratacionesRepository contratacionesRepository;
    
    @Autowired
    private UsuariosClient usuariosClient;
    
    @Autowired
    private ServiciosClient serviciosClient;
    
    @Autowired
    private ProyectosClient proyectosClient;
    
    @Autowired
    private EstadosClient estadosClient;

    //metodo para  crear una nueva contratación a partir de una solicitud del cliente
    public Contrataciones crearContratacion(Contrataciones contratacion) {
        if (contratacion.getDescripcion() == null || contratacion.getDescripcion().trim().isEmpty()) {
            throw new RuntimeException("La descripción es requerida");
        }
        if (contratacion.getId_usuario() == null) {
            throw new RuntimeException("El ID del usuario es requerido");
        }
        if (contratacion.getId_servicio() == null) {
            throw new RuntimeException("El ID del servicio es requerido");
        }
        
        // metodo para validar que el usuario existe
        if (!usuariosClient.existeUsuario(contratacion.getId_usuario())) {
            throw new RuntimeException("El usuario con ID " + contratacion.getId_usuario() + " no existe");
        }
        
        // metodo para validar que el servicio existe
        if (!serviciosClient.existeServicio(contratacion.getId_servicio())) {
            throw new RuntimeException("El servicio con ID " + contratacion.getId_servicio() + " no existe");
        }
        
        // metodo para establecer fecha de solicitud automáticamente
        contratacion.setFecha_Solicitud(LocalDate.now());
        
        // metodo para establecer estado inicial como pendiente (asumiendo que 1 = pendiente)
        contratacion.setId_estado(1L);
        
        // metodo para establecer que inicialmente el proyecto es null hasta que se asigne
        contratacion.setId_proyecto(null);
        
        return contratacionesRepository.save(contratacion);
    }

    //metodo para obtener todas las contrataciones por administrador o coordinador de proyectos
    public List<Contrataciones> obtenerTodasLasContrataciones() {
        return contratacionesRepository.findAll();
    }

    //metodos para buscar una contratación específica
    public Contrataciones obtenerContratacionPorId(Long id) {
        return contratacionesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contratación no encontrada con ID: " + id));
    }

    //metodo para las contrataciones hechas por un cliente específico
    public List<Contrataciones> obtenerContratacionesPorUsuario(Long idUsuario) {
        return contratacionesRepository.findAll().stream()
                .filter(contratacion -> contratacion.getId_usuario().equals(idUsuario))
                .toList();
    }

    //metodo para obtener las contrataciones por proyecto específico
    public List<Contrataciones> obtenerContratacionesPorProyecto(Long idProyecto) {
        return contratacionesRepository.findAll().stream()
                .filter(contratacion -> contratacion.getId_proyecto() != null && 
                                       contratacion.getId_proyecto().equals(idProyecto))
                .toList();
    }

    //metodo para permitir al coordinador cambiar el estado de la contratación
    public Contrataciones actualizarEstadoContratacion(Long id, Long nuevoEstadoId) {
        Contrataciones contratacion = contratacionesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contratación no encontrada con ID: " + id));
        
        // metodo para validar que el estado existe
        if (!estadosClient.existeEstado(nuevoEstadoId)) {
            throw new RuntimeException("El estado con ID " + nuevoEstadoId + " no existe");
        }
        
        contratacion.setId_estado(nuevoEstadoId);
        return contratacionesRepository.save(contratacion);
    }

    //metodo para  cuando el coordinador crea un proyecto 
    public Contrataciones asignarProyectoAContratacion(Long idContratacion, Long idProyecto) {
        Contrataciones contratacion = contratacionesRepository.findById(idContratacion)
                .orElseThrow(() -> new RuntimeException("Contratación no encontrada con ID: " + idContratacion));
        
        if (idProyecto == null) {
            throw new RuntimeException("El ID del proyecto es requerido");
        }
        
        // metodo para validar que el proyecto existe
        if (!proyectosClient.existeProyecto(idProyecto)) {
            throw new RuntimeException("El proyecto con ID " + idProyecto + " no existe");
        }
        
        // metodo para asignar el proyecto y cambiar el estado a "en curso" (asumiendo que 2 = en curso)
        contratacion.setId_proyecto(idProyecto);
        contratacion.setId_estado(2L);
        
        return contratacionesRepository.save(contratacion);
    }

    //metodo para eliminar una contratación
    public void eliminarContratacion(Long id) {
        if (!contratacionesRepository.existsById(id)) {
            throw new RuntimeException("Contratación no encontrada con ID: " + id);
        }
        contratacionesRepository.deleteById(id);
    }
}
