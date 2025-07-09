package com.example.USUARIOS.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un rol de usuario en el sistema")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del rol")
    private Long id_rol;

    @Column(nullable = false, unique = true, length = 50)
    @Schema(description = "Nombre del rol")
    private String nombre;

    @OneToMany(mappedBy = "rol",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "Usuarios asociados a este rol")
    private List<Usuarios> usuarios;
}
