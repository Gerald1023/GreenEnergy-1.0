package com.example.DIRECCIONES.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DIRECCIONES.model.Comunas;
import com.example.DIRECCIONES.model.Direcciones;
import com.example.DIRECCIONES.model.Regiones;
import com.example.DIRECCIONES.repository.ComunasRepository;
import com.example.DIRECCIONES.repository.DireccionesRepository;
import com.example.DIRECCIONES.repository.RegionesRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class DireccionesService {
    @Autowired
    private DireccionesRepository direccionesRepository;

    @Autowired
    private RegionesRepository regionesRepository;

    @Autowired
    private ComunasRepository comunasRepository; 
    //get
    //metodo para ver todas las direcciones
    public List<Direcciones> verTodasLasDirecciones() { 
        return direccionesRepository.findAll();
    }

    //metodo para buscar una direccion por id
    public Direcciones buscarDireccionPorId(Long id) {
        return direccionesRepository.findById(id).orElse(null);
    }

    
    //post
    //metodo para crear una direccion nueva
    public Direcciones crearDireccion(Direcciones direccion) {
        if (direccion.getComuna() == null || direccion.getComuna().getId_comuna() == null) {
            throw new RuntimeException("La comuna es obligatoria y debe tener un id_comuna.");
        }
        if (direccion.getRegion() == null || direccion.getRegion().getId_region() == null) {
            throw new RuntimeException("La región es obligatoria y debe tener un id_region.");
        }
        Comunas comuna = comunasRepository.findById(direccion.getComuna().getId_comuna())
            .orElseThrow(() -> new RuntimeException("Comuna no encontrada"));
        Regiones region = regionesRepository.findById(direccion.getRegion().getId_region())
            .orElseThrow(() -> new RuntimeException("Región no encontrada"));
        direccion.setComuna(comuna);
        direccion.setRegion(region);
        return direccionesRepository.save(direccion);
    }
    //put
    //metodo para actualizar una direccion
    public Direcciones actualizarDireccion(Long id, Direcciones direccion) {
        Direcciones direccionExistente = direccionesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Dirección no encontrada con ID: " + id));
        direccionExistente.setCalle(direccion.getCalle());
        direccionExistente.setNumero(direccion.getNumero());
        direccionExistente.setDetalle(direccion.getDetalle());
        if (direccion.getComuna() == null || direccion.getComuna().getId_comuna() == null) {
            throw new RuntimeException("La comuna es obligatoria y debe tener un id_comuna.");
        }
        if (direccion.getRegion() == null || direccion.getRegion().getId_region() == null) {
            throw new RuntimeException("La región es obligatoria y debe tener un id_region.");
        }
        Comunas comuna = comunasRepository.findById(direccion.getComuna().getId_comuna())
            .orElseThrow(() -> new RuntimeException("Comuna no encontrada"));
        Regiones region = regionesRepository.findById(direccion.getRegion().getId_region())
            .orElseThrow(() -> new RuntimeException("Región no encontrada"));
        direccionExistente.setComuna(comuna);
        direccionExistente.setRegion(region);
        return direccionesRepository.save(direccionExistente);
    }
    //delete
    //metodo para eliminar una direccion
    public void eliminarDireccion(Long id) {
        Direcciones direccion = direccionesRepository.findById(id).orElseThrow(() -> new RuntimeException("Direccion no encontrada" + id));
        direccionesRepository.delete(direccion);
    }

}
