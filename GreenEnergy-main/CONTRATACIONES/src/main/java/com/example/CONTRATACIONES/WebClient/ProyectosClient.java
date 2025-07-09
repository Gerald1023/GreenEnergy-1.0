package com.example.CONTRATACIONES.WebClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class ProyectosClient {

    //variable para la comunicación
    private final WebClient webclient;

    //metodo constructor de la clase
    public ProyectosClient(@Value("${proyectos-service.url}") String baseUrl) {
        this.webclient = WebClient.builder().baseUrl(baseUrl).build();
    }

    // Verificar si existe el proyecto
    public boolean existeProyecto(Long idProyecto) {
        try {
            webclient.get()
                    .uri("/proyectos/" + idProyecto)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true; // Si llega aquí, el proyecto existe
        } catch (WebClientResponseException.NotFound e) {
            return false; // Proyecto no encontrado
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error en la comunicación con el microservicio de proyectos: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar proyecto: " + e.getMessage() + ". Verifica que el microservicio de proyectos esté ejecutándose en http://localhost:8083"); 
        }
    }

    // Obtener información del proyecto
    public Object obtenerProyecto(Long idProyecto) {
        try {
            return webclient.get()
                    .uri("/proyectos/" + idProyecto)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Proyecto no encontrado con ID: " + idProyecto);
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error en la comunicación con el microservicio de proyectos: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener proyecto: " + e.getMessage() + ". Verifica que el microservicio de proyectos esté ejecutándose en http://localhost:8083"); 
        }
    }
} 