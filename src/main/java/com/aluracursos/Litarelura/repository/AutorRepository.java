package com.aluracursos.Litarelura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aluracursos.Litarelura.model.DatosAutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<DatosAutor, Long> {
    List<DatosAutor> findByAnioFallecimientoIsNullOrAnioFallecimientoGreaterThan(int anio);

    Optional<DatosAutor> findByNombre(String nombre);

    List<DatosAutor> findByNombreContainingIgnoreCase(String nombre);

    List<DatosAutor> findByAnioNacimientoBeforeAndAnioFallecimientoAfter(int anioNacimiento, int anioFallecimiento);


}
