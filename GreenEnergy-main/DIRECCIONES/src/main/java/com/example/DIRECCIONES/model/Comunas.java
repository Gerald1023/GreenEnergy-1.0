package com.example.DIRECCIONES.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "comunas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Comunas registradas en el sistema")
public class Comunas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la comuna")
    private Long id_comuna;

    @Column(nullable = false, length = 50)
    @Schema(description = "Nombre de la comuna")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_region", nullable = false)
    @JsonIgnore
    @Schema(description = "Región a la que pertenece la comuna")
    private Regiones region;

    @OneToMany(mappedBy = "comuna")
    @Schema(description = "Direcciones asociadas a la comuna")
    private List<Direcciones> direcciones;

    
}
