package com.example.CONTRATACIONES.WebClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class ServiciosClient {

    //variable para la comunicación
    private final WebClient webclient;

    //metodo constructor de la clase
    public ServiciosClient(@Value("${servicios-service.url}") String baseUrl) {
        this.webclient = WebClient.builder().baseUrl(baseUrl).build();
    }

    // Verificar si existe el servicio
    public boolean existeServicio(Long idServicio) {
        try {
            webclient.get()
                    .uri("/servicios/" + idServicio)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true; // Si llega aquí, el servicio existe
        } catch (WebClientResponseException.NotFound e) {
            return false; // Servicio no encontrado
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error en la comunicación con el microservicio de servicios: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar servicio: " + e.getMessage() + ". Verifica que el microservicio de servicios esté ejecutándose en http://localhost:8082"); 
        }
    }

    // Obtener información del servicio
    public Object obtenerServicio(Long idServicio) {
        try {
            return webclient.get()
                    .uri("/servicios/" + idServicio)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Servicio no encontrado con ID: " + idServicio);
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error en la comunicación con el microservicio de servicios: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener servicio: " + e.getMessage() + ". Verifica que el microservicio de servicios esté ejecutándose en http://localhost:8082"); 
        }
    }
} 