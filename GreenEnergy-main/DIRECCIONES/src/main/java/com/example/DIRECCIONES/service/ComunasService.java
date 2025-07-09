package com.example.DIRECCIONES.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DIRECCIONES.model.Comunas;
import com.example.DIRECCIONES.model.Regiones;
import com.example.DIRECCIONES.repository.ComunasRepository;
import com.example.DIRECCIONES.repository.RegionesRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunasService {
    @Autowired
    private ComunasRepository comunasRepository;

    @Autowired
    private RegionesRepository regionesRepository;

    //metodo para ver todas las comunas
    public List<Comunas> verTodasLasComunas() { 
        return comunasRepository.findAll();
    }

    //metodo para buscar una comuna por id
    public Comunas buscarComunaPorId(Long id) {
        return comunasRepository.findById(id).orElse(null);
    }

    //metodo para obtener una comuna por su region
    public List<Comunas> buscarComunasPorRegion(Long id_region) {
        Regiones region = regionesRepository.findById(id_region).orElseThrow(() -> new RuntimeException("Region no encontrada" + id_region));
        return comunasRepository.findAll().stream() 
                .filter(comuna -> comuna.getRegion().equals(region))
                .toList();  
    }   

    //metodo para crear una comuna nueva
    public Comunas crearComuna(String nombre) {
        Comunas nuevaComuna = new Comunas();
        nuevaComuna.setNombre(nombre);
        return comunasRepository.save(nuevaComuna);
    }

    //metodo para actualizar una comuna
    public Comunas actualizarComuna(Long id, String nombre) {
        Comunas comunaExistente = comunasRepository.findById(id).orElseThrow(() -> new RuntimeException("Comuna no encontrada" + id));
        comunaExistente.setNombre(nombre);
        return comunasRepository.save(comunaExistente);
    }

    //metodo para eliminar una comuna
    public void eliminarComuna(Long id) {
        Comunas comuna = comunasRepository.findById(id).orElseThrow(() -> new RuntimeException("Comuna no encontrada" + id));
        comunasRepository.delete(comuna);
    }

}
