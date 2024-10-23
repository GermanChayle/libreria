package com.example.libreria.model;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutorTest {
    private Autor autor;

    @BeforeEach
    void setUp() {
        Libro libro = new Libro();

        libro.setId(1L);
        libro.setTitulo("Título libro");
        libro.setAlta(true);

        Set<Libro> libros = new HashSet<>();

        libros.add(libro);

        autor = new Autor();

        autor.setId(1L);
        autor.setNombre("Nombre autor");
        autor.setAlta(true);
        autor.setLibros(libros);
    }

    @Test
    void getIdTest() {
        assertEquals(1L, autor.getId());
    }

    @Test
    void getNombreTest() {
        assertEquals("Nombre autor", autor.getNombre());
    }

    @Test
    void getAltaTest() {
        assertTrue(autor.getAlta());
    }

    @Test
    void getLibrosTest() {
        Libro libro = new Libro();

        libro.setId(1L);
        libro.setTitulo("Título libro");
        libro.setAlta(true);

        Set<Libro> libros = new HashSet<>();

        libros.add(libro);

        assertEquals(libros, autor.getLibros());
    }

    @Test
    void setIdTest() {
        autor.setId(2L);

        assertEquals(2L, autor.getId());
    }

    @Test
    void setNombreTest() {
        autor.setNombre("Nuevo nombre autor");

        assertEquals("Nuevo nombre autor", autor.getNombre());
    }

    @Test
    void setAltaTest() {
        autor.setAlta(false);

        assertFalse(autor.getAlta());
    }

    @Test
    void setLibrosTest() {
        Libro newLibro = new Libro();

        newLibro.setId(2L);
        newLibro.setTitulo("Nuevo título libro");
        newLibro.setAlta(false);

        Set<Libro> newLibros = new HashSet<>();

        newLibros.add(newLibro);
        autor.setLibros(newLibros);

        assertEquals(newLibros, autor.getLibros());
    }
}