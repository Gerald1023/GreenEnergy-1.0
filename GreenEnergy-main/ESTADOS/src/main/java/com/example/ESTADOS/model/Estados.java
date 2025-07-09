package com.example.ESTADOS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "estados")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un estado de un proyecto")
public class Estados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del estado")
    private Long id;

    @Column(name = "nombre", nullable = false)
    @Schema(description = "Nombre del estado")
    private String nombre;

    @Column(name = "activo", nullable = false)
    @Schema(description = "Indica si el estado está activo")
    private Boolean activo = true;
}
