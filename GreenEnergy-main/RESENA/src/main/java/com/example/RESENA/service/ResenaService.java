package com.example.RESENA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RESENA.model.Resena;
import com.example.RESENA.repository.ResenaRepository;
import com.example.RESENA.WebClient.UsuariosClient;
import com.example.RESENA.WebClient.ServiciosClient;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;
    
    @Autowired
    private UsuariosClient usuariosClient;
    
    @Autowired
    private ServiciosClient serviciosClient;

    //metodo para permitir a un usuario dejar una reseña después de usar un servicio
    public Resena crearResena(Resena resena) {
        if (resena.getComentario() == null || resena.getComentario().trim().isEmpty()) {
            throw new RuntimeException("El comentario es requerido");
        }
        if (resena.getId_usuario() == null) {
            throw new RuntimeException("El ID del usuario es requerido");
        }
        if (resena.getId_servicio() == null) {
            throw new RuntimeException("El ID del servicio es requerido");
        }
        if (resena.getCalificacion() < 1 || resena.getCalificacion() > 5) {
            throw new RuntimeException("La calificación debe estar entre 1 y 5");
        }
        
        // metodo para validar que el usuario existe
        if (!usuariosClient.existeUsuario(resena.getId_usuario())) {
            throw new RuntimeException("El usuario con ID " + resena.getId_usuario() + " no existe");
        }
        
        // metodo para validar que el servicio existe
        if (!serviciosClient.existeServicio(resena.getId_servicio())) {
            throw new RuntimeException("El servicio con ID " + resena.getId_servicio() + " no existe");
        }
        
        // metodo para establecer fecha automáticamente
        resena.setFecha(LocalDate.now());
        
        return resenaRepository.save(resena);
    }

    //metodo para listar todas las reseñas del sistema
    public List<Resena> obtenerTodasLasResenas() {
        return resenaRepository.findAll();
    }

    //metodo para buscar una reseña específica por su ID
    public Resena obtenerResenaPorId(Long id) {
        return resenaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con ID: " + id));
    }

    //metodo para listar todas las reseñas que ha hecho un usuario específico
    public List<Resena> obtenerResenasPorUsuario(Long idUsuario) {
        return resenaRepository.findAll().stream()
                .filter(resena -> resena.getId_usuario().equals(idUsuario))
                .toList();
    }

    //metodo para listar todas las reseñas hechas a un servicio específico
    public List<Resena> obtenerResenasPorServicio(Long idServicio) {
        return resenaRepository.findAll().stream()
                .filter(resena -> resena.getId_servicio().equals(idServicio))
                .toList();
    }

    //metodo para eliminar una reseña por ID
    public void eliminarResena(Long id) {
        if (!resenaRepository.existsById(id)) {
            throw new RuntimeException("Reseña no encontrada con ID: " + id);
        }
        resenaRepository.deleteById(id);
    }
} 