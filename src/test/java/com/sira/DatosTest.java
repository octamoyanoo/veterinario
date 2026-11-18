package com.sira;

import com.sira.model.Animal;
import com.sira.model.Datos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DatosTest {

    @BeforeEach
    public void setUp() {
        Datos.cargarDatosIniciales();
    }

    @Test
    public void testListar() {
        List<Animal> lista = Datos.listar();
        assertNotNull(lista, "La lista no debe ser nula");
        assertEquals(5, lista.size(), "Debe contener inicialmente 5 animales");
    }

    @Test
    public void testBuscarPorNombre() {
        List<Animal> resultado = Datos.buscar("Luna");
        assertEquals(1, resultado.size());
        assertEquals("Luna", resultado.get(0).getNombre());
    }

    @Test
    public void testBuscarPorCodigo() {
        List<Animal> resultado = Datos.buscar("A-003");
        assertEquals(1, resultado.size());
        assertEquals("Otto", resultado.get(0).getNombre());
    }

    @Test
    public void testBuscarPorEspecie() {
        List<Animal> resultado = Datos.buscar("Gato");
        assertEquals(2, resultado.size());
    }

    @Test
    public void testAgregar() {
        int totalInicial = Datos.listar().size();
        Animal nuevo = new Animal("A-006", "Thor", "Perro", "EN_REFUGIO", "macho", "18/11/2026");
        Datos.agregar(nuevo);

        List<Animal> listaActualizada = Datos.listar();
        assertEquals(totalInicial + 1, listaActualizada.size());

        List<Animal> busqueda = Datos.buscar("Thor");
        assertEquals(1, busqueda.size());
        assertEquals("Thor", busqueda.get(0).getNombre());
    }
}
