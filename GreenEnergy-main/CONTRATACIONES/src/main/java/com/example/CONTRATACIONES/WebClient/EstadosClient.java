package com.example.CONTRATACIONES.WebClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class EstadosClient {

    //variable para la comunicación
    private final WebClient webclient;

    //metodo constructor de la clase
    public EstadosClient(@Value("${estados-service.url}") String baseUrl) {
        this.webclient = WebClient.builder().baseUrl(baseUrl).build();
    }

    // Verificar si existe el estado
    public boolean existeEstado(Long idEstado) {
        try {
            webclient.get()
                    .uri("/estados/" + idEstado)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true; // Si llega aquí, el estado existe
        } catch (WebClientResponseException.NotFound e) {
            return false; // Estado no encontrado
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error en la comunicación con el microservicio de estados: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar estado: " + e.getMessage() + ". Verifica que el microservicio de estados esté ejecutándose en http://localhost:8085"); 
        }
    }

    // Obtener información del estado
    public Object obtenerEstado(Long idEstado) {
        try {
            return webclient.get()
                    .uri("/estados/" + idEstado)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Estado no encontrado con ID: " + idEstado);
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error en la comunicación con el microservicio de estados: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener estado: " + e.getMessage() + ". Verifica que el microservicio de estados esté ejecutándose en http://localhost:8085"); 
        }
    }
} 