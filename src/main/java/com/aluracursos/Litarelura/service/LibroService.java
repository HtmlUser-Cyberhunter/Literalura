package com.aluracursos.Litarelura.service;

import com.aluracursos.Litarelura.dto.LibroDTO;
import com.aluracursos.Litarelura.model.DatosAutor;
import com.aluracursos.Litarelura.model.DatosLibros;
import com.aluracursos.Litarelura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aluracursos.Litarelura.repository.AutorRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private ConexionAPI conexionAPI;

    @Autowired
    private AutorRepository repositorioAutor;

    @Transactional
    public DatosLibros buscarYGuardar(String titulo) {
        Optional<DatosLibros> libroExistente = libroRepository.findByTituloContainsIgnoreCase(titulo);
        if (libroExistente.isPresent()) {
            System.out.println("Este libro ya está registrado.");
            return libroExistente.get();
        }

        Optional<DatosLibros> libroDesdeApi = conexionAPI.buscarLibroPorTitulo(titulo);
        if (libroDesdeApi.isPresent()) {
            DatosLibros libro = libroDesdeApi.get();

            // Configurar idiomas
            if (libro.getListaIdiomas() != null && !libro.getListaIdiomas().isEmpty()) {
                String idiomasConcatenados = String.join(",", libro.getListaIdiomas());
                libro.setIdioma(idiomasConcatenados);
            } else {
                libro.setIdioma("desconocido");
            }

            if ((libro.getAutores() == null || libro.getAutores().isEmpty())
                    && libro.getAuthors() != null && !libro.getAuthors().isEmpty()) {
                libro.setAutores(libro.getAuthors());
            }

            if (libro.getAutores() != null && !libro.getAutores().isEmpty()) {
                List<DatosAutor> autoresActualizados = libro.getAutores().stream()
                        .map(autor -> {
                            Optional<DatosAutor> autorExistente = repositorioAutor.findByNombre(autor.getNombre());
                            if (autorExistente.isPresent()) {
                                return autorExistente.get();
                            } else {
                                return repositorioAutor.save(autor);
                            }
                        })
                        .collect(Collectors.toList());
                libro.setAutores(autoresActualizados);
                autoresActualizados.forEach(autor -> autor.getLibros().add(libro));
            }

            DatosLibros libroGuardado = libroRepository.save(libro);
            System.out.println("Libro y autores registrados con éxito.");
            return libroGuardado;
        } else {
            System.out.println("No se encontró el libro en la API.");
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<LibroDTO> obtenerTodos() {
        List<DatosLibros> libros = libroRepository.findAll();
        return libros.stream()
                .map(libro -> new LibroDTO(
                        libro.getId(),
                        libro.getTitulo(),
                        libro.getAutores() != null && !libro.getAutores().isEmpty()
                                ? libro.getAutores().stream()
                                .map(autor -> autor.getNombre())
                                .collect(Collectors.joining(", "))
                                : "Desconocido",
                        libro.getIdioma(),
                        "", // resumen (puedes agregar si lo tienes)
                        ""  // imagen (puedes agregar si lo tienes)
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LibroDTO> obtenerPorIdioma(String idioma) {
        List<DatosLibros> libros = libroRepository.findByIdiomaContainingIgnoreCase(idioma);
        return libros.stream()
                .map(libro -> new LibroDTO(
                        libro.getId(),
                        libro.getTitulo(),
                        libro.getAutores() != null && !libro.getAutores().isEmpty()
                                ? libro.getAutores().stream()
                                .map(DatosAutor::getNombre)
                                .collect(Collectors.joining(", "))
                                : "Desconocido",
                        libro.getIdioma(),
                        "",
                        ""
                ))
                .collect(Collectors.toList());
    }
}
