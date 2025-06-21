package com.aluracursos.Litarelura.repository;

import com.aluracursos.Litarelura.model.DatosLibros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<DatosLibros, Long> {

    Optional<DatosLibros> findByTituloContainsIgnoreCase(String nombreLibro);

    List<DatosLibros> findByIdiomaContainingIgnoreCase(String idioma);
}
