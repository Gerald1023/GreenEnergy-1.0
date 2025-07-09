package com.example.SERVICIOS.service;

import com.example.SERVICIOS.model.Servicios;
import com.example.SERVICIOS.repository.ServiciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServiciosService {

    @Autowired
    private ServiciosRepository serviciosRepository;

    //metodo para registrar un nuevo servicio ofrecido por la empresa
    public Servicios crearServicio(Servicios servicio) {
        if (servicio.getNombre() == null || servicio.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre es requerido");
        }
        if (servicio.getDescripcion() == null || servicio.getDescripcion().trim().isEmpty()) {
            throw new RuntimeException("La descripción es requerida");
        }
        if (servicio.getPrecio() == null || servicio.getPrecio() <= 0) {
            throw new RuntimeException("El precio es requerido y debe ser mayor a 0");
        }
        return serviciosRepository.save(servicio);
    }

    //metodo para obtener todos los servicios disponibles
    public List<Servicios> obtenerTodosLosServicios() {
        return serviciosRepository.findAll();
    }

    //metodo para devolver un servicio específico por su ID
    public Servicios obtenerServicioPorId(Long id) {
        return serviciosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
    }

    //metodo para permitir modificar nombre, descripción o precio del servicio
    public Servicios actualizarServicio(Long id, Servicios datosActualizados) {
        Servicios servicio = serviciosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));

        // metodo para actualizar campos si no son null
        if (datosActualizados.getNombre() != null && !datosActualizados.getNombre().trim().isEmpty()) {
            servicio.setNombre(datosActualizados.getNombre());
        }
        if (datosActualizados.getDescripcion() != null && !datosActualizados.getDescripcion().trim().isEmpty()) {
            servicio.setDescripcion(datosActualizados.getDescripcion());
        }
        if (datosActualizados.getPrecio() != null && datosActualizados.getPrecio() > 0) {
            servicio.setPrecio(datosActualizados.getPrecio());
        }

        return serviciosRepository.save(servicio);
    }

    //metodo para eliminar un servicio (si ya no se ofrece)
    public void eliminarServicio(Long id) {
        if (!serviciosRepository.existsById(id)) {
            throw new RuntimeException("Servicio no encontrado con ID: " + id);
        }
        serviciosRepository.deleteById(id);
    }

    //metodo adicional para buscar servicios por nombre
    public List<Servicios> buscarPorNombre(String nombre) {
        return serviciosRepository.findAll().stream()
                .filter(servicio -> servicio.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
    }
}
