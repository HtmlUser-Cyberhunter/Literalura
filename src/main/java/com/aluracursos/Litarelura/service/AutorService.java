package com.aluracursos.Litarelura.service;

import com.aluracursos.Litarelura.dto.AutorDTO;
import com.aluracursos.Litarelura.model.DatosAutor;
import com.aluracursos.Litarelura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepo;

    private List<AutorDTO> convierteDatos(List<DatosAutor> autores) {
        return autores.stream()
                .map(a -> new AutorDTO(a.getNombre(), a.getAnioNacimiento(), a.getAnioFallecimiento()))
                .collect(Collectors.toList());
    }

    public List<AutorDTO> listarAutores() {
        return convierteDatos(autorRepo.findAll());
    }

    public List<AutorDTO> autoresVivosEn(int anio) {
        List<DatosAutor> autores = autorRepo.findAll();
        List<DatosAutor> vivos = autores.stream()
                .filter(a -> a.getAnioNacimiento() != null    // Se descartan autores sin año de nacimiento
                        && a.getAnioNacimiento() <= anio
                        && (a.getAnioFallecimiento() == null || a.getAnioFallecimiento() > anio))
                .collect(Collectors.toList());
        return convierteDatos(vivos);
    }

    public List<AutorDTO> obtenerTodos() {
        return convierteDatos(autorRepo.findAll());
    }
}
