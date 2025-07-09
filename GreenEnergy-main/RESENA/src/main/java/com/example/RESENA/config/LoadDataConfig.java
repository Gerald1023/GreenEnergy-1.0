package com.example.RESENA.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.RESENA.model.Resena;
import com.example.RESENA.repository.ResenaRepository;

import java.time.LocalDate;

@Configuration
public class LoadDataConfig {

    @Bean
    CommandLineRunner initDatabase(ResenaRepository resenaRepository) {
        return args -> {
            if (resenaRepository.count() == 0) {
                
                // Reseña 1: Evaluación de instalación solar
                Resena resena1 = new Resena();
                resena1.setComentario("Excelente servicio de evaluación. El técnico fue muy profesional y detallado en su análisis. Me explicó todo el proceso y las opciones disponibles. Muy recomendado.");
                resena1.setCalificacion(5);
                resena1.setFecha(LocalDate.of(2025, 7, 1)); // 2025-07-01
                resena1.setId_usuario(1L);
                resena1.setId_servicio(1L);
                resenaRepository.save(resena1);

                // Reseña 2: Instalación de paneles solares
                Resena resena2 = new Resena();
                resena2.setComentario("La instalación fue perfecta. El equipo trabajó de manera eficiente y limpia. Los paneles están funcionando excelente y ya veo la diferencia en mi factura de electricidad.");
                resena2.setCalificacion(5);
                resena2.setFecha(LocalDate.of(2025, 7, 2)); // 2025-07-02
                resena2.setId_usuario(2L);
                resena2.setId_servicio(2L);
                resenaRepository.save(resena2);

                // Reseña 3: Mantenimiento preventivo
                Resena resena3 = new Resena();
                resena3.setComentario("Buen servicio de mantenimiento. El técnico revisó todo el sistema y limpió los paneles. El servicio fue rápido y profesional. Recomiendo el mantenimiento anual.");
                resena3.setCalificacion(4);
                resena3.setFecha(LocalDate.of(2025, 7, 3)); // 2025-07-03
                resena3.setId_usuario(3L);
                resena3.setId_servicio(3L);
                resenaRepository.save(resena3);

                // Reseña 4: Instalación de baterías
                Resena resena4 = new Resena();
                resena4.setComentario("La instalación de las baterías fue más compleja de lo esperado, pero el equipo fue muy paciente y explicó cada paso. Ahora tengo respaldo energético y estoy muy satisfecho.");
                resena4.setCalificacion(4);
                resena4.setFecha(LocalDate.of(2025, 7, 4)); // 2025-07-04
                resena4.setId_usuario(4L);
                resena4.setId_servicio(4L);
                resenaRepository.save(resena4);
                
                System.out.println("Datos iniciales cargados");
            } else {
                System.out.println("Los datos ya existen. No se cargaron nuevos datos.");
            }
        };
    }
} 