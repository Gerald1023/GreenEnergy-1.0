package com.example.CONTRATACIONES.controller;

import com.example.CONTRATACIONES.model.Contrataciones;
import com.example.CONTRATACIONES.service.ContratacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

// http://localhost:8087/doc/swagger-ui/index.html#/Contrataciones
@RestController
@RequestMapping("/contrataciones")
@Tag(name = "Contrataciones", description = "Operaciones relacionadas con las contrataciones")
public class ContratacionesController {

    @Autowired
    private ContratacionesService contratacionesService;
    //metodos para obtener todas las contrataciones
    @GetMapping
    @Operation(summary = "Obtener todas las contrataciones", description = "Retorna una lista de todas las contrataciones registradas en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contrataciones encontradas y devueltas.", content = @Content(schema = @Schema(implementation = Contrataciones.class))),
        @ApiResponse(responseCode = "204", description = "No hay contrataciones registradas para devolver (lista vacía)."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al intentar obtener las contrataciones.")
    })
    public ResponseEntity<?> getTodasLasContrataciones() {
        try {
            List<Contrataciones> contrataciones = contratacionesService.obtenerTodasLasContrataciones();
            if (contrataciones.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(contrataciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las contrataciones: " + e.getMessage());
        }
    }

    //metodos para obtener una contratación por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener contratación por ID", description = "Busca y retorna una contratación por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contratación encontrada y devuelta.", content = @Content(schema = @Schema(implementation = Contrataciones.class))),
        @ApiResponse(responseCode = "404", description = "Contratación no encontrada."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getContratacionPorId(@PathVariable Long id) {
        try {
            Contrataciones contratacion = contratacionesService.obtenerContratacionPorId(id);
            return ResponseEntity.ok(contratacion);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Contratación no encontrada con ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar la contratación: " + e.getMessage());
        }
    }

    //metodos para obtener contrataciones por usuario
    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Obtener contrataciones por usuario", description = "Busca contrataciones por el ID del usuario.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contrataciones encontradas y devueltas.", content = @Content(schema = @Schema(implementation = Contrataciones.class))),
        @ApiResponse(responseCode = "204", description = "No hay contrataciones para el usuario."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getContratacionesPorUsuario(@PathVariable Long idUsuario) {
        try {
            List<Contrataciones> contrataciones = contratacionesService.obtenerContratacionesPorUsuario(idUsuario);
            if (contrataciones.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(contrataciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las contrataciones del usuario: " + e.getMessage());
        }
    }

    //metodos para obtener contrataciones por proyecto
    @GetMapping("/proyecto/{idProyecto}")
    @Operation(summary = "Obtener contrataciones por proyecto", description = "Busca contrataciones por el ID del proyecto.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contrataciones encontradas y devueltas.", content = @Content(schema = @Schema(implementation = Contrataciones.class))),
        @ApiResponse(responseCode = "204", description = "No hay contrataciones para el proyecto."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getContratacionesPorProyecto(@PathVariable Long idProyecto) {
        try {
            List<Contrataciones> contrataciones = contratacionesService.obtenerContratacionesPorProyecto(idProyecto);
            if (contrataciones.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(contrataciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las contrataciones del proyecto: " + e.getMessage());
        }
    }
    //post
    //metodo para crear una nueva contratación
    @PostMapping("/crear")
    @Operation(summary = "Crear una nueva contratación", description = "Crea una contratación con los datos proporcionados.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Contratación creada exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes en la petición."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear la contratación.")
    })
    public ResponseEntity<?> postCrearContratacion(@RequestBody Contrataciones contratacion) {
        try {
            Contrataciones nueva = contratacionesService.crearContratacion(contratacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la contratación: " + e.getMessage());
        }
    }
    //put
    //metodo para actualizar el estado de una contratación
    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de la contratación", description = "Actualiza el estado de una contratación por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado de la contratación actualizado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Contratación no encontrada."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al actualizar el estado de la contratación.")
    })
    public ResponseEntity<?> putActualizarEstadoContratacion(@PathVariable Long id, @RequestParam Long nuevoEstadoId) {
        try {
            Contrataciones actualizada = contratacionesService.actualizarEstadoContratacion(id, nuevoEstadoId);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el estado de la contratación: " + e.getMessage());
        }
    }
    //put
    //metodo para asignar proyecto a una contratación
    @PutMapping("/{idContratacion}/asignarP")
    @Operation(summary = "Asignar proyecto a una contratación", description = "Asigna un proyecto a una contratación por sus IDs.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Proyecto asignado exitosamente a la contratación."),
        @ApiResponse(responseCode = "404", description = "Contratación o proyecto no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al asignar el proyecto.")
    })
    public ResponseEntity<?> putAsignarProyectoAContratacion(@PathVariable Long idContratacion, @RequestParam Long idProyecto) {
        try {
            Contrataciones actualizada = contratacionesService.asignarProyectoAContratacion(idContratacion, idProyecto);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al asignar proyecto a la contratación: " + e.getMessage());
        }
    }
    //metodo para eliminar una contratación
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar contratación", description = "Elimina una contratación por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Contratación eliminada exitosamente."),
        @ApiResponse(responseCode = "404", description = "Contratación no encontrada."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar la contratación.")
    })
    public ResponseEntity<?> deleteEliminarContratacion(@PathVariable Long id) {
        try {
            contratacionesService.eliminarContratacion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la contratación: " + e.getMessage());
        }
    }
}
