package com.example.libreria.repository;

import com.example.libreria.model.Autor;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest

class AutorRepositorioTest {
    @Autowired
    private AutorRepositorio autorRepositorio;

    @BeforeEach
    void setUp() {
        Autor autor1 = new Autor();
        Autor autor2 = new Autor();

        autor1.setNombre("Nombre autor 1");
        autor1.setAlta(true);
        autorRepositorio.save(autor1);
        autor2.setNombre("Nombre autor 2");
        autor2.setAlta(false);
        autorRepositorio.save(autor2);
    }

    @Test
    void findAllByAltaTrueTest() {
        List<Autor> autores = autorRepositorio.findAllByAltaTrue();

        assertNotNull(autores);
        assertFalse(autores.isEmpty());
        assertTrue(autores.stream().allMatch(Autor::getAlta));
    }

    @Test
    void findAllByAltaFalseTest() {
        List<Autor> autores = autorRepositorio.findAllByAltaFalse();

        assertNotNull(autores);
        assertFalse(autores.isEmpty());
        assertTrue(autores.stream().noneMatch(Autor::getAlta));
    }

    @Test
    void findByNombreTest() {
        String nombre = "Nombre autor 1";
        Optional<Autor> autor = autorRepositorio.findByNombre(nombre);

        assertTrue(autor.isPresent());
        assertEquals(nombre, autor.get().getNombre());
    }

    @Test
    void findByNombreAndAltaTrueTest() {
        String nombre = "Nombre autor 1";
        Optional<Autor> autor = autorRepositorio.findByNombreAndAltaTrue(nombre);

        assertTrue(autor.isPresent());
        assertEquals(nombre, autor.get().getNombre());
        assertTrue(autor.get().getAlta());
    }

    @Test
    void findByNombreAndAltaFalseTest() {
        String nombre = "Nombre autor 2";
        Optional<Autor> autor = autorRepositorio.findByNombreAndAltaFalse(nombre);

        assertTrue(autor.isPresent());
        assertEquals(nombre, autor.get().getNombre());
        assertFalse(autor.get().getAlta());
    }
}