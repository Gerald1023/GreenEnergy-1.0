package com.example.USUARIOS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "usuarios")
@Data 
@AllArgsConstructor
@NoArgsConstructor   
@Schema(description = "Entidad que representa a un usuario del sistema")
public class Usuarios {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario")
    private Long id_usuario;

    @Column(nullable = false, length = 50)
    @Schema(description = "Nombre del usuario")
    private String nombre;

    @Column(nullable = false, length = 50)
    @Schema(description = "Apellido del usuario")
    private String apellido;

    @Column(nullable = false, length = 100)
    @Schema(description = "Correo electrónico del usuario")
    private String email;

    @Column(nullable = false, length = 100)
    @Schema(description = "Contraseña del usuario")
    private String contrasena;

    @Column(nullable = false, length = 15)
    @Schema(description = "Teléfono del usuario")
    private String telefono;
    
    @Column(name = "id_direccion", nullable = false)
    @Schema(description = "ID de la dirección asociada al usuario")
    private Long id_direccion;
    
    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    @JsonIgnore
    @Schema(description = "Rol del usuario")
    private Roles rol;

}
