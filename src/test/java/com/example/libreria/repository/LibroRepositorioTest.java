package com.example.libreria.repository;

import com.example.libreria.model.Autor;
import com.example.libreria.model.Editorial;
import com.example.libreria.model.Libro;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest

class LibroRepositorioTest {
    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    @BeforeEach
    void setUp() {
        Autor autor1 = new Autor();
        Autor autor2 = new Autor();

        autor1.setNombre("Nombre autor 1");
        autor1.setAlta(true);
        autor2.setNombre("Nombre autor 2");
        autor2.setAlta(false);

        Set<Autor> autores1 = new HashSet<>();
        Set<Autor> autores2 = new HashSet<>();

        autores1.add(autor1);
        autorRepositorio.save(autor1);
        autores2.add(autor2);
        autorRepositorio.save(autor2);

        Editorial editorial1 = new Editorial();
        Editorial editorial2 = new Editorial();

        editorial1.setNombre("Nombre editorial 1");
        editorial1.setAlta(true);
        editorialRepositorio.save(editorial1);
        editorial2.setNombre("Nombre editorial 2");
        editorial2.setAlta(false);
        editorialRepositorio.save(editorial2);

        Libro libro1 = new Libro();
        Libro libro2 = new Libro();
        Libro libro3 = new Libro();

        libro1.setIsbn(1234567890123L);
        libro1.setTitulo("Título libro 1");
        libro1.setAnio(2000);
        libro1.setEjemplares(10);
        libro1.setEjemplaresPrestados(5);
        libro1.setEjemplaresRestantes(5);
        libro1.setAlta(true);
        libro1.setAutores(autores1);
        libro1.setEditorial(editorial1);
        libroRepositorio.save(libro1);
        libro2.setIsbn(2345678901234L);
        libro2.setTitulo("Título libro 2");
        libro2.setAnio(2000);
        libro2.setEjemplares(20);
        libro2.setEjemplaresPrestados(10);
        libro2.setEjemplaresRestantes(10);
        libro2.setAlta(true);
        libro2.setAutores(autores1);
        libro2.setEditorial(editorial1);
        libroRepositorio.save(libro2);
        libro3.setIsbn(3456789012345L);
        libro3.setTitulo("Título libro 3");
        libro3.setAnio(2001);
        libro3.setEjemplares(10);
        libro3.setEjemplaresPrestados(5);
        libro3.setEjemplaresRestantes(5);
        libro3.setAlta(false);
        libro3.setAutores(autores2);
        libro3.setEditorial(editorial2);
        libroRepositorio.save(libro3);
    }

    @Test
    void findAllByAltaTrueTest() {
        List<Libro> listaLibros = libroRepositorio.findAllByAltaTrue();

        assertNotNull(listaLibros);
        assertFalse(listaLibros.isEmpty());
        assertTrue(listaLibros.stream().allMatch(Libro::getAlta));
    }

    @Test
    void findAllByAltaFalseTest() {
        List<Libro> listaLibros = libroRepositorio.findAllByAltaFalse();

        assertNotNull(listaLibros);
        assertFalse(listaLibros.isEmpty());
        assertTrue(listaLibros.stream().noneMatch(Libro::getAlta));
    }

    @Test
    void findAllByAnioAndAltaTrueTest() {
        int anio = 2000;
        List<Libro> listaLibros = libroRepositorio.findAllByAnioAndAltaTrue(anio);

        assertNotNull(listaLibros);
        assertFalse(listaLibros.isEmpty());
        assertTrue(listaLibros.stream().allMatch(libro -> libro.getAnio() == anio && libro.getAlta()));
    }

    @Test
    void findByIsbnTest() {
        long isbn = 1234567890123L;
        Optional<Libro> libro = libroRepositorio.findByIsbn(isbn);

        assertTrue(libro.isPresent());
        assertEquals(isbn, libro.get().getIsbn());
    }

    @Test
    void findByIsbnAndAltaTrueTest() {
        long isbn = 1234567890123L;
        Optional<Libro> libro = libroRepositorio.findByIsbnAndAltaTrue(isbn);

        assertTrue(libro.isPresent());
        assertEquals(isbn, libro.get().getIsbn());
        assertTrue(libro.get().getAlta());
    }

    @Test
    void findByIsbnAndAltaFalseTest() {
        long isbn = 3456789012345L;
        Optional<Libro> libro = libroRepositorio.findByIsbnAndAltaFalse(isbn);

        assertTrue(libro.isPresent());
        assertEquals(isbn, libro.get().getIsbn());
        assertFalse(libro.get().getAlta());
    }

    @Test
    void findByTituloTest() {
        String titulo = "Título libro 1";
        Optional<Libro> libro = libroRepositorio.findByTitulo(titulo);

        assertTrue(libro.isPresent());
        assertEquals(titulo, libro.get().getTitulo());
    }

    @Test
    void findByTituloAndAltaTrueTest() {
        String titulo = "Título libro 1";
        Optional<Libro> libro = libroRepositorio.findByTituloAndAltaTrue(titulo);

        assertTrue(libro.isPresent());
        assertEquals(titulo, libro.get().getTitulo());
        assertTrue(libro.get().getAlta());
    }

    @Test
    void existsByAutoresAndAltaTrueTest() {
        Optional<Autor> autor = autorRepositorio.findByNombre("Nombre autor 1");

        assertTrue(autor.isPresent());
        assertTrue(libroRepositorio.existsByAutoresAndAltaTrue(autor.get()));
    }

    @Test
    void existsByEditorialAndAltaTrueTest() {
        Optional<Editorial> editorial = editorialRepositorio.findByNombre("Nombre editorial 1");

        assertTrue(editorial.isPresent());
        assertTrue(libroRepositorio.existsByEditorialAndAltaTrue(editorial.get()));
    }
}