package com.example.RESENA.WebClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class UsuariosClient {

    //variable para la comunicación
    private final WebClient webclient;

    //metodo constructor de la clase
    public UsuariosClient(@Value("${usuarios-service.url}") String baseUrl) {
        this.webclient = WebClient.builder().baseUrl(baseUrl).build();
    }

    // Verificar si existe el usuario
    public boolean existeUsuario(Long idUsuario) {
        try {
            webclient.get()
                    .uri("/usuarios/" + idUsuario)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true; // Si llega aquí, el usuario existe
        } catch (WebClientResponseException.NotFound e) {
            return false; // Usuario no encontrado
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error en la comunicación con el microservicio de usuarios: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar usuario: " + e.getMessage() + ". Verifica que el microservicio de usuarios esté ejecutándose en http://localhost:8081"); 
        }
    }

    // Obtener información del usuario
    public Object obtenerUsuario(Long idUsuario) {
        try {
            return webclient.get()
                    .uri("/usuarios/" + idUsuario)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error en la comunicación con el microservicio de usuarios: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener usuario: " + e.getMessage() + ". Verifica que el microservicio de usuarios esté ejecutándose en http://localhost:8081"); 
        }
    }
} 