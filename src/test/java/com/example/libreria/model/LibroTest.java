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
        libro.setIsbn(1234567890123L);
        libro.setTitulo("Título libro");
        libro.setAnio(2000);
        libro.setEjemplares(10);
        libro.setEjemplaresPrestados(5);
        libro.setEjemplaresRestantes(5);
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
        assertEquals(1234567890123L, libro.getIsbn());
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
        assertEquals(10, libro.getEjemplares());
    }

    @Test
    void getEjemplaresPrestadosTest() {
        assertEquals(5, libro.getEjemplaresPrestados());
    }

    @Test
    void getEjemplaresRestantesTest() {
        assertEquals(5, libro.getEjemplaresRestantes());
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
        libro.setIsbn(3210987654321L);

        assertEquals(3210987654321L, libro.getIsbn());
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
        libro.setEjemplares(20);

        assertEquals(20, libro.getEjemplares());
    }

    @Test
    void setEjemplaresPrestadosTest() {
        libro.setEjemplaresPrestados(10);

        assertEquals(10, libro.getEjemplaresPrestados());
    }

    @Test
    void setEjemplaresRestantesTest() {
        libro.setEjemplaresRestantes(10);

        assertEquals(10, libro.getEjemplaresRestantes());
    }

    @Test
    void setAltaTest() {
        libro.setAlta(false);

        assertFalse(libro.getAlta());
    }

    @Test
    void setAutoresTest() {
        Autor newAutor = new Autor();

        newAutor.setId(2L);
        newAutor.setNombre("Nuevo nombre autor");
        newAutor.setAlta(false);

        Set<Autor> newAutores = new HashSet<>();

        newAutores.add(newAutor);
        libro.setAutores(newAutores);

        assertEquals(newAutores, libro.getAutores());
    }

    @Test
    void setEditorialTest() {
        Editorial newEditorial = new Editorial();

        newEditorial.setId(2L);
        newEditorial.setNombre("Nuevo nombre editorial");
        newEditorial.setAlta(false);
        libro.setEditorial(newEditorial);

        assertEquals(newEditorial, libro.getEditorial());
    }
}