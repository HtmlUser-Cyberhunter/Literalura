// script.js

// URL base para los endpoints del back end (ajusta este valor según tu configuración)
const API_BASE_URL = 'http://localhost:8080/api';

// Asignar eventos a los botones del menú
document.getElementById('btnBuscarLibro').addEventListener('click', showBuscarLibroForm);
document.getElementById('btnListarLibros').addEventListener('click', listarLibros);
document.getElementById('btnListarAutores').addEventListener('click', listarAutores);
document.getElementById('btnAutoresVivos').addEventListener('click', showAutoresVivosForm);
document.getElementById('btnLibrosPorIdioma').addEventListener('click', showLibrosPorIdiomaForm);
document.getElementById('btnSalir').addEventListener('click', salir);

// Función para limpiar formularios y resultados
function clearContainers() {
  document.getElementById('formContainer').innerHTML = '';
  document.getElementById('resultContainer').innerHTML = '';
}

// Mostrar formulario para buscar un libro por título
function showBuscarLibroForm() {
  clearContainers();
  const formContainer = document.getElementById('formContainer');
  formContainer.classList.remove('hidden');

  formContainer.innerHTML = `
    <form id="buscarLibroForm">
      <input type="text" id="tituloLibro" placeholder="Ingrese el título del libro" required>
      <button type="submit">Buscar</button>
    </form>
  `;

  document.getElementById('buscarLibroForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const titulo = document.getElementById('tituloLibro').value;
    buscarLibroPorTitulo(titulo);
  });
}

// Llamar a la API para buscar libro por título
function buscarLibroPorTitulo(titulo) {
  fetch(`${API_BASE_URL}/libros/search?title=${encodeURIComponent(titulo)}`)
    .then(response => response.json())
    .then(data => displayResults(data))
    .catch(error => {
      console.error('Error al buscar libro:', error);
      displayError('Error al buscar libro');
    });
}

// Listar todos los libros registrados
function listarLibros() {
  clearContainers();
  fetch(`${API_BASE_URL}/libros`)
    .then(response => response.json())
    .then(data => displayResults(data))
    .catch(error => {
      console.error('Error al listar libros:', error);
      displayError('Error al listar libros');
    });
}

// Listar todos los autores registrados
function listarAutores() {
  clearContainers();
  fetch(`${API_BASE_URL}/autores`)
    .then(response => response.json())
    .then(data => displayResults(data))
    .catch(error => {
      console.error('Error al listar autores:', error);
      displayError('Error al listar autores');
    });
}

// Mostrar formulario para listar autores vivos en un año
function showAutoresVivosForm() {
  clearContainers();
  const formContainer = document.getElementById('formContainer');
  formContainer.classList.remove('hidden');

  formContainer.innerHTML = `
    <form id="autoresVivosForm">
      <input type="number" id="anio" placeholder="Ingrese el año" required>
      <button type="submit">Buscar autores vivos</button>
    </form>
  `;

  document.getElementById('autoresVivosForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const anio = document.getElementById('anio').value;
    autoresVivosEnAnio(anio);
  });
}

// Llamar a la API para obtener autores vivos en un año
function autoresVivosEnAnio(anio) {
  fetch(`${API_BASE_URL}/autores/vivos/alive?year=${anio}`)
    .then(response => response.json())
    .then(data => displayResults(data))
    .catch(error => {
      console.error('Error al listar autores vivos:', error);
      displayError('Error al listar autores vivos');
    });
}

// Mostrar formulario para buscar libros por idioma
function showLibrosPorIdiomaForm() {
  clearContainers();
  const formContainer = document.getElementById('formContainer');
  formContainer.classList.remove('hidden');

  formContainer.innerHTML = `
    <form id="librosPorIdiomaForm">
      <input type="text" id="idioma" placeholder="Ingrese el código de idioma (es, en, fr, pt)" required>
      <button type="submit">Buscar libros por idioma</button>
    </form>
  `;

  document.getElementById('librosPorIdiomaForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const idioma = document.getElementById('idioma').value;
    librosPorIdioma(idioma);
  });
}

// Llamar a la API para listar libros por idioma
function librosPorIdioma(idioma) {
  fetch(`${API_BASE_URL}/libros/idioma?code=${encodeURIComponent(idioma)}`)
    .then(response => response.json())
    .then(data => displayResults(data))
    .catch(error => {
      console.error('Error al listar libros por idioma:', error);
      displayError('Error al listar libros por idioma');
    });
}

// Función genérica para mostrar los resultados en la página
function displayResults(data) {
  const resultContainer = document.getElementById('resultContainer');
  resultContainer.innerHTML = '';
  if (Array.isArray(data) && data.length > 0) {
    data.forEach(item => {
      const div = document.createElement('div');
      div.className = 'result-item';
      div.innerHTML = `<pre>${JSON.stringify(item, null, 2)}</pre>`;
      resultContainer.appendChild(div);
    });
  } else {
    resultContainer.innerHTML = '<p>No se encontraron resultados.</p>';
  }
}

// Función para mostrar mensajes de error
function displayError(message) {
  const resultContainer = document.getElementById('resultContainer');
  resultContainer.innerHTML = `<p class="error">${message}</p>`;
}

// Función para el botón "Salir"
function salir() {
  clearContainers();
  document.getElementById('resultContainer').innerHTML = "<p>Gracias por usar Litarelura. ¡Hasta luego!</p>";
}