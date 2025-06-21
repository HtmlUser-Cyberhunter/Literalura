package com.aluracursos.Litarelura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "proyecto_libros")
public class DatosLibros {

    @Id
    @JsonProperty("id") private Long id;

    @JsonProperty("title") private String titulo;

    @Transient
    @JsonProperty("authors") private List<DatosAutor> authors;


    @JsonProperty("languages")
    @Column(name = "lista_idiomas") private List<String> listaIdiomas;


    @JsonProperty("download_count")
    @Column(name = "descargas") private Long descargas;

    @Transient
    @JsonProperty("summaries") private List<String> summaries;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition = "text")
    private String resumen;

    private String idioma;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id", referencedColumnName = "id")
    )
    private List<DatosAutor> autores;

    public DatosLibros() {}

    public DatosLibros(Long id, String titulo, String idioma, List<DatosAutor> autores) {
        this.id = id;
        this.titulo = titulo;
        this.idioma = idioma;
        this.autores = autores;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<String> getListaIdiomas() {
        return listaIdiomas;
    }

    public void setListaIdiomas(List<String> listaIdiomas) {
        this.listaIdiomas = listaIdiomas;
        if (listaIdiomas != null && !listaIdiomas.isEmpty()) {
            this.idioma = String.join(",", listaIdiomas);
        } else {
            this.idioma = "desconocido";
        }
    }

    public List<String> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<String> summaries) {
        this.summaries = summaries;
        if (summaries != null && !summaries.isEmpty()) {
            this.resumen = summaries.get(0); // Tomamos el primero como resumen principal
        }
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public List<DatosAutor> getAutores() {
        return autores;
    }

    public void setAutores(List<DatosAutor> autores) {
        this.autores = autores;
    }

    public List<DatosAutor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<DatosAutor> authors) {
        this.authors = authors;
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }
}