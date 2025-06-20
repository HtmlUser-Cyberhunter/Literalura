package com.aluracursos.Litarelura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "datos_autor")
public class DatosAutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonAlias("name") private String nombre;

    @JsonAlias("birth_year") private Integer anioNacimiento;

    @JsonAlias("death_year") private Integer anioFallecimiento;

    @ManyToMany(mappedBy = "autores")
    private List<DatosLibros> libros = new ArrayList<>();

    public DatosAutor() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Integer getAnioFallecimiento() {
        return anioFallecimiento;
    }

    public void setAnioFallecimiento(Integer anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }
    public List<DatosLibros> getLibros() {
        return libros;
    }

    public void setLibros(List<DatosLibros> libros) {
        this.libros = libros;
    }

    public void agregarLibro(DatosLibros libro) {
        this.libros.add(libro);
    }
}
