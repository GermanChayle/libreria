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
        Autor autor = new Autor();

        autor.setId(1L);
        autor.setNombre("Nombre autor");
        autor.setAlta(true);

        Set<Autor> autores = new HashSet<>();

        autores.add(autor);

        Libro libro = new Libro();

        libro.setId(1L);
        libro.setIsbn(9789871234560L);
        libro.setTitulo("Título libro");
        libro.setAnio(2000);
        libro.setEjemplares(20);
        libro.setEjemplaresPrestados(10);
        libro.setEjemplaresRestantes(10);
        libro.setAlta(true);
        libro.setAutores(autores);

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
        Autor autor = new Autor();

        autor.setId(1L);
        autor.setNombre("Nombre autor");
        autor.setAlta(true);

        Set<Autor> autores = new HashSet<>();

        autores.add(autor);

        Libro libro = new Libro();

        libro.setId(1L);
        libro.setIsbn(9789871234560L);
        libro.setTitulo("Título libro");
        libro.setAnio(2000);
        libro.setEjemplares(20);
        libro.setEjemplaresPrestados(10);
        libro.setEjemplaresRestantes(10);
        libro.setAlta(true);
        libro.setAutores(autores);

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
        Autor autor = new Autor();

        autor.setId(2L);
        autor.setNombre("Nuevo nombre autor");
        autor.setAlta(false);

        Set<Autor> autores = new HashSet<>();

        autores.add(autor);

        Libro libro = new Libro();

        libro.setId(2L);
        libro.setIsbn(9789871234561L);
        libro.setTitulo("Nuevo título libro");
        libro.setAnio(2001);
        libro.setEjemplares(40);
        libro.setEjemplaresPrestados(20);
        libro.setEjemplaresRestantes(20);
        libro.setAlta(false);
        libro.setAutores(autores);

        Set<Libro> libros = new HashSet<>();

        libros.add(libro);
        editorial.setLibros(libros);

        assertEquals(libros, editorial.getLibros());
    }
}