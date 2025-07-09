package com.example.SERVICIOS.controller;

import com.example.SERVICIOS.model.Servicios;
import com.example.SERVICIOS.service.ServiciosService;
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

//http://localhost:8082/doc/swagger-ui/index.html#/Servicios
@RestController
@RequestMapping("/servicios")
@Tag(name = "Servicios", description = "Operaciones relacionadas con los servicios ofrecidos")
public class ServiciosController {

    @Autowired
    private ServiciosService serviciosService;

    // metodos get

    // metodo para obtener todos los servicios
    @GetMapping
    @Operation(summary = "Obtener todos los servicios", description = "Retorna una lista de todos los servicios registrados en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servicios encontrados y devueltos.", content = @Content(schema = @Schema(implementation = Servicios.class))),
        @ApiResponse(responseCode = "204", description = "No hay servicios registrados para devolver (lista vacía)."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al intentar obtener los servicios.")
    })
    public ResponseEntity<?> getTodosLosServicios() {
        try {
            List<Servicios> servicios = serviciosService.obtenerTodosLosServicios();
            if (servicios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(servicios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los servicios: " + e.getMessage());
        }
    }

    // metodo para obtener un servicio por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener servicio por ID", description = "Busca y retorna un servicio por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servicio encontrado y devuelto.", content = @Content(schema = @Schema(implementation = Servicios.class))),
        @ApiResponse(responseCode = "404", description = "Servicio no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getServicioPorId(@PathVariable Long id) {
        try {
            Servicios servicio = serviciosService.obtenerServicioPorId(id);
            return ResponseEntity.ok(servicio);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Servicio no encontrado con ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar el servicio: " + e.getMessage());
        }
    }

    // metodo para buscar servicios por nombre
    @GetMapping("/buscar")
    @Operation(summary = "Buscar servicios por nombre", description = "Busca servicios por nombre.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servicios encontrados y devueltos.", content = @Content(schema = @Schema(implementation = Servicios.class))),
        @ApiResponse(responseCode = "204", description = "No hay servicios que coincidan con el nombre."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getBuscarPorNombre(@RequestParam String nombre) {
        try {
            List<Servicios> servicios = serviciosService.buscarPorNombre(nombre);
            if (servicios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(servicios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar servicios: " + e.getMessage());
        }
    }

    // metodos post

    // metodo para crear un nuevo servicio
    @PostMapping("/crear")
    @Operation(summary = "Crear un nuevo servicio", description = "Crea un servicio con los datos proporcionados.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Servicio creado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes en la petición."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear el servicio.")
    })
    public ResponseEntity<?> postCrearServicio(@RequestBody Servicios servicio) {
        try {
            Servicios nuevo = serviciosService.crearServicio(servicio);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el servicio: " + e.getMessage());
        }
    }

    // metodos put

    // metodo para actualizar un servicio
    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualizar servicio", description = "Actualiza los datos de un servicio existente por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servicio actualizado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Servicio no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al actualizar el servicio.")
    })
    public ResponseEntity<?> putActualizarServicio(@PathVariable Long id, @RequestBody Servicios servicio) {
        try {
            Servicios actualizado = serviciosService.actualizarServicio(id, servicio);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el servicio: " + e.getMessage());
        }
    }

    // metodos delete

    // metodo para eliminar un servicio
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar servicio", description = "Elimina un servicio por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Servicio eliminado exitosamente."),
        @ApiResponse(responseCode = "404", description = "Servicio no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el servicio.")
    })
    public ResponseEntity<?> deleteEliminarServicio(@PathVariable Long id) {
        try {
            serviciosService.eliminarServicio(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el servicio: " + e.getMessage());
        }
    }
}
