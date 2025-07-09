package com.example.RESENA.model;

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
@Table(name = "resenas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una reseña de un servicio")
public class Resena {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la reseña")
    private Long id_resena;

    @Column(nullable = false)
    @Schema(description = "Comentario de la reseña")
    private String comentario;

    @Column(nullable = false)
    @Schema(description = "Calificación otorgada en la reseña")
    private int calificacion;

    @Column(nullable = false)
    @Schema(description = "Fecha de la reseña")
    private LocalDate fecha;

    @Column(nullable = false)
    @Schema(description = "ID del usuario que realiza la reseña")
    private Long id_usuario;

    @Column(nullable = false)
    @Schema(description = "ID del servicio reseñado")
    private Long id_servicio;

    


}
