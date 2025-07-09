package com.example.PROYECTOS.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "proyectos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un proyecto")
public class Proyectos {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del proyecto")
    private Long id_proyecto;

    @Column(nullable = false)
    @Schema(description = "Nombre del proyecto")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Fecha de inicio del proyecto")
    private LocalDate Fecha_inicio;

    @Column(nullable = false)
    @Schema(description = "Fecha de fin del proyecto")
    private LocalDate Fecha_fin;

    @Column(nullable = false)
    @Schema(description = "Avance del proyecto")
    private String avance;

    @Column(nullable = false)
    @Schema(description = "ID de la contratación asociada")
    private Long id_contratacion;

    @Column(nullable = false)
    @Schema(description = "ID del estado del proyecto")
    private Long id_estado;

    

}
