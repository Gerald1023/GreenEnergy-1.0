package com.example.PROYECTOS.controller;

import com.example.PROYECTOS.model.Proyectos;
import com.example.PROYECTOS.service.ProyectosService;
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

// http://localhost:8084/doc/swagger-ui/index.html#/Proyectos
@RestController
@RequestMapping("/proyectos")
@Tag(name = "Proyectos", description = "Operaciones relacionadas con los proyectos")
public class ProyectosController {

    @Autowired
    private ProyectosService proyectosService;

    // metodos get

    // metodo para obtener todos los proyectos
    @GetMapping
    @Operation(summary = "Obtener todos los proyectos", description = "Retorna una lista de todos los proyectos registrados en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Proyectos encontrados y devueltos.", content = @Content(schema = @Schema(implementation = Proyectos.class))),
        @ApiResponse(responseCode = "204", description = "No hay proyectos registrados para devolver (lista vacía)."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al intentar obtener los proyectos.")
    })
    public ResponseEntity<?> getTodosLosProyectos() {
        try {
            List<Proyectos> proyectos = proyectosService.obtenerTodosLosProyectos();
            if (proyectos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(proyectos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los proyectos: " + e.getMessage());
        }
    }

    // metodo para obtener un proyecto por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener proyecto por ID", description = "Busca y retorna un proyecto por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Proyecto encontrado y devuelto.", content = @Content(schema = @Schema(implementation = Proyectos.class))),
        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getProyectoPorId(@PathVariable Long id) {
        try {
            Proyectos proyecto = proyectosService.obtenerProyectoPorId(id);
            return ResponseEntity.ok(proyecto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Proyecto no encontrado con ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar el proyecto: " + e.getMessage());
        }
    }

    // metodos post

    // metodo para crear un nuevo proyecto
    @PostMapping("/crear")
    @Operation(summary = "Crear un nuevo proyecto", description = "Crea un proyecto con los datos proporcionados.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Proyecto creado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes en la petición."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear el proyecto.")
    })
    public ResponseEntity<?> postCrearProyecto(@RequestBody Proyectos proyecto) {
        try {
            Proyectos nuevo = proyectosService.crearProyecto(proyecto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el proyecto: " + e.getMessage());
        }
    }

    // metodos put

    // metodo para actualizar un proyecto
    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualizar proyecto", description = "Actualiza los datos de un proyecto existente por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Proyecto actualizado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al actualizar el proyecto.")
    })
    public ResponseEntity<?> putActualizarProyecto(@PathVariable Long id, @RequestBody Proyectos proyecto) {
        try {
            Proyectos actualizado = proyectosService.actualizarProyecto(id, proyecto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el proyecto: " + e.getMessage());
        }
    }

    // metodo para actualizar el estado del proyecto
    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado del proyecto", description = "Actualiza el estado de un proyecto por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado del proyecto actualizado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al actualizar el estado del proyecto.")
    })
    public ResponseEntity<?> putActualizarEstadoProyecto(@PathVariable Long id, @RequestParam Long idEstado) {
        try {
            Proyectos actualizado = proyectosService.actualizarEstadoProyecto(id, idEstado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el estado del proyecto: " + e.getMessage());
        }
    }

    // metodos delete

    // metodo para eliminar un proyecto
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar proyecto", description = "Elimina un proyecto por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Proyecto eliminado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el proyecto.")
    })
    public ResponseEntity<?> deleteEliminarProyecto(@PathVariable Long id) {
        try {
            proyectosService.eliminarProyecto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el proyecto: " + e.getMessage());
        }
    }
} 