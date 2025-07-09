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

import com.example.DIRECCIONES.model.Direcciones;
import com.example.DIRECCIONES.service.DireccionesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

// http://localhost:8086/doc/swagger-ui/index.html#/Direcciones
@RestController
@RequestMapping("/direcciones")
@Tag(name = "Direcciones", description = "Operaciones relacionadas con las direcciones")
public class DireccionesController {
    @Autowired
    private DireccionesService direccionesService;
    

    //metodo para ver todas las direcciones
    @GetMapping
    @Operation(summary = "Obtener todas las direcciones", description = "Retorna una lista de todas las direcciones registradas en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Direcciones encontradas y devueltas.", content = @Content(schema = @Schema(implementation = Direcciones.class))),
        @ApiResponse(responseCode = "204", description = "No hay direcciones registradas para devolver (lista vacía)."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al intentar obtener las direcciones.")
    })
    public ResponseEntity<?> getverTodasLasDirecciones() {
        try {
            List<Direcciones> direcciones = direccionesService.verTodasLasDirecciones();
            if (direcciones.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }
            return ResponseEntity.ok(direcciones); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error del servidor: " + e.getMessage()); // 500 
        } 
    }

    //metodo para buscar una direccion por id
    @GetMapping("/{id}")
    @Operation(summary = "Obtener dirección por ID", description = "Busca y retorna una dirección por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dirección encontrada y devuelta.", content = @Content(schema = @Schema(implementation = Direcciones.class))),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getBuscarDireccionPorId(@PathVariable Long id) {
        try {
            Direcciones direccion = direccionesService.buscarDireccionPorId(id);
            if (direccion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Dirección no encontrada con ID: " + id); // 404
            }
            return ResponseEntity.ok(direccion); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) 
                                 .body("Error del servidor: " + e.getMessage()); // 500
        }
    }
    
    //metodo para crear una direccion nueva
    @PostMapping("/crear")
    @Operation(summary = "Crear una nueva dirección", description = "Crea una dirección con los datos proporcionados.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Dirección creada exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes en la petición."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear la dirección.")
    })
    public ResponseEntity<?> crearDireccion(@RequestBody Direcciones direccion) {
        try {
            if (direccion.getCalle() == null || direccion.getCalle().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La calle es obligatoria.");
            }
            if (direccion.getNumero() == null || direccion.getNumero().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El número es obligatorio.");
            }
            if (direccion.getDetalle() == null || direccion.getDetalle().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El detalle es obligatorio.");
            }
            if (direccion.getComuna() == null || direccion.getComuna().getId_comuna() == null) {
                return ResponseEntity.badRequest().body("La comuna es obligatoria y debe tener un id_comuna.");
            }
            if (direccion.getRegion() == null || direccion.getRegion().getId_region() == null) {
                return ResponseEntity.badRequest().body("La región es obligatoria y debe tener un id_region.");
            }
            Direcciones guardada = direccionesService.crearDireccion(direccion);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al crear la dirección: " + e.getMessage());
        }
    }
    //metodo para actualizar una direccion
    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualizar dirección", description = "Actualiza los datos de una dirección existente por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dirección actualizada exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes en la petición."),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al actualizar la dirección.")
    })
    public ResponseEntity<?> putActualizarDireccion(@PathVariable Long id, @RequestBody Direcciones direccion) {
        try {
            if (direccion.getCalle() == null || direccion.getCalle().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La calle es obligatoria.");
            }
            if (direccion.getNumero() == null || direccion.getNumero().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El número es obligatorio.");
            }
            if (direccion.getDetalle() == null || direccion.getDetalle().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El detalle es obligatorio.");
            }
            if (direccion.getComuna() == null || direccion.getComuna().getId_comuna() == null) {
                return ResponseEntity.badRequest().body("La comuna es obligatoria y debe tener un id_comuna.");
            }
            if (direccion.getRegion() == null || direccion.getRegion().getId_region() == null) {
                return ResponseEntity.badRequest().body("La región es obligatoria y debe tener un id_region.");
            }
            Direcciones actualizada = direccionesService.actualizarDireccion(id, direccion);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al actualizar dirección: " + e.getMessage());
        }
    }
    //metodo para eliminar una direccion
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar dirección", description = "Elimina una dirección por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Dirección eliminada exitosamente."),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar la dirección.")
    })
    public ResponseEntity<?> deleteEliminarDireccion(@PathVariable Long id) {
        try {
            direccionesService.eliminarDireccion(id);
            return ResponseEntity.noContent().build(); // 204 
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Dirección no encontrada con ID: " + id); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al eliminar la dirección: " + e.getMessage());// 500
        }
    }
    
    
}
