package com.example.RESENA.controller;

import com.example.RESENA.model.Resena;
import com.example.RESENA.service.ResenaService;
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

// http://localhost:8083/doc/swagger-ui/index.html#/Reseñas
@RestController
@RequestMapping("/resenas")
@Tag(name = "Reseñas", description = "Operaciones relacionadas con las reseñas de servicios")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    // metodos get

    // metodo para obtener todas las reseñas
    @GetMapping
    @Operation(summary = "Obtener todas las reseñas", description = "Retorna una lista de todas las reseñas registradas en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reseñas encontradas y devueltas.", content = @Content(schema = @Schema(implementation = Resena.class))),
        @ApiResponse(responseCode = "204", description = "No hay reseñas registradas para devolver (lista vacía)."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al intentar obtener las reseñas.")
    })
    public ResponseEntity<?> getTodasLasResenas() {
        try {
            List<Resena> resenas = resenaService.obtenerTodasLasResenas();
            if (resenas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(resenas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las reseñas: " + e.getMessage());
        }
    }

    // metodo para obtener una reseña por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener reseña por ID", description = "Busca y retorna una reseña por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reseña encontrada y devuelta.", content = @Content(schema = @Schema(implementation = Resena.class))),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getResenaPorId(@PathVariable Long id) {
        try {
            Resena resena = resenaService.obtenerResenaPorId(id);
            return ResponseEntity.ok(resena);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reseña no encontrada con ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar la reseña: " + e.getMessage());
        }
    }

    // metodo para obtener reseñas por usuario
    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Obtener reseñas por usuario", description = "Busca reseñas por el ID del usuario.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reseñas encontradas y devueltas.", content = @Content(schema = @Schema(implementation = Resena.class))),
        @ApiResponse(responseCode = "204", description = "No hay reseñas para el usuario."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getResenasPorUsuario(@PathVariable Long idUsuario) {
        try {
            List<Resena> resenas = resenaService.obtenerResenasPorUsuario(idUsuario);
            if (resenas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(resenas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las reseñas del usuario: " + e.getMessage());
        }
    }

    // metodo para obtener reseñas por servicio
    @GetMapping("/servicio/{idServicio}")
    @Operation(summary = "Obtener reseñas por servicio", description = "Busca reseñas por el ID del servicio.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reseñas encontradas y devueltas.", content = @Content(schema = @Schema(implementation = Resena.class))),
        @ApiResponse(responseCode = "204", description = "No hay reseñas para el servicio."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getResenasPorServicio(@PathVariable Long idServicio) {
        try {
            List<Resena> resenas = resenaService.obtenerResenasPorServicio(idServicio);
            if (resenas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(resenas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las reseñas del servicio: " + e.getMessage());
        }
    }

    // metodos post

    // metodo para crear una nueva reseña
    @PostMapping("/crear")
    @Operation(summary = "Crear una nueva reseña", description = "Crea una reseña con los datos proporcionados.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes en la petición."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear la reseña.")
    })
    public ResponseEntity<?> postCrearResena(@RequestBody Resena resena) {
        try {
            Resena nueva = resenaService.crearResena(resena);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la reseña: " + e.getMessage());
        }
    }

    // metodos delete

    // metodo para eliminar una reseña
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Reseña eliminada exitosamente."),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar la reseña.")
    })
    public ResponseEntity<?> deleteEliminarResena(@PathVariable Long id) {
        try {
            resenaService.eliminarResena(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la reseña: " + e.getMessage());
        }
    }
} 