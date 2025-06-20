package com.aluracursos.Litarelura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aluracursos.Litarelura.model.DatosAutor;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<DatosAutor, Long> {
    List<DatosAutor> findByAnioFallecimientoIsNullOrAnioFallecimientoGreaterThan(int anio);

    Optional<DatosAutor> findByNombre(String nombre);

    List<DatosAutor> findByAnioNacimientoBeforeAndAnioFallecimientoAfter(int anioNacimiento, int anioFallecimiento);


}
