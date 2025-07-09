package com.example.ESTADOS.service;

import com.example.ESTADOS.model.Estados;
import com.example.ESTADOS.repository.EstadosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EstadosService {

    @Autowired
    private EstadosRepository estadosRepository;

    //metodo para registrar un nuevo estado en el sistema
    public Estados crearEstado(Estados estado) {
        if (estado.getNombre() == null || estado.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del estado es requerido");
        }
        
        // metodo para verificar si ya existe un estado activo con el mismo nombre
        if (estadosRepository.existsByNombreAndActivoTrue(estado.getNombre())) {
            throw new RuntimeException("Ya existe un estado activo con el nombre: " + estado.getNombre());
        }
        
        estado.setActivo(true);
        return estadosRepository.save(estado);
    }

    //metodo para listar todos los estados disponibles
    public List<Estados> obtenerTodosLosEstados() {
        return estadosRepository.findByActivoTrue();
    }

    //metodo para buscar un estado específico por su ID
    public Estados obtenerEstadoPorId(Long id) {
        return estadosRepository.findById(id)
                .filter(Estados::getActivo)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));
    }

    //metodo para modificar el nombre de un estado
    public Estados actualizarEstado(Long id, Estados estadoActualizado) {
        Estados estado = estadosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));

        if (!estado.getActivo()) {
            throw new RuntimeException("No se puede actualizar un estado inactivo");
        }

        if (estadoActualizado.getNombre() == null || estadoActualizado.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del estado es requerido");
        }

        // metodo para verificar si el nuevo nombre ya existe en otro estado activo
        if (!estado.getNombre().equalsIgnoreCase(estadoActualizado.getNombre()) && 
            estadosRepository.existsByNombreAndActivoTrue(estadoActualizado.getNombre())) {
            throw new RuntimeException("Ya existe otro estado activo con el nombre: " + estadoActualizado.getNombre());
        }

        estado.setNombre(estadoActualizado.getNombre());
        return estadosRepository.save(estado);
    }

    //metodo para eliminar un estado si no está en uso
    public void eliminarEstado(Long id) {
        Estados estado = estadosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));
        
        // metodo para marcar como inactivo en lugar de eliminar físicamente
        estado.setActivo(false);
        estadosRepository.save(estado);
    }

    //metodo adicional para verificar si un estado existe por ID
    public boolean existeEstado(Long id) {
        return estadosRepository.findById(id)
                .map(Estados::getActivo)
                .orElse(false);
    }

    //metodo adicional para buscar estados por nombre
    public List<Estados> buscarPorNombre(String nombre) {
        return estadosRepository.findByNombreContainingAndActivoTrue(nombre);
    }
}

