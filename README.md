# SIRA - Sistema de Gestión Veterinaria y Refugio de Animales

Proyecto JavaFX creado con interfaces FXML listas para editar y visualizar en **Scene Builder** y **Apache NetBeans**.

---

## 📁 Estructura de las Ventanas (FXML)

Las vistas se encuentran en: `src/main/resources/com/sira/view/`

1. **`MainView.fxml`** (Ventana Principal)
   - Barra de título con estilo y barra de menús: *Archivo, Animales, Personas, Adopciones, Veterinaria, Donaciones, Alertas, Asistente*.
   - Botones de acción: `Nuevo`, `Editar`, `Refrescar`.
   - Barra de búsqueda: `Buscar animal...`.
   - Tabla de Animales con columnas: `Nombre`, `Especie`, `Estado`.
   - Panel Card "Resumen del seleccionado" con datos de *Luna* y botones: `Abrir ficha` y `Nueva adopción`.
   - Barra inferior de estado: `BOTTOM API: conectado | operador`.

2. **`AnimalFichaView.fxml`** (Ficha animal modal)
   - Ventana modal hija de la Principal (`owner = principal`).
   - Campos de formulario: `Nombre` (Luna), `Especie` (Perro), `Estado` (EN_REFUGIO).
   - Tabla "Historial clínico (lista enlazada)" con columnas `Fecha` y `Tipo` (datos de ejemplo: *02/09 Vacuna*, *18/09 Control*).
   - Botones de acción: `Guardar` (verde), `Deshacer` (amarillo/mostaza) y `Cerrar` (gris oscuro).

3. **`AdopcionFormView.fxml`** (Nueva adopción modal)
   - Modal hija de la ficha (`HIJA DE LA FICHA`).
   - Identificación del animal cargado (*Luna*).
   - Campo de formulario: `Adoptante` (*Ana Pérez*).
   - Botón de confirmación: `Confirmar` (verde).

---

## 🛠️ Cómo abrir en Scene Builder

1. Iniciar **Scene Builder** (por ejemplo ejecutando `iniciar_scenebuilder.bat`).
2. Ir a **File -> Open...** (o `Ctrl + O`).
3. Navegar a `veterinario/src/main/resources/com/sira/view/` y seleccionar cualquiera de los archivos `.fxml`:
   - `MainView.fxml`
   - `AnimalFichaView.fxml`
   - `AdopcionFormView.fxml`
