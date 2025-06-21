package com.aluracursos.Litarelura.service;

import com.aluracursos.Litarelura.model.DatosLibros;
import com.aluracursos.Litarelura.model.ResultadoAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ConexionAPI {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al obtener datos de la API", e);
        }
    }

    public Optional<DatosLibros> buscarLibroPorTitulo(String titulo) {
        try {
            String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "+");
            String json = obtenerDatos(url);

            ResultadoAPI resultado = objectMapper.readValue(json, ResultadoAPI.class);
            if (resultado.getResults() != null && !resultado.getResults().isEmpty()) {
                return Optional.of(resultado.getResults().get(0));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<DatosLibros> obtenerLibrosDeLaAPI() {
        String url = "https://gutendex.com/books/";
        String json = obtenerDatos(url);
        try {
            ResultadoAPI resultado = objectMapper.readValue(json, ResultadoAPI.class);
            if (resultado.getResults() != null && !resultado.getResults().isEmpty()) {
                return resultado.getResults();
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir la respuesta de la API", e);
        }
    }
}