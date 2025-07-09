package com.example.DIRECCIONES.model;

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
@Table(name = "regiones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Regiones registradas en el sistema")
public class Regiones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la región")
    private Long id_region;

    @Column(nullable = false, length = 50)
    @Schema(description = "Nombre de la región")
    private String nombre;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "Comunas asociadas a la región")
    private List<Comunas> comunas;

}
