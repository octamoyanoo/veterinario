# SIRA - Sistema de Gestión Veterinaria y Refugio de Animales

Proyecto JavaFX desarrollado con interfaces FXML para editar y visualizar en **Scene Builder** y **Apache NetBeans**, orientado a la gestión básica de pacientes/animales de una veterinaria o refugio.

---

## 📌 Objetivos de la Entrega Implementados

1. **Carga en memoria desde `Datos.java`**:
   - La lista en memoria se gestiona centralizadamente en `src/main/java/com/sira/model/Datos.java`.
   - Incluye los métodos solicitados:
     - `listar()`: Devuelve todos los elementos de la lista en memoria.
     - `buscar(criterio)`: Filtra la lista por nombre, código/legajo, especie o estado.
     - `agregar(elemento)`: Agrega una nueva entidad `Animal` a la lista en memoria.

2. **Clase para la Entidad**:
   - `Animal.java` (`com.sira.model.Animal`) modela la entidad con sus atributos: `codigo`, `nombre`, `especie`, `estado`, `sexo` y `fechaIngreso`, con constructores, getters, setters y `toString()`.

3. **Botón Nuevo**:
   - Desde la ventana principal (`MainView.fxml`), el botón **Nuevo** abre el formulario modal (`AnimalFichaView.fxml`) en modo alta con los campos listos para ingresar.

4. **Validación de datos críticos (Aviso en color rojo)**:
   - Se seleccionaron como datos críticos obligatorios: **Nombre** y **Especie**.
   - Al presionar **Guardar**, si falta alguno de estos datos:
     - El formulario **no se cierra**.
     - Los campos vacíos se resaltan con borde rojo (`.text-field-error`).
     - Se muestran avisos en texto rojo indicando con precisión qué dato falta ingresar.
     - Al escribir en el campo, el aviso de error se retira automáticamente.
   - Solo al completar los datos críticos se crea el nuevo animal, se llama a `Datos.agregar(nuevo)` y se cierra la ventana, actualizando la tabla principal.

---

## 📁 Estructura del Proyecto

* **Vistas (FXML):** `src/main/resources/com/sira/view/`
  1. `MainView.fxml`: Ventana principal con tabla de animales, buscador por nombre/código, barra de botones (`Nuevo`, `Editar`, `Refrescar`) y tarjeta de resumen.
  2. `AnimalFichaView.fxml`: Formulario modal reutilizable tanto para alta (`Nuevo`) con validación en rojo, como para visualización y edición (`Ficha`).
  3. `AdopcionFormView.fxml`: Formulario modal secundario para registrar adopción.

* **Estilos:** `src/main/resources/com/sira/css/styles.css`
  - Incluye clases para diseño moderno y estilos específicos de validación en rojo (`.text-field-error`, `.label-error`, `.banner-error`).

* **Controladores y Modelos:**
  - `com.sira.controller.MainController`
  - `com.sira.controller.AnimalFichaController`
  - `com.sira.controller.AdopcionFormController`
  - `com.sira.model.Animal` (Entidad)
  - `com.sira.model.Datos` (Gestor de datos en memoria)
  - `com.sira.model.HistorialMedico`

---

## 🚀 Ejecución

Para iniciar la aplicación, ejecutar el script:
```bat
iniciar_app.bat
```
O directamente con Maven y Java 21+:
```bash
mvn javafx:run
```
