package com.example.PROYECTOS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.PROYECTOS.model.Proyectos;
import com.example.PROYECTOS.repository.ProyectosRepository;

import java.time.LocalDate;

@Configuration
public class LoadDataConfig {

    @Bean
    CommandLineRunner initDatabase(ProyectosRepository proyectosRepository) {
        return args -> {
            if (proyectosRepository.count() == 0) {
                
                // Proyecto 1: Instalación de 8 paneles solares en techo plano
                Proyectos proyecto1 = new Proyectos();
                proyecto1.setNombre("Instalación Solar Residencial - Casa García");
                proyecto1.setFecha_inicio(LocalDate.of(2025, 7, 1)); // 2025-07-01
                proyecto1.setFecha_fin(LocalDate.of(2025, 7, 15)); // 2025-07-15
                proyecto1.setAvance("25%");
                proyecto1.setId_contratacion(1L);
                proyecto1.setId_estado(2L); // En curso
                proyectosRepository.save(proyecto1);

                // Proyecto 2: Mantenimiento preventivo de sistema solar residencial
                Proyectos proyecto2 = new Proyectos();
                proyecto2.setNombre("Mantenimiento Sistema Solar - Edificio Central");
                proyecto2.setFecha_inicio(LocalDate.of(2025, 7, 2)); // 2025-07-02
                proyecto2.setFecha_fin(LocalDate.of(2025, 7, 5)); // 2025-07-05
                proyecto2.setAvance("75%");
                proyecto2.setId_contratacion(2L);
                proyecto2.setId_estado(2L); // En curso
                proyectosRepository.save(proyecto2);

                // Proyecto 3: Evaluación para instalación solar en empresa comercial
                Proyectos proyecto3 = new Proyectos();
                proyecto3.setNombre("Evaluación Solar Comercial - Empresa TechCorp");
                proyecto3.setFecha_inicio(LocalDate.of(2025, 7, 3)); // 2025-07-03
                proyecto3.setFecha_fin(LocalDate.of(2025, 7, 10)); // 2025-07-10
                proyecto3.setAvance("10%");
                proyecto3.setId_contratacion(3L);
                proyecto3.setId_estado(1L); // Pendiente
                proyectosRepository.save(proyecto3);

                // Proyecto 4: Instalación de baterías y optimizador en sistema solar
                Proyectos proyecto4 = new Proyectos();
                proyecto4.setNombre("Mejora Sistema Solar - Baterías y Optimizadores");
                proyecto4.setFecha_inicio(LocalDate.of(2025, 7, 4)); // 2025-07-04
                proyecto4.setFecha_fin(LocalDate.of(2025, 7, 20)); // 2025-07-20
                proyecto4.setAvance("0%");
                proyecto4.setId_contratacion(4L);
                proyecto4.setId_estado(1L); // Pendiente
                proyectosRepository.save(proyecto4);
                
                System.out.println("Datos iniciales de proyectos cargados");
            } else {
                System.out.println("Los datos de proyectos ya existen. No se cargaron nuevos datos.");
            }
        };
    }
} 