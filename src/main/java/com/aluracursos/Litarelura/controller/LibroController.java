package com.aluracursos.Litarelura.controller;

import com.aluracursos.Litarelura.dto.AutorDTO;
import com.aluracursos.Litarelura.dto.LibroDTO;
import com.aluracursos.Litarelura.model.DatosAutor;
import com.aluracursos.Litarelura.model.DatosLibros;
import com.aluracursos.Litarelura.repository.AutorRepository;
import com.aluracursos.Litarelura.repository.LibroRepository;
import com.aluracursos.Litarelura.service.AutorService;
import com.aluracursos.Litarelura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class LibroController {

    @Autowired
    private LibroService servicioLibro;

    @Autowired
    private AutorService servicioAutor;

    // Inyectamos los repositorios para usarlos directamente en las nuevas funcionalidades
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    // Endpoint para buscar y registrar un libro a partir de un título
    @PostMapping("/libros/buscar")
    public ResponseEntity<DatosLibros> buscarYRegistrarLibro(@RequestParam String titulo) {
        DatosLibros libro = servicioLibro.buscarYGuardar(titulo);
        if (libro != null) {
            return ResponseEntity.ok(libro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Listar libros registrados
    @GetMapping("/libros")
    public List<LibroDTO> listarLibros() {
        return servicioLibro.obtenerTodos();
    }

    // Listar autores registrados
    @GetMapping("/autores")
    public List<AutorDTO> listarAutores() {
        return servicioAutor.obtenerTodos();
    }

    // Listar autores vivos en un año determinado
    @GetMapping("/autores/vivos")
    public List<AutorDTO> autoresVivosEn(@RequestParam int año) {
        return servicioAutor.autoresVivosEn(año);
    }

    // Listar libros filtrados por idioma
    @GetMapping("/libros/idioma")
    public List<LibroDTO> librosPorIdioma(@RequestParam String idioma) {
        return servicioLibro.obtenerPorIdioma(idioma);
    }

    // Generar estadísticas sobre el campo "descargas" de los libros utilizando DoubleSummaryStatistics
    @GetMapping("/libros/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        DoubleSummaryStatistics stats = libroRepository.findAll().stream()
                .mapToDouble(libro -> libro.getDescargas() != null ? libro.getDescargas() : 0)
                .summaryStatistics();

        Map<String, Object> response = new HashMap<>();
        response.put("count", stats.getCount());
        response.put("min", stats.getMin());
        response.put("max", stats.getMax());
        response.put("sum", stats.getSum());
        response.put("average", stats.getAverage());

        return ResponseEntity.ok(response);
    }

    // Obtener el Top 10 de libros más descargados
    @GetMapping("/libros/top10")
    public List<LibroDTO> obtenerTop10Libros() {
        return libroRepository.findAll().stream()
                .sorted(Comparator.comparing(
                        libro -> libro.getDescargas() != null ? libro.getDescargas() : 0,
                        Comparator.reverseOrder()
                ))
                .limit(10)
                .map(libro -> new LibroDTO(
                        libro.getId(), // Long
                        libro.getTitulo(), // String
                        libro.getAutores() != null
                                ? libro.getAutores().stream()
                                .map(autor -> autor.getNombre())
                                .collect(Collectors.joining(", "))
                                : "Desconocido", // Convierte List<DatosAutor> a String
                        libro.getIdioma(), // String
                        libro.getResumen(), // String
                        "", // Valor por defecto para 'imagen' (puedes cambiarlo según tu necesidad)
                        libro.getDescargas() != null ? libro.getDescargas().intValue() : 0 // Convertir Long a Integer
                ))


                .collect(Collectors.toList());
    }

    // Buscar autores por nombre (búsqueda parcial ignorando mayúsculas/minúsculas)
    @GetMapping("/autores/buscar")
    public ResponseEntity<List<AutorDTO>> buscarAutorPorNombre(@RequestParam String nombre) {
        List<DatosAutor> autores = autorRepository.findByNombreContainingIgnoreCase(nombre);
        List<AutorDTO> resultado = autores.stream()
                .map(a -> new AutorDTO(a.getNombre(), a.getAnioNacimiento(), a.getAnioFallecimiento()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultado);
    }

    // Filtrar autores por año de nacimiento y/o fallecimiento
    @GetMapping("/autores/filter")
    public ResponseEntity<List<AutorDTO>> filtrarAutores(
            @RequestParam(required = false) Integer anioNacimiento,
            @RequestParam(required = false) Integer anioFallecimiento) {

        List<DatosAutor> todosAutores = autorRepository.findAll();
        Stream<DatosAutor> stream = todosAutores.stream();

        if (anioNacimiento != null) {
            stream = stream.filter(a ->
                    a.getAnioNacimiento() != null && a.getAnioNacimiento().equals(anioNacimiento));
        }
        if (anioFallecimiento != null) {
            stream = stream.filter(a ->
                    a.getAnioFallecimiento() != null && a.getAnioFallecimiento().equals(anioFallecimiento));
        }
        List<AutorDTO> resultado = stream
                .map(a -> new AutorDTO(a.getNombre(), a.getAnioNacimiento(), a.getAnioFallecimiento()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }
}