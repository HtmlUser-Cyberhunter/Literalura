package com.aluracursos.Litarelura.dto;

public record LibroDTO(Long id,
                       String titulo,
                       String autor,
                       String idioma,
                       String resumen,
                       String imagen,
                       Integer descargas) {
}
