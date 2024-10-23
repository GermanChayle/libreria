package com.example.libreria.model;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditorialTest {
    private Editorial editorial;

    @BeforeEach
    void setUp() {
        Libro libro = new Libro();

        libro.setId(1L);
        libro.setTitulo("Título libro");
        libro.setAlta(true);

        Set<Libro> libros = new HashSet<>();

        libros.add(libro);

        editorial = new Editorial();

        editorial.setId(1L);
        editorial.setNombre("Nombre editorial");
        editorial.setAlta(true);
        editorial.setLibros(libros);
    }

    @Test
    void getIdTest() {
        assertEquals(1L, editorial.getId());
    }

    @Test
    void getNombreTest() {
        assertEquals("Nombre editorial", editorial.getNombre());
    }

    @Test
    void getAltaTest() {
        assertTrue(editorial.getAlta());
    }

    @Test
    void getLibrosTest() {
        Libro libro = new Libro();

        libro.setId(1L);
        libro.setTitulo("Título libro");
        libro.setAlta(true);

        Set<Libro> libros = new HashSet<>();

        libros.add(libro);

        assertEquals(libros, editorial.getLibros());
    }

    @Test
    void setIdTest() {
        editorial.setId(2L);

        assertEquals(2L, editorial.getId());
    }

    @Test
    void setNombreTest() {
        editorial.setNombre("Nuevo nombre editorial");

        assertEquals("Nuevo nombre editorial", editorial.getNombre());
    }

    @Test
    void setAltaTest() {
        editorial.setAlta(false);

        assertFalse(editorial.getAlta());
    }

    @Test
    void setLibrosTest() {
        Libro newLibro = new Libro();

        newLibro.setId(2L);
        newLibro.setTitulo("Nuevo título libro");
        newLibro.setAlta(false);

        Set<Libro> newLibros = new HashSet<>();

        newLibros.add(newLibro);
        editorial.setLibros(newLibros);

        assertEquals(newLibros, editorial.getLibros());
    }
}