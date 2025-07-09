package com.example.SERVICIOS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "servicios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un servicio ofrecido por el sistema")
public class Servicios {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del servicio")
    private Long id_servicio;

    @Column(name = "nombre", nullable = false)
    @Schema(description = "Nombre del servicio")
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    @Schema(description = "Descripción del servicio")
    private String descripcion;

    @Column(name = "precio", nullable = false)
    @Schema(description = "Precio del servicio")
    private Double precio;
}
