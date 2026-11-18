package com.sira;

import com.sira.model.Animal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    @Test
    public void testCrearAnimal() {
        Animal animal = new Animal("A-010", "Simba", "Felino", "EN_TRATAMIENTO", "macho", "18/11/2026");
        assertEquals("A-010", animal.getCodigo());
        assertEquals("Simba", animal.getNombre());
        assertEquals("Felino", animal.getEspecie());
        assertEquals("EN_TRATAMIENTO", animal.getEstado());
        assertEquals("macho", animal.getSexo());
        assertEquals("18/11/2026", animal.getFechaIngreso());
    }

    @Test
    public void testSettersAnimal() {
        Animal animal = new Animal();
        animal.setCodigo("A-020");
        animal.setNombre("Max");
        animal.setEspecie("Canino");
        animal.setEstado("ADOPTADO");
        animal.setSexo("macho");
        animal.setFechaIngreso("01/01/2026");

        assertEquals("A-020", animal.getCodigo());
        assertEquals("Max", animal.getNombre());
        assertEquals("Canino", animal.getEspecie());
        assertEquals("ADOPTADO", animal.getEstado());
        assertEquals("macho", animal.getSexo());
        assertEquals("01/01/2026", animal.getFechaIngreso());
    }
}
