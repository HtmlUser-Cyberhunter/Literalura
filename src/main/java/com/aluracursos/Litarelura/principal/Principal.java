package com.aluracursos.Litarelura.Principal;

import com.aluracursos.Litarelura.dto.AutorDTO;
import com.aluracursos.Litarelura.dto.LibroDTO;
import com.aluracursos.Litarelura.model.DatosLibros;
import com.aluracursos.Litarelura.service.AutorService;
import com.aluracursos.Litarelura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Comparator;

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
                case 6 -> generarEstadisticas();
                case 7 -> top10Libros();
                case 8 -> buscarAutorPorNombre();
                case 9 -> filtrarAutores();
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
        System.out.println("║  6. 📊 Estadísticas de descargas            ║");
        System.out.println("║  7. 📈 Top 10 libros más descargados        ║");
        System.out.println("║  8. 🔎 Buscar autor por nombre              ║");
        System.out.println("║  9. 📝 Filtrar autores por año              ║");
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
                    System.out.printf("Nombre: %s | Nacimiento: %s | Fallecimiento: %s%n",
                            autor.nombre(),
                            autor.anioNacimiento() != null ? autor.anioNacimiento() : "Desconocido",
                            autor.anioFallecimiento() != null ? autor.anioFallecimiento() : "Vivo")
            );
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
                    System.out.printf("Nombre: %s | Nacimiento: %s | Fallecimiento: %s%n",
                            autor.nombre(),
                            autor.anioNacimiento() != null ? autor.anioNacimiento() : "Desconocido",
                            autor.anioFallecimiento() != null ? autor.anioFallecimiento() : "Vivo")
            );
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

    // Función 6. Generar estadísticas de descargas con DoubleSummaryStatistics.
    private void generarEstadisticas() {
        List<LibroDTO> libros = libroService.obtenerTodos();
        DoubleSummaryStatistics stats = libros.stream()
                .mapToDouble(libro -> libro.descargas() != null ? libro.descargas() : 0)
                .summaryStatistics();
        System.out.println("Estadísticas de descargas:");
        System.out.println("Cantidad: " + stats.getCount());
        System.out.println("Mínimo: " + stats.getMin());
        System.out.println("Máximo: " + stats.getMax());
        System.out.println("Suma: " + stats.getSum());
        System.out.println("Promedio: " + stats.getAverage());
    }

    // Función 7. Top 10 libros más descargados.
    private void top10Libros() {
        libroService.actualizarLibrosDesdeAPI();

        List<LibroDTO> libros = libroService.obtenerTodos().stream()
                .sorted(Comparator.comparing(
                        libro -> libro.descargas() != null ? libro.descargas() : 0,
                        Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toList());

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            System.out.println("Top 10 libros más descargados:");
            libros.forEach(libro ->
                    System.out.printf("Título: %s | Descargas: %d%n",
                            libro.titulo(),
                            libro.descargas() != null ? libro.descargas() : 0)
            );
        }
    }

    // Función 8. Buscar autor por nombre.
    private void buscarAutorPorNombre() {
        System.out.print("Ingrese el nombre del autor a buscar: ");
        String nombre = scanner.nextLine();
        List<AutorDTO> autores = autorService.obtenerTodos().stream()
                .filter(autor -> autor.nombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores que coincidan.");
        } else {
            autores.forEach(autor ->
                    System.out.printf("Nombre: %s | Nacimiento: %s | Fallecimiento: %s%n",
                            autor.nombre(),
                            autor.anioNacimiento() != null ? autor.anioNacimiento() : "Desconocido",
                            autor.anioFallecimiento() != null ? autor.anioFallecimiento() : "Vivo")
            );
        }
    }

    // Función 9. Filtrar autores por año de nacimiento y/o fallecimiento.
    private void filtrarAutores() {
        // Solicitar el rango de fechas
        System.out.print("Ingrese el año de inicio: ");
        String inputInicio = scanner.nextLine();
        Integer anioInicio = inputInicio.isEmpty() ? null : Integer.parseInt(inputInicio);

        System.out.print("Ingrese el año final: ");
        String inputFin = scanner.nextLine();
        Integer anioFin = inputFin.isEmpty() ? null : Integer.parseInt(inputFin);

        if (anioInicio == null || anioFin == null) {
            System.out.println("Debe ingresar ambos límites del rango");
            return;
        }
        if (anioInicio > anioFin) {
            System.out.println("El año de inicio debe ser menor o igual que el año final.");
            return;
        }

            List<AutorDTO> autores = this.autorService.obtenerTodos().stream()
                    .filter(autor -> {
                        Integer nacimiento = autor.anioNacimiento();
                        if (nacimiento == null) return false;
                        // Si no tiene año de fallecimiento, se considera que sigue vivo.
                        Integer fallecimiento = autor.anioFallecimiento() != null ? autor.anioFallecimiento() : Integer.MAX_VALUE;

                        return nacimiento <= anioFin && fallecimiento >= anioInicio;
                    })
                    .collect(Collectors.toList());
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores que estuvieran vivos entre esos años");
        } else {
            System.out.println("Autores que estuvieron vivos entre " + anioInicio + " y " + anioFin + ":");
            autores.forEach(autor ->
                    System.out.printf("Nombre: %s | Nacimiento: %s | Fallecimiento: %s%n",
                            autor.nombre(),
                            autor.anioNacimiento() != null ? autor.anioNacimiento() : "Desconocido",
                            autor.anioFallecimiento() != null ? autor.anioFallecimiento() : "Vivo")
            );
        }
    }
}