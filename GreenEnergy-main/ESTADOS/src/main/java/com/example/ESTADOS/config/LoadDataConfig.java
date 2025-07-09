package com.example.ESTADOS.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ESTADOS.model.Estados;
import com.example.ESTADOS.repository.EstadosRepository;

@Configuration
public class LoadDataConfig {

    @Bean
    CommandLineRunner initDatabase(EstadosRepository estadosRepository) {
        return args -> {
            if (estadosRepository.count() == 0) {
                
                // Estado 1: Pendiente
                Estados estadoPendiente = new Estados();
                estadoPendiente.setNombre("Pendiente");
                estadoPendiente.setActivo(true);
                estadosRepository.save(estadoPendiente);

                // Estado 2: En Curso
                Estados estadoEnCurso = new Estados();
                estadoEnCurso.setNombre("En Curso");
                estadoEnCurso.setActivo(true);
                estadosRepository.save(estadoEnCurso);

                // Estado 3: Finalizado
                Estados estadoFinalizado = new Estados();
                estadoFinalizado.setNombre("Finalizado");
                estadoFinalizado.setActivo(true);
                estadosRepository.save(estadoFinalizado);

                // Estado 4: Cancelado
                Estados estadoCancelado = new Estados();
                estadoCancelado.setNombre("Cancelado");
                estadoCancelado.setActivo(true);
                estadosRepository.save(estadoCancelado);

                // Estado 5: En Revisión
                Estados estadoEnRevision = new Estados();
                estadoEnRevision.setNombre("En Revisión");
                estadoEnRevision.setActivo(true);
                estadosRepository.save(estadoEnRevision);
                
                System.out.println("Datos iniciales de contrataciones cargados");
            } else {
                System.out.println("Los datos de contrataciones ya existen. No se cargaron nuevos datos.");
            }
        };
    }
}
