package com.example.DIRECCIONES.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name = "direcciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una dirección")
public class Direcciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la dirección")
    private Long id_direccion;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre de la calle")
    private String calle;

    @Column(nullable = false, length = 50)
    @Schema(description = "Número de la dirección")
    private String numero;

    @Column(nullable = false, length = 50) // departamento o casa etc
    @Schema(description = "Detalle adicional (departamento, casa, etc.)")
    private String detalle;
    
    @ManyToOne 
    @JoinColumn(name = "id_comuna", nullable = false)
    @Schema(description = "Comuna asociada a la dirección")
    private Comunas comuna;

    @ManyToOne
    @JoinColumn(name = "id_region", nullable = false)
    @Schema(description = "Región asociada a la dirección")
    private Regiones region;

    
}
