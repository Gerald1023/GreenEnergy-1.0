package com.example.PROYECTOS.WebClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class ContratacionesClient {

    //variable para la comunicación
    private final WebClient webclient;

    //metodo constructor de la clase
    public ContratacionesClient(@Value("${contrataciones-service.url}") String baseUrl) {
        this.webclient = WebClient.builder().baseUrl(baseUrl).build();
    }

    // Verificar si existe la contratación
    public boolean existeContratacion(Long idContratacion) {
        try {
            webclient.get()
                    .uri("/contrataciones/" + idContratacion)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true; // Si llega aquí, la contratación existe
        } catch (WebClientResponseException.NotFound e) {
            return false; // Contratación no encontrada
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error en la comunicación con el microservicio de contrataciones: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar contratación: " + e.getMessage() + ". Verifica que el microservicio de contrataciones esté ejecutándose en http://localhost:8087"); 
        }
    }

    // Obtener información de la contratación
    public Object obtenerContratacion(Long idContratacion) {
        try {
            return webclient.get()
                    .uri("/contrataciones/" + idContratacion)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Contratación no encontrada con ID: " + idContratacion);
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error en la comunicación con el microservicio de contrataciones: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener contratación: " + e.getMessage() + ". Verifica que el microservicio de contrataciones esté ejecutándose en http://localhost:8087"); 
        }
    }
} 