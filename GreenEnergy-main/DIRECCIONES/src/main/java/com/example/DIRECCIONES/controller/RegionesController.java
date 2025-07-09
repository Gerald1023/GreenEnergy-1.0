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

import com.example.DIRECCIONES.model.Regiones;
import com.example.DIRECCIONES.service.RegionesService;

@RestController
@RequestMapping("/regiones")
public class RegionesController {
    @Autowired
    private RegionesService regionesService;

    // Método para ver todas las regiones
    @GetMapping
    public ResponseEntity<?> getverTodasLasRegiones() {
        try {
            List<Regiones> regiones = regionesService.verTodasLasRegiones();

            if (regiones.isEmpty()) {
                return ResponseEntity.noContent().build();  // 204 
            }
            return ResponseEntity.ok(regiones); // 200 OK

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Solicitud inválida: " + e.getMessage());  // 400 

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error del servidor: " + e.getMessage()); // 500
        }
    }
    // Método para buscar una región por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBuscarRegionPorId(@PathVariable Long id) {
        try {
            Regiones region = regionesService.buscarRegionPorId(id); 
            if (region == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Región no encontrada con ID: " + id); // 404
            }
            return ResponseEntity.ok(region);   // 200 OK

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al buscar la región: " + e.getMessage()); // 500
        }
    }

    //metodo para crear una nueva región   
    @PostMapping("/crear")
    public ResponseEntity<?> postCrearRegion(@RequestBody Regiones region) {
        try {
            if (region.getNombre() == null || region.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre de la región es obligatorio."); // 400
            }
            Regiones nuevaRegion = regionesService.crearRegion(region.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRegion); // 201
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al crear la región: " + e.getMessage()); // 500
        }
    }

    // Métodos para actualizar 
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> putActualizarRegion(@PathVariable Long id, @RequestBody Regiones region) {
        try {
            if (region.getNombre() == null || region.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre de la región es obligatorio."); // 400
            }
            Regiones regionActualizada = regionesService.actualizarRegion(id, region.getNombre());
            return ResponseEntity.ok(regionActualizada); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Región no encontrada con ID: " + id); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al actualizar la región: " + e.getMessage()); // 500
        }
    }
    // Método para eliminar una región por ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteEliminarRegion(@PathVariable Long id) {
        try {
            regionesService.eliminarRegion(id);
            return ResponseEntity.noContent().build(); // 204

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Región no encontrada con ID: " + id); // 404

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al eliminar la región: " + e.getMessage()); // 500
        }
    }



}
    

