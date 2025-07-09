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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.USUARIOS.model.Usuarios;
import com.example.USUARIOS.model.Roles;
import com.example.USUARIOS.service.UsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

//http://localhost:8081/doc/swagger-ui/index.html#/Usuarios
@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios")
public class UsuariosController {
    
    @Autowired
    private UsuariosService usuariosService;
    
    
    // get
    //metodo para obtener todos los usuarios
    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de todos los usuarios registrados en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados y devueltos.", content = @Content(schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "204", description = "No hay usuarios registrados para devolver (lista vacía)."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al intentar obtener los usuarios.")
    })
    public ResponseEntity<?> getAllUsuarios() {
        try {
            List<Usuarios> usuarios = usuariosService.TodosLosUsuarios();
            
            if (usuarios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No hay usuarios registrados en el sistema");
            }
            
            return ResponseEntity.ok(usuarios);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }
    // get
    // metodo para buscar un usuario por ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Busca y retorna un usuario por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado y devuelto.", content = @Content(schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "400", description = "ID inválido o mal formado."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body("El ID del usuario debe ser un número válido mayor a 0");
            }
            
            Usuarios usuario = usuariosService.BuscarUsuarioPorId(id);
            
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario con ID " + id + " no encontrado");
            }
            
            return ResponseEntity.ok(usuario);
            
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                .body("El ID debe ser un número válido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }
    
    // post
    // metodo para crear un nuevo usuario
    @PostMapping("/crear")
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un usuario con los datos proporcionados en el cuerpo de la petición.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes en la petición."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear el usuario.")
    })
    public ResponseEntity<?> createUsuario(@RequestBody Map<String, Object> datosUsuario) {
        try {
            // Validar que el body no esté vacío
            if (datosUsuario == null || datosUsuario.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("El cuerpo de la petición no puede estar vacío");
            }
            
            // metodo para validar que los campos requeridos
            if (datosUsuario.get("nombre") == null || datosUsuario.get("nombre").toString().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("El nombre es requerido");
            }
            if (datosUsuario.get("apellido") == null || datosUsuario.get("apellido").toString().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("El apellido es requerido");
            }
            if (datosUsuario.get("email") == null || datosUsuario.get("email").toString().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("El email es requerido");
            }
            if (datosUsuario.get("contrasena") == null || datosUsuario.get("contrasena").toString().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("La contraseña es requerida");
            }
            if (datosUsuario.get("telefono") == null || datosUsuario.get("telefono").toString().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("El teléfono es requerido");
            }
            if (datosUsuario.get("id_rol") == null) {
                return ResponseEntity.badRequest()
                    .body("El ID del rol es requerido. Use GET /roles para ver los roles disponibles");
            }
            if (datosUsuario.get("id_direccion") == null) {
                return ResponseEntity.badRequest()
                    .body("El ID de la dirección es requerido");
            }
            
            // metodo para crear objeto Usuario con los datos recibidos
            Usuarios usuario = new Usuarios();
            usuario.setNombre(datosUsuario.get("nombre").toString());
            usuario.setApellido(datosUsuario.get("apellido").toString());
            usuario.setEmail(datosUsuario.get("email").toString());
            usuario.setContrasena(datosUsuario.get("contrasena").toString());
            usuario.setTelefono(datosUsuario.get("telefono").toString());
            
            // metodo para crear rol con solo el ID
            Roles rol = new Roles();
            rol.setId_rol(((Number) datosUsuario.get("id_rol")).longValue());
            usuario.setRol(rol);
            
            // metodo para asignar ID de dirección
            usuario.setId_direccion(((Number) datosUsuario.get("id_direccion")).longValue());
            

            Usuarios nuevoUsuario = usuariosService.crearUsuarios(usuario);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario creado exitosamente: " + nuevoUsuario.getNombre() + " " + nuevoUsuario.getApellido() + 
                      " con rol: " + nuevoUsuario.getRol().getNombre());
                
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest()
                .body("Error en el formato de datos: ID de rol y dirección deben ser números");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }
    
    // put
    //metodo para actualizar usuario
    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes en la petición."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al actualizar el usuario.")
    })
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Map<String, Object> datosUsuario) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body("El ID del usuario debe ser un número válido mayor a 0");
            }
            
            if (datosUsuario == null || datosUsuario.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("El cuerpo de la petición no puede estar vacío");
            }
            
            // metodo para crear objeto Usuario con los datos recibidos
            Usuarios usuario = new Usuarios();
            
            if (datosUsuario.get("nombre") != null) {
                usuario.setNombre(datosUsuario.get("nombre").toString());
            }
            if (datosUsuario.get("apellido") != null) {
                usuario.setApellido(datosUsuario.get("apellido").toString());
            }
            if (datosUsuario.get("email") != null) {
                usuario.setEmail(datosUsuario.get("email").toString());
            }
            if (datosUsuario.get("contrasena") != null) {
                usuario.setContrasena(datosUsuario.get("contrasena").toString());
            }
            if (datosUsuario.get("telefono") != null) {
                usuario.setTelefono(datosUsuario.get("telefono").toString());
            }
            
            // metodo para manejar rol si se proporciona
            if (datosUsuario.get("id_rol") != null) {
                Roles rol = new Roles();
                rol.setId_rol(((Number) datosUsuario.get("id_rol")).longValue());
                usuario.setRol(rol);
            }
            
            // metodo para manejar dirección si se proporciona
            if (datosUsuario.get("id_direccion") != null) {
                usuario.setId_direccion(((Number) datosUsuario.get("id_direccion")).longValue());
            }

            Usuarios usuarioActualizado = usuariosService.actualizarUsuario(id, usuario);
            
            return ResponseEntity.ok("Usuario actualizado exitosamente: " + usuarioActualizado.getNombre());
            
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest()
                .body("Error en el formato de datos: ID de rol y dirección deben ser números");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Usuario no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario con ID " + id + " no encontrado");
            }
            return ResponseEntity.badRequest()
                .body("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    }
    // delete
    //metodo para eliminar usuario
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente."),
        @ApiResponse(responseCode = "400", description = "ID inválido o mal formado."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el usuario.")
    })
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body("El ID del usuario debe ser un número válido mayor a 0");
            }
            
            usuariosService.eliminarUsuario(id);
            
            return ResponseEntity.ok("Usuario con ID " + id + " eliminado exitosamente");
            
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                .body("El ID debe ser un número válido");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Usuario no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario con ID " + id + " no encontrado");
            }
            return ResponseEntity.badRequest()
                .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + e.getMessage());
        }
    } 
    // post
    // metodo para iniciar sesión
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario autenticarse con email y contraseña.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes en la petición."),
        @ApiResponse(responseCode = "401", description = "Contraseña incorrecta."),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor durante el login.")
    })
    public ResponseEntity<?> login(@RequestBody Map<String, String> datosLogin) {
        try {
            // metodo para validar que el body no esté vacío
            if (datosLogin == null || datosLogin.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("El cuerpo de la petición no puede estar vacío");
            }
            
            String email = datosLogin.get("email");
            String contrasena = datosLogin.get("contrasena");

            // metodo para validar que los campos requeridos
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("El email es requerido");
            }
            if (contrasena == null || contrasena.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body("La contraseña es requerida");
            }

            // metodo para intentar hacer login
            Usuarios usuario = usuariosService.login(email, contrasena);
            
            // metodo para login exitoso
            return ResponseEntity.ok("Login exitoso - Bienvenido " + usuario.getNombre() + " " + usuario.getApellido());

        } catch (RuntimeException e) {
            if (e.getMessage().contains("Correo no registrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado con el email proporcionado");
            }
            if (e.getMessage().contains("Contraseña incorrecta")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Contraseña incorrecta");
            }
            return ResponseEntity.badRequest()
                .body("Error de autenticación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor durante el login");
        }
    }
}
