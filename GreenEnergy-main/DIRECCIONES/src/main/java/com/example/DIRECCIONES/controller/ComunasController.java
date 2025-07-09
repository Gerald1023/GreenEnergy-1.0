package com.example.DIRECCIONES.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.DIRECCIONES.model.Comunas;
import com.example.DIRECCIONES.service.ComunasService;

@RestController
@RequestMapping("/comunas")
public class ComunasController {
    @Autowired
    private ComunasService comunasService;
    //metodo para ver todas las comunas
    @GetMapping
    public ResponseEntity<?> getAllComunas() {
        try {
            List<Comunas> comunas = comunasService.verTodasLasComunas();
            if (comunas.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 
            }
            return ResponseEntity.ok(comunas); // 200 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error del servidor: " + e.getMessage()); // 500
        }
    }
    //metodo para buscar una comuna por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getComunaById(@PathVariable Long id) {
        try {
            Comunas comuna = comunasService.buscarComunaPorId(id); 
            if (comuna == null) { 
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Comuna no encontrada con ID: " + id); // 404 
            }
            return ResponseEntity.ok(comuna); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error del servidor: " + e.getMessage()); // 500
        }
    }

    //metodo para crear una nueva comuna
    @PostMapping("/crear")
    public ResponseEntity<?> postCrearComuna(@RequestBody Comunas comuna) {
        try {
            if (comuna.getNombre() == null || comuna.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                                     .body("El nombre de la comuna no puede estar vacío."); // 400 
            }
            Comunas nuevaComuna = comunasService.crearComuna(comuna.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaComuna); // 201 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al crear la comuna: " + e.getMessage()); // 500
        }
    }
    //metodo para actualizar una comuna
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> putActualizarComuna(@PathVariable Long id, @RequestBody Comunas comuna) {
        try {
            if (comuna.getNombre() == null || comuna.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                                     .body("El nombre de la comuna no puede estar vacío."); // 400
            }
            Comunas comunaActualizada = comunasService.actualizarComuna(id, comuna.getNombre());
            if (comunaActualizada == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Comuna con ID " + id + " no encontrada."); // 404
            }
            return ResponseEntity.ok(comunaActualizada); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al actualizar la comuna: " + e.getMessage()); // 500
        }
    }
    //metodo para eliminar una comuna por id
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteEliminarComuna(@PathVariable Long id) {
        try {
            comunasService.eliminarComuna(id);
            return ResponseEntity.noContent().build(); // 204 
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Comuna no encontrada con ID: " + id); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al eliminar la comuna: " + e.getMessage()); // 500
        }
    }
}



