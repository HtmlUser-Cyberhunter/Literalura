package com.aluracursos.Litarelura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
