package com.example.libreria.model;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibroTest {
    private Libro libro;

    @BeforeEach
    void setUp() {
        Autor autor = new Autor();

        autor.setId(1L);
        autor.setNombre("Nombre autor");
        autor.setAlta(true);

        Set<Autor> autores = new HashSet<>();

        autores.add(autor);

        Editorial editorial = new Editorial();

        editorial.setId(1L);
        editorial.setNombre("Nombre editorial");
        editorial.setAlta(true);

        libro = new Libro();

        libro.setId(1L);
        libro.setIsbn(9789871234560L);
        libro.setTitulo("Título libro");
        libro.setAnio(2000);
        libro.setEjemplares(20);
        libro.setEjemplaresPrestados(10);
        libro.setEjemplaresRestantes(10);
        libro.setAlta(true);
        libro.setAutores(autores);
        libro.setEditorial(editorial);
    }

    @Test
    void getIdTest() {
        assertEquals(1L, libro.getId());
    }

    @Test
    void getIsbnTest() {
        assertEquals(9789871234560L, libro.getIsbn());
    }

    @Test
    void getTituloTest() {
        assertEquals("Título libro", libro.getTitulo());
    }

    @Test
    void getAnioTest() {
        assertEquals(2000, libro.getAnio());
    }

    @Test
    void getEjemplaresTest() {
        assertEquals(20, libro.getEjemplares());
    }

    @Test
    void getEjemplaresPrestadosTest() {
        assertEquals(10, libro.getEjemplaresPrestados());
    }

    @Test
    void getEjemplaresRestantesTest() {
        assertEquals(10, libro.getEjemplaresRestantes());
    }

    @Test
    void getAltaTest() {
        assertTrue(libro.getAlta());
    }

    @Test
    void getAutoresTest() {
        Autor autor = new Autor();

        autor.setId(1L);
        autor.setNombre("Nombre autor");
        autor.setAlta(true);

        Set<Autor> autores = new HashSet<>();

        autores.add(autor);

        assertEquals(autores, libro.getAutores());
    }

    @Test
    void getEditorialTest() {
        Editorial editorial = new Editorial();

        editorial.setId(1L);
        editorial.setNombre("Nombre editorial");
        editorial.setAlta(true);

        assertEquals(editorial, libro.getEditorial());
    }

    @Test
    void setIdTest() {
        libro.setId(2L);

        assertEquals(2L, libro.getId());
    }

    @Test
    void setIsbnTest() {
        libro.setIsbn(9789871234561L);

        assertEquals(9789871234561L, libro.getIsbn());
    }

    @Test
    void setTituloTest() {
        libro.setTitulo("Nuevo título libro");

        assertEquals("Nuevo título libro", libro.getTitulo());
    }

    @Test
    void setAnioTest() {
        libro.setAnio(2001);

        assertEquals(2001, libro.getAnio());
    }

    @Test
    void setEjemplaresTest() {
        libro.setEjemplares(40);

        assertEquals(40, libro.getEjemplares());
    }

    @Test
    void setEjemplaresPrestadosTest() {
        libro.setEjemplaresPrestados(20);

        assertEquals(20, libro.getEjemplaresPrestados());
    }

    @Test
    void setEjemplaresRestantesTest() {
        libro.setEjemplaresRestantes(20);

        assertEquals(20, libro.getEjemplaresRestantes());
    }

    @Test
    void setAltaTest() {
        libro.setAlta(false);

        assertFalse(libro.getAlta());
    }

    @Test
    void setAutoresTest() {
        Autor autor = new Autor();

        autor.setId(2L);
        autor.setNombre("Nuevo nombre autor");
        autor.setAlta(false);

        Set<Autor> autores = new HashSet<>();

        autores.add(autor);
        libro.setAutores(autores);

        assertEquals(autores, libro.getAutores());
    }

    @Test
    void setEditorialTest() {
        Editorial editorial = new Editorial();

        editorial.setId(2L);
        editorial.setNombre("Nuevo nombre editorial");
        editorial.setAlta(false);
        libro.setEditorial(editorial);

        assertEquals(editorial, libro.getEditorial());
    }
}