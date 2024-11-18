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
        Editorial editorial = new Editorial();

        editorial.setId(1L);
        editorial.setNombre("Nombre editorial");
        editorial.setAlta(true);

        Libro libro = new Libro();

        libro.setId(1L);
        libro.setIsbn(9789871234560L);
        libro.setTitulo("Título libro");
        libro.setAnio(2000);
        libro.setEjemplares(20);
        libro.setEjemplaresPrestados(10);
        libro.setEjemplaresRestantes(10);
        libro.setAlta(true);
        libro.setEditorial(editorial);

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
        Editorial editorial = new Editorial();

        editorial.setId(1L);
        editorial.setNombre("Nombre editorial");
        editorial.setAlta(true);

        Libro libro = new Libro();

        libro.setId(1L);
        libro.setIsbn(9789871234560L);
        libro.setTitulo("Título libro");
        libro.setAnio(2000);
        libro.setEjemplares(20);
        libro.setEjemplaresPrestados(10);
        libro.setEjemplaresRestantes(10);
        libro.setAlta(true);
        libro.setEditorial(editorial);

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
        Editorial editorial = new Editorial();

        editorial.setId(2L);
        editorial.setNombre("Nuevo nombre editorial");
        editorial.setAlta(false);

        Libro libro = new Libro();

        libro.setId(2L);
        libro.setIsbn(9789871234561L);
        libro.setTitulo("Nuevo título libro");
        libro.setAnio(2001);
        libro.setEjemplares(40);
        libro.setEjemplaresPrestados(20);
        libro.setEjemplaresRestantes(20);
        libro.setAlta(false);
        libro.setEditorial(editorial);

        Set<Libro> libros = new HashSet<>();

        libros.add(libro);
        autor.setLibros(libros);

        assertEquals(libros, autor.getLibros());
    }
}