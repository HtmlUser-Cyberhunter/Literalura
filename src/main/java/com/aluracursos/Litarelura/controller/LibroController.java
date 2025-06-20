package com.aluracursos.Litarelura.controller;

import com.aluracursos.Litarelura.dto.AutorDTO;
import com.aluracursos.Litarelura.dto.LibroDTO;
import com.aluracursos.Litarelura.model.DatosAutor;
import com.aluracursos.Litarelura.model.DatosLibros;
import com.aluracursos.Litarelura.service.AutorService;
import com.aluracursos.Litarelura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LibroController {

    @Autowired
    private LibroService servicioLibro;
    @Autowired
    private AutorService servicioAutor;

    @PostMapping("/libros/buscar")
    public ResponseEntity<DatosLibros> buscarYRegistrarLibro(@RequestParam String titulo) {
        DatosLibros libro = servicioLibro.buscarYGuardar(titulo);
        if (libro != null) {
            return ResponseEntity.ok(libro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/libros")
    public List<LibroDTO> listarLibros() {
        return servicioLibro.obtenerTodos();
    }

    @GetMapping("/autores")
    public List<AutorDTO> listarAutores() {
        return servicioAutor.obtenerTodos();
    }

    @GetMapping("/autores/vivos")
    public List<AutorDTO> autoresVivosEn(@RequestParam int año) {
        return servicioAutor.autoresVivosEn(año);
    }

    @GetMapping("/libros/idioma")
    public List<LibroDTO> librosPorIdioma(@RequestParam String idioma) {
        return servicioLibro.obtenerPorIdioma(idioma);
    }
}
