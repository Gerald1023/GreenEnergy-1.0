package com.example.ESTADOS.WebClient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UsuariosClient {

    //variable para la comunicación
    private final WebClient webclient;

    //metodo constructor de la clase
    public UsuariosClient(@Value("${usuarios-service.url}") String usuariosServiceUrl){
        this.webclient = WebClient.builder().baseUrl(usuariosServiceUrl).build();
    }
    
    //metodo para comunicarnos con el microservicio de usuarios
    //y buscar si un usuario existe mediante su id
    public Map<String, Object> getUsuarioById(Long id){
        return this.webclient.get()
        .uri("/usuarios/{id}",id).retrieve()
        .onStatus(status -> status.is4xxClientError(), response -> response.bodyToMono(String.class)
        .map(body -> new RuntimeException("Usuario no encontrado"))).bodyToMono(Map.class).block();
    }

} 