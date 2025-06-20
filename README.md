# Literalura Versión 1
Este proyecto es la parte backend de la aplicación Litarelura, desarrollada con Spring Boot y configurada para gestionar y consultar información sobre libros y autores. Se encarga de conectar con un API externa para obtener datos de libros y luego persistirlos en una base de datos PostgreSQL, además de gestionar registros de autores y establecer relaciones entre ellos (muchos a muchos).
# Características principales
- Gestión de libros y autores:
Utiliza JPA (Hibernate) para mapear las entidades DatosLibros y DatosAutor, permitiendo operaciones CRUD y consultas específicas. La entidad de libros maneja campos LOB (para el resumen) a través de la anotación @Lob y se mapea como texto en PostgreSQL.
- Operaciones disponibles:
- Buscar libro por título: Consulta y registra libros obtenidos de un API externa.
- Listar libros registrados: Devuelve el listado de libros almacenados en la base de datos.
- Listar autores registrados: Retorna todos los autores disponibles.
- Filtrar autores vivos en un año dado: Permite obtener los autores que estuvieron vivos en un año específico.
- Filtrar libros por idioma: Consulta los libros registrados que corresponden a un código de idioma (ej. es, en, fr, pt).
- Relaciones y manejo de datos:
Se implementa una relación muchos a muchos entre libros y autores mediante una tabla intermedia (libro_autor), asegurando la integridad referencial de los datos.
- Configuración de CORS:
Se ha configurado una política de CORS para permitir peticiones desde orígenes como http://localhost:5500, facilitando la integración con el front end (realizado en HTML, CSS y JavaScript).
- Transaccionalidad y manejo de errores:
La aplicación está configurada para manejar de forma correcta las transacciones y evitar problemas al acceder a datos LOB, entre otras configuraciones importantes en application.properties.

![Static Badge](https://img.shields.io/badge/STATUS-Finalizado-green)        ![GitHub User's stars](https://img.shields.io/github/stars/HtmlUser-Cyberhunter)
