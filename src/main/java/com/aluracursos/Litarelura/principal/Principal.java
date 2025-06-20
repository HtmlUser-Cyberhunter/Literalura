package com.aluracursos.Litarelura.principal;

import com.aluracursos.Litarelura.dto.AutorDTO;
import com.aluracursos.Litarelura.dto.LibroDTO;
import com.aluracursos.Litarelura.model.DatosLibros;
import com.aluracursos.Litarelura.service.AutorService;
import com.aluracursos.Litarelura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private final Scanner scanner = new Scanner(System.in);

    private final LibroService libroService;
    private final AutorService autorService;

    @Autowired
    public Principal(LibroService libroService, AutorService autorService) {
        this.libroService = libroService;
        this.autorService = autorService;
    }

    public void muestraElMenu() {
        int opcion;
        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Intente nuevamente.");
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosEnAnio();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);
        System.exit(0);
    }

    private void mostrarMenu() {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║            📚 MENÚ PRINCIPAL 📚            ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║  1. 🔍 Buscar libro por título             ║");
        System.out.println("║  2. 📘 Listar libros registrados            ║");
        System.out.println("║  3. 👩‍🏫 Listar autores registrados           ║");
        System.out.println("║  4. 🧓 Autores vivos en un año              ║");
        System.out.println("║  5. 🌐 Libros por idioma (es, en, fr, pt)   ║");
        System.out.println("║  0. 🚪 Salir                                ║");
        System.out.println("╚═════════════════════════════════════════════╝");
    }

    private void buscarLibroPorTitulo() {
        System.out.print("Ingrese el título del libro a buscar: ");
        String nombreLibro = scanner.nextLine();

        DatosLibros libro = libroService.buscarYGuardar(nombreLibro);
        if (libro != null) {
            System.out.println("Libro encontrado: " + libro.getTitulo());
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private void listarLibrosRegistrados() {
        List<LibroDTO> libros = libroService.obtenerTodos();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(libro ->
                    System.out.printf("ID: %d | Título: %s | Idioma: %s | Autor: %s%n",
                            libro.id(),
                            libro.titulo(),
                            libro.idioma(),
                            libro.autor() != null ? libro.autor() : "Desconocido")
            );
        }
    }

    private void listarAutoresRegistrados() {
        List<AutorDTO> autores = autorService.obtenerTodos();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(autor ->
                    System.out.printf("Nombre: %s | Nacimiento: %d | Fallecimiento: %s%n",
                            autor.nombre(),
                            autor.aniooNacimiento() != null ? autor.aniooNacimiento() : 0,
                            autor.anioFallecimiento() != null ? autor.anioFallecimiento() : "Vivo"));
        }
    }

    private void listarAutoresVivosEnAnio() {
        System.out.print("Ingrese el año: ");
        int anio = Integer.parseInt(scanner.nextLine());
        List<AutorDTO> vivos = autorService.autoresVivosEn(anio);
        if (vivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            vivos.forEach(autor ->
                    System.out.printf("Nombre: %s | Nacimiento: %d | Fallecimiento: %s%n",
                            autor.nombre(),
                            autor.aniooNacimiento() != null ? autor.aniooNacimiento() : 0,
                            autor.anioFallecimiento() != null ? autor.anioFallecimiento() : "Vivo"));
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.print("Ingrese el código de idioma (es, en, fr, pt): ");
        String idioma = scanner.nextLine();
        List<LibroDTO> libros = libroService.obtenerPorIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
        } else {
            libros.forEach(libro ->
                    System.out.printf("Título: %s | Autor: %s%n",
                            libro.titulo(),
                            libro.autor() != null ? libro.autor() : "Desconocido")
            );
        }
    }
}
