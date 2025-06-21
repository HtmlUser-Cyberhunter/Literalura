# Literalura Versión 2

Litarelura es una aplicación de línea de comandos desarrollada en Java que permite gestionar y consultar información literaria obtenida en tiempo real desde una API externa (por ejemplo, Gutendex). La aplicación integra datos de libros y autores, almacenándolos en una base de datos PostgreSQL para realizar diversas consultas y análisis.
Funcionalidades
La aplicación cuenta con las siguientes funciones:
- 🔍 Buscar libro por título
Permite buscar un libro mediante su título. Si el libro existe en la API, se guarda o actualiza la información en la base de datos.
- 📘 Listar libros registrados
Muestra todos los libros registrados en la base de datos, incluyendo detalles como título, resumen y otros atributos relevantes.
- 👩‍🏫 Listar autores registrados
Genera un listado de todos los autores que están almacenados en la base de datos, mostrando su información básica.
- 🧓 Autores vivos en un año
Filtra y muestra aquellos autores que estaban vivos en un año específico, utilizando sus años de nacimiento y fallecimiento (si aplica).
- 🌐 Libros por idioma (es, en, fr, pt)
Permite filtrar los libros según el idioma en el que están escritos.
- 📊 Estadísticas de descargas
Proporciona información y análisis sobre el número de descargas de los libros registrados.
- 📈 Top 10 libros más descargados
Muestra un ranking de los 10 libros con mayor número de descargas, facilitando el conocimiento de las obras más populares.
- 🔎 Buscar autor por nombre
Permite buscar y filtrar autores por su nombre, haciendo la búsqueda insensible a mayúsculas/minúsculas para mayor precisión.
- 📝 Filtrar autores por año
Permite seleccionar un rango de años y filtrar aquellos autores que estuvieron vivos durante ese período.
## Tecnologías utilizadas
- Java 11+ y Spring Boot
- JPA/Hibernate para la persistencia de datos
- PostgreSQL para el almacenamiento
- HTTP Client (java.net.http) para comunicarse con la API
- Jackson para el mapeo de JSON a objetos Java
## Cómo empezar
- Clona el repositorio:
git clone https://github.com/HtmlUser-Cyberhunter/Literalura/edit/Literalura_V2.git
cd Litarelura
- Configura la conexión a la base de datos:
Edita el archivo src/main/resources/application.properties para definir la URL, usuario y contraseña de PostgreSQL.
- Resetea la base de datos (opcional):
Puedes resetear la base de datos ejecutando los siguientes comandos en tu consola de PostgreSQL:
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
- Ejecuta la aplicación:
- Con Maven:
./mvnw spring-boot:run
- O con Gradle:
./gradlew bootRun
Notas adicionales- La aplicación realiza la sincronización automatizada de datos desde una API externa.
- Se gestionan las relaciones entre libros y autores automáticamente mediante JPA/Hibernate.
- La información sobre descargas se corrige mapeándola del campo download_count que provee la API al campo descargas en la entidad.
Este proyecto te permite explorar y analizar datos literarios actualizados, integrando funcionalidades de búsqueda, filtrado y estadísticas que facilitan la gestión de una base de datos de libros y autores. ¡Disfruta explorando Litarelura!



![Static Badge](https://img.shields.io/badge/STATUS-Finalizado-green)        ![GitHub User's stars](https://img.shields.io/github/stars/HtmlUser-Cyberhunter)
