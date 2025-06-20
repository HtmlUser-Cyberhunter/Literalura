package com.aluracursos.Litarelura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultadoAPI {
    private List<DatosLibros> results;

    public List<DatosLibros> getResults() {
        return results;
    }

    public void setResults(List<DatosLibros> results) {
        this.results = results;
    }
}
