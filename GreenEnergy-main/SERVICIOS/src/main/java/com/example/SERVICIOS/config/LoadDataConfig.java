package com.example.SERVICIOS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.SERVICIOS.model.Servicios;
import com.example.SERVICIOS.repository.ServiciosRepository;

@Configuration
public class LoadDataConfig {

    @Bean
    CommandLineRunner initDatabase(ServiciosRepository serviciosRepository) {
        return args -> {
            if (serviciosRepository.count() == 0) {
                
                // Servicio 1: Evaluación de instalación solar
                Servicios servicio1 = new Servicios();
                servicio1.setNombre("Evaluación de Instalación Solar");
                servicio1.setDescripcion("Evaluación técnica completa para determinar la viabilidad y optimización de instalación de paneles solares en su propiedad. Incluye análisis de consumo, orientación del techo, sombras y cálculo de ahorro energético.");
                servicio1.setPrecio(150000.0); // $150,000 CLP
                serviciosRepository.save(servicio1);

                // Servicio 2: Instalación de paneles solares residencial
                Servicios servicio2 = new Servicios();
                servicio2.setNombre("Instalación Paneles Solares Residencial");
                servicio2.setDescripcion("Instalación completa de sistema solar fotovoltaico para viviendas. Incluye paneles solares, inversor, sistema de montaje, cableado y conexión a la red eléctrica. Capacidad de 3kW a 10kW.");
                servicio2.setPrecio(2500000.0); // $2,500,000 CLP
                serviciosRepository.save(servicio2);

                // Servicio 3: Mantenimiento preventivo de sistemas solares
                Servicios servicio3 = new Servicios();
                servicio3.setNombre("Mantenimiento Preventivo Solar");
                servicio3.setDescripcion("Servicio de mantenimiento preventivo para sistemas solares existentes. Incluye limpieza de paneles, verificación de conexiones, revisión del inversor, análisis de rendimiento y reporte técnico detallado.");
                servicio3.setPrecio(120000.0); // $120,000 CLP
                serviciosRepository.save(servicio3);

                // Servicio 4: Instalación de baterías de respaldo
                Servicios servicio4 = new Servicios();
                servicio4.setNombre("Instalación Baterías de Respaldo");
                servicio4.setDescripcion("Instalación de sistema de baterías para respaldo energético. Compatible con sistemas solares existentes. Incluye baterías de litio, controlador de carga, instalación y configuración del sistema de respaldo.");
                servicio4.setPrecio(1800000.0); // $1,800,000 CLP
                serviciosRepository.save(servicio4);

                // Servicio 5: Optimización de sistemas solares existentes
                Servicios servicio5 = new Servicios();
                servicio5.setNombre("Optimización de Sistemas Solares");
                servicio5.setDescripcion("Servicio de optimización para sistemas solares ya instalados. Incluye análisis de rendimiento, actualización de componentes, mejora de eficiencia, instalación de optimizadores y reprogramación del inversor.");
                servicio5.setPrecio(800000.0); // $800,000 CLP
                serviciosRepository.save(servicio5);
                
                System.out.println("Datos iniciales cargados");
            } else {
                System.out.println("Los datos ya existen. No se cargaron nuevos datos.");
            }
        };
    }
} 