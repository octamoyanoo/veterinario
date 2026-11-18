package com.sira.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestión de datos en memoria para la Veterinaria.
 * Carga inicial de datos y métodos de acceso: listar, buscar y agregar.
 */
public class Datos {

    // Lista cargada en memoria
    private static List<Animal> lista = new ArrayList<>();

    // Inicialización de datos de prueba en memoria
    static {
        cargarDatosIniciales();
    }

    public static void cargarDatosIniciales() {
        lista.clear();
        lista.add(new Animal("A-001", "Luna", "Perro", "EN_REFUGIO", "hembra", "12/08/2026"));
        lista.add(new Animal("A-002", "Mora", "Gato", "EN_TRATAMIENTO", "hembra", "01/09/2026"));
        lista.add(new Animal("A-003", "Otto", "Perro", "RESERVADO", "macho", "15/07/2026"));
        lista.add(new Animal("A-004", "Pipa", "Gato", "ADOPTADO", "hembra", "10/06/2026"));
        lista.add(new Animal("A-005", "Roco", "Perro", "EN_REFUGIO", "macho", "20/08/2026"));
    }

    /**
     * Devuelve todos los elementos de la lista.
     */
    public static List<Animal> listar() {
        return new ArrayList<>(lista);
    }

    /**
     * Filtra la lista por nombre, código/legajo, especie o estado.
     */
    public static List<Animal> buscar(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return listar();
        }
        String filtro = criterio.trim().toLowerCase();
        List<Animal> resultado = new ArrayList<>();
        for (Animal a : lista) {
            boolean coincideNombre = a.getNombre() != null && a.getNombre().toLowerCase().contains(filtro);
            boolean coincideCodigo = a.getCodigo() != null && a.getCodigo().toLowerCase().contains(filtro);
            boolean coincideEspecie = a.getEspecie() != null && a.getEspecie().toLowerCase().contains(filtro);
            boolean coincideEstado = a.getEstado() != null && a.getEstado().toLowerCase().contains(filtro);

            if (coincideNombre || coincideCodigo || coincideEspecie || coincideEstado) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    /**
     * Agrega el parámetro elemento a la lista.
     */
    public static void agregar(Animal elemento) {
        if (elemento != null) {
            lista.add(elemento);
        }
    }

    // Métodos de compatibilidad de instancia (por si el docente o un test instancia 'new Datos()')
    public List<Animal> listarInstancia() {
        return listar();
    }

    public List<Animal> buscarInstancia(String criterio) {
        return buscar(criterio);
    }

    public void agregarInstancia(Animal elemento) {
        agregar(elemento);
    }
}
