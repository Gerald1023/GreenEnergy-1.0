package com.example.DIRECCIONES.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DIRECCIONES.model.Regiones;
import com.example.DIRECCIONES.repository.RegionesRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class RegionesService {
    @Autowired
    private RegionesRepository regionesRepository;

    //metodo para ver todas las regiones
    public List<Regiones> verTodasLasRegiones() { 
        return regionesRepository.findAll();
    }

    //metodos ara buscar una region por id
    public Regiones buscarRegionPorId(Long id) {
        return regionesRepository.findById(id).orElse(null);
    }

    //metodo para crear una region nueva
    public Regiones crearRegion(String nombre) {
        Regiones nuevaRegion = new Regiones();
        nuevaRegion.setNombre(nombre);
        return regionesRepository.save(nuevaRegion);
    }

    //metodo para actualizar una region
    public Regiones actualizarRegion(Long id, String nombre) {
        Regiones regionExistente = regionesRepository.findById(id).orElseThrow(() -> new RuntimeException("Region no encontrada" + id));
        regionExistente.setNombre(nombre);
        return regionesRepository.save(regionExistente);
    }

    //metodo para eliminar una region
    public void eliminarRegion(Long id) {
        Regiones region = regionesRepository.findById(id).orElseThrow(() -> new RuntimeException("Region no encontrada" + id));
        regionesRepository.delete(region);
    }
    
}