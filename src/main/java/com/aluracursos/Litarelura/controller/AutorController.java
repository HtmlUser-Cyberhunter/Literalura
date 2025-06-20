package com.aluracursos.Litarelura.controller;

import com.aluracursos.Litarelura.dto.AutorDTO;
import com.aluracursos.Litarelura.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService servicio;

    @GetMapping("/authors")
    public List<AutorDTO> listar() {
        return servicio.listarAutores();
    }

    @GetMapping("/authors/vivos/{año}")
    public List<AutorDTO> vivosEn(@PathVariable int año) {
        return servicio.autoresVivosEn(año);
    }
}
