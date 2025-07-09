package com.example.USUARIOS.WebClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@Component
public class DireccionesClient {

    //variable para la comunicación
    private final WebClient webclient;

    //metodo constructor de la clase
   public DireccionesClient(@Value("${direcciones-service.url}") String baseUrl) {
        this.webclient = WebClient.builder().baseUrl(baseUrl).build();
    }

    // Solo verificar si existe la dirección
    public boolean existeDireccion(Long id_Direccion) {
        try {
            webclient.get()
                    .uri("/direcciones/" + id_Direccion)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true; // Si llega aquí, la dirección existe
        } catch (WebClientResponseException.NotFound e) {
            return false; // Dirección no encontrada
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error en la comunicación con el microservicio de direcciones: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar dirección: " + e.getMessage() + ". Verifica que el microservicio de direcciones esté ejecutándose en http://localhost:8086"); 
        }
    }

}