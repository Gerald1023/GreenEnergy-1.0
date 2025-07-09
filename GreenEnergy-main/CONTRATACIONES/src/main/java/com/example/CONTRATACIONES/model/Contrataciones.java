package com.example.CONTRATACIONES.model;

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
@Table(name = "contrataciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una contratación")
public class Contrataciones {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la contratación")
    private Long id_contratacion;

    @Column(nullable = false)
    @Schema(description = "Fecha de solicitud de la contratación")
    private LocalDate fecha_Solicitud;

    @Column(nullable = false)
    @Schema(description = "Descripción de la contratación")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "ID del usuario que realiza la contratación")
    private Long id_usuario;

    @Column(nullable = false)
    @Schema(description = "ID del servicio contratado")
    private Long id_servicio;

    @Column(nullable = true)
    @Schema(description = "ID del proyecto asociado (opcional)")
    private Long id_proyecto;

    @Column(nullable = false)
    @Schema(description = "ID del estado de la contratación")
    private Long id_estado;
   
    
}
