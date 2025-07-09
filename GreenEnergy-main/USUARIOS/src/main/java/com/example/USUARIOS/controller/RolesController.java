package com.example.USUARIOS.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.USUARIOS.model.Roles;
import com.example.USUARIOS.service.RolesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles", description = "Operaciones relacionadas con la gestión de roles de usuario")
public class RolesController {
    @Autowired
    private RolesService rolesService;

   
    // metodo para obtener todos los roles
    @GetMapping
    @Operation(summary = "Obtener todos los roles", description = "Retorna una lista de todos los roles registrados en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Roles encontrados y devueltos.", content = @Content(schema = @Schema(implementation = Roles.class))),
        @ApiResponse(responseCode = "204", description = "No hay roles registrados para devolver (lista vacía)."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al intentar obtener los roles.")
    })
    public ResponseEntity<?> getAllRoles() {
        try {
            List<Roles> roles = rolesService.TodosLosRoles();
            
            if (roles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No hay roles registrados en el sistema");
            }
            
            return ResponseEntity.ok(roles);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }
    
    // metodo para obtener un rol por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener rol por ID", description = "Busca y retorna un rol por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol encontrado y devuelto.", content = @Content(schema = @Schema(implementation = Roles.class))),
        @ApiResponse(responseCode = "400", description = "ID inválido o mal formado."),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body("El ID del rol debe ser un número válido mayor a 0");
            }
            
            Roles role = rolesService.BuscarRolPorId(id);
            
            if (role == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol con ID " + id + " no encontrado");
            }
            
            return ResponseEntity.ok(role);
            
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                .body("El ID debe ser un número válido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }
    
    // metodo para crear un nuevo rol
    @PostMapping("/crear")
    @Operation(summary = "Crear un nuevo rol", description = "Crea un rol con los datos proporcionados en el cuerpo de la petición.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Rol creado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes en la petición."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear el rol.")
    })
    public ResponseEntity<?> createRole(@RequestBody Map<String, String> datosRol) {
        try {
            // metodo para validar que el body no esté vacío
            if (datosRol == null || datosRol.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("El cuerpo de la petición no puede estar vacío");
            }
            
            String nombre = datosRol.get("nombre");
            
            // metodo para validar que el campo requerido
            if (nombre == null || nombre.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("El nombre del rol es requerido");
            }
            
            // metodo para validar la longitud del nombre
            if (nombre.trim().length() < 2) {
                return ResponseEntity.badRequest()
                    .body("El nombre del rol debe tener al menos 2 caracteres");
            }
            
            if (nombre.trim().length() > 50) {
                return ResponseEntity.badRequest()
                    .body("El nombre del rol no puede exceder 50 caracteres");
            }
            
            Roles nuevoRol = rolesService.CrearRol(nombre.trim());
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("Rol creado exitosamente: " + nuevoRol.getNombre());
                
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }
    
    // metodo para actualizar un rol
    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualizar rol", description = "Actualiza los datos de un rol existente por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes en la petición."),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al actualizar el rol.")
    })
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody Map<String, String> datosRol) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body("El ID del rol debe ser un número válido mayor a 0");
            }
            
            if (datosRol == null || datosRol.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("El cuerpo de la petición no puede estar vacío");
            }
            
            String nombre = datosRol.get("nombre");
            
            // metodo para validar que el campo requerido
            if (nombre == null || nombre.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("El nombre del rol es requerido");
            }
            
            // metodo para validar la longitud del nombre
            if (nombre.trim().length() < 2) {
                return ResponseEntity.badRequest()
                    .body("El nombre del rol debe tener al menos 2 caracteres");
            }
            
            if (nombre.trim().length() > 50) {
                return ResponseEntity.badRequest()
                    .body("El nombre del rol no puede exceder 50 caracteres");
            }
            
            Roles updatedRole = rolesService.ActualizarRol(id, nombre.trim());
            
            return ResponseEntity.ok("Rol actualizado exitosamente: " + updatedRole.getNombre());
            
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                .body("El ID debe ser un número válido");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Rol no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol con ID " + id + " no encontrado");
            }
            return ResponseEntity.badRequest()
                .body("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }
    
    // metodo para eliminar un rol
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar rol", description = "Elimina un rol por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol eliminado exitosamente."),
        @ApiResponse(responseCode = "400", description = "ID inválido o mal formado."),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el rol.")
    })
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body("El ID del rol debe ser un número válido mayor a 0");
            }
            
            rolesService.EliminarRol(id);
            
            return ResponseEntity.ok("Rol con ID " + id + " eliminado exitosamente");
            
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                .body("El ID debe ser un número válido");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Rol no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol con ID " + id + " no encontrado");
            }
            return ResponseEntity.badRequest()
                .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }
}
