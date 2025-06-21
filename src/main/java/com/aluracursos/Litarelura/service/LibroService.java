package com.aluracursos.Litarelura.service;

import com.aluracursos.Litarelura.dto.LibroDTO;
import com.aluracursos.Litarelura.model.DatosAutor;
import com.aluracursos.Litarelura.model.DatosLibros;
import com.aluracursos.Litarelura.repository.LibroRepository;
import com.aluracursos.Litarelura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

            // Configurar autores a partir de los datos de la API
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
                        libro.getAutores() != null ?
                                libro.getAutores().stream()
                                        .map(autor -> autor.getNombre())
                                        .collect(Collectors.joining(", "))
                                : "Desconocido",
                        libro.getIdioma(),
                        libro.getResumen(),
                        "",  // Valor por defecto para 'imagen'
                        libro.getDescargas() != null ? libro.getDescargas().intValue() : 0
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
                        libro.getAutores() != null ?
                                libro.getAutores().stream()
                                        .map(autor -> autor.getNombre())
                                        .collect(Collectors.joining(", "))
                                : "Desconocido",
                        libro.getIdioma(),
                        libro.getResumen(),
                        "",  // Valor por defecto para 'imagen'
                        libro.getDescargas() != null ? libro.getDescargas().intValue() : 0
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void actualizarLibrosDesdeAPI() {

        List<DatosLibros> librosDesdeAPI = conexionAPI.obtenerLibrosDeLaAPI();
        if (librosDesdeAPI != null) {
            for (DatosLibros libro : librosDesdeAPI) {
                // Si el libro (según su id) no existe, se guarda.
                Optional<DatosLibros> libroExistente = libroRepository.findById(libro.getId());
                if (libroExistente.isEmpty()) {
                    if (libro.getListaIdiomas() != null && !libro.getListaIdiomas().isEmpty()) {
                        String idiomasConcatenados = String.join(",", libro.getListaIdiomas());
                        libro.setIdioma(idiomasConcatenados);
                    } else {
                        libro.setIdioma("desconocido");
                    }
                    // Configurar autores usando los datos de la API
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
                    libroRepository.save(libro);
                }
            }
        }
        System.out.println("Actualización desde la API completada.");
    }
}