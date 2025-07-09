package com.example.CONTRATACIONES.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.CONTRATACIONES.model.Contrataciones;
import com.example.CONTRATACIONES.repository.ContratacionesRepository;

import java.time.LocalDate;

@Configuration
public class LoadDataConfig {

    @Bean
    CommandLineRunner initDatabase(ContratacionesRepository contratacionesRepository) {
        return args -> {
            if (contratacionesRepository.count() == 0) {
                
                // Contratación 1: Instalación de 8 paneles solares en techo plano
                Contrataciones contratacion1 = new Contrataciones();
                contratacion1.setFecha_Solicitud(LocalDate.of(2025, 7, 1)); // 2025-07-01
                contratacion1.setDescripcion("Instalación de 8 paneles solares en techo plano");
                contratacion1.setId_usuario(1L);
                contratacion1.setId_servicio(2L);
                contratacion1.setId_proyecto(1L);
                contratacion1.setId_estado(1L);
                contratacionesRepository.save(contratacion1);

                // Contratación 2: Mantenimiento preventivo de sistema solar residencial
                Contrataciones contratacion2 = new Contrataciones();
                contratacion2.setFecha_Solicitud(LocalDate.of(2025, 7, 2)); // 2025-07-02
                contratacion2.setDescripcion("Mantenimiento preventivo de sistema solar residencial");
                contratacion2.setId_usuario(2L);
                contratacion2.setId_servicio(3L);
                contratacion2.setId_proyecto(2L);
                contratacion2.setId_estado(2L);
                contratacionesRepository.save(contratacion2);

                // Contratación 3: Evaluación para instalación solar en empresa comercial
                Contrataciones contratacion3 = new Contrataciones();
                contratacion3.setFecha_Solicitud(LocalDate.of(2025, 7, 3)); // 2025-07-03
                contratacion3.setDescripcion("Evaluación para instalación solar en empresa comercial");
                contratacion3.setId_usuario(3L);
                contratacion3.setId_servicio(1L);
                contratacion3.setId_proyecto(3L);
                contratacion3.setId_estado(1L);
                contratacionesRepository.save(contratacion3);

                // Contratación 4: Instalación de baterías y optimizador en sistema solar
                Contrataciones contratacion4 = new Contrataciones();
                contratacion4.setFecha_Solicitud(LocalDate.of(2025, 7, 4)); // 2025-07-04
                contratacion4.setDescripcion("Instalación de baterías y optimizador en sistema solar");
                contratacion4.setId_usuario(4L);
                contratacion4.setId_servicio(2L);
                contratacion4.setId_proyecto(4L);
                contratacion4.setId_estado(1L);
                contratacionesRepository.save(contratacion4);
                
                System.out.println("Datos iniciales de contrataciones cargados");
            } else {
                System.out.println("Los datos de contrataciones ya existen. No se cargaron nuevos datos.");
            }
        };
    }
} 