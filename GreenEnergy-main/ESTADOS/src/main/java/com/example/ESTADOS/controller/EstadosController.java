package com.example.ESTADOS.controller;

import com.example.ESTADOS.model.Estados;
import com.example.ESTADOS.service.EstadosService;
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

// http://localhost:8085/doc/swagger-ui/index.html#/Estados
@RestController
@RequestMapping("/estados")
@Tag(name = "Estados", description = "Operaciones relacionadas con los estados de los proyectos")
public class EstadosController {

    @Autowired
    private EstadosService estadosService;

    // metodos get

    // metodos para obtener todos los estados
    @GetMapping
    @Operation(summary = "Obtener todos los estados", description = "Retorna una lista de todos los estados registrados en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estados encontrados y devueltos.", content = @Content(schema = @Schema(implementation = Estados.class))),
        @ApiResponse(responseCode = "204", description = "No hay estados registrados para devolver (lista vacía)."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al intentar obtener los estados.")
    })
    public ResponseEntity<?> getTodosLosEstados() {
        try {
            List<Estados> estados = estadosService.obtenerTodosLosEstados();
            if (estados.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(estados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los estados: " + e.getMessage());
        }
    }

    // metodos para obtener un estado por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener estado por ID", description = "Busca y retorna un estado por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado encontrado y devuelto.", content = @Content(schema = @Schema(implementation = Estados.class))),
        @ApiResponse(responseCode = "404", description = "Estado no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getEstadoPorId(@PathVariable Long id) {
        try {
            Estados estado = estadosService.obtenerEstadoPorId(id);
            return ResponseEntity.ok(estado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estado no encontrado con ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar el estado: " + e.getMessage());
        }
    }

    // metodos para buscar estados por nombre
    @GetMapping("/buscar")
    @Operation(summary = "Buscar estados por nombre", description = "Busca estados por nombre.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estados encontrados y devueltos.", content = @Content(schema = @Schema(implementation = Estados.class))),
        @ApiResponse(responseCode = "204", description = "No hay estados que coincidan con el nombre."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getBuscarPorNombre(@RequestParam String nombre) {
        try {
            List<Estados> estados = estadosService.buscarPorNombre(nombre);
            if (estados.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(estados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar estados: " + e.getMessage());
        }
    }

    // metodos post

    // metodos para crear un nuevo estado
    @PostMapping("/crear")
    @Operation(summary = "Crear un nuevo estado", description = "Crea un estado con los datos proporcionados.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Estado creado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes en la petición."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear el estado.")
    })
    public ResponseEntity<?> postCrearEstado(@RequestBody Estados estado) {
        try {
            Estados nuevo = estadosService.crearEstado(estado);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el estado: " + e.getMessage());
        }
    }

    // metodos put

    // metodos para actualizar un estado
    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualizar estado", description = "Actualiza los datos de un estado existente por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Estado no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al actualizar el estado.")
    })
    public ResponseEntity<?> putActualizarEstado(@PathVariable Long id, @RequestBody Estados estado) {
        try {
            Estados actualizado = estadosService.actualizarEstado(id, estado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el estado: " + e.getMessage());
        }
    }

    // metodos delete

    // metodos para eliminar un estado
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar estado", description = "Elimina un estado por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Estado eliminado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Estado no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el estado.")
    })
    public ResponseEntity<?> deleteEliminarEstado(@PathVariable Long id) {
        try {
            estadosService.eliminarEstado(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el estado: " + e.getMessage());
        }
    }
}



