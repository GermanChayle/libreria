package com.example.libreria.repository;

import com.example.libreria.model.Editorial;
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

class EditorialRepositorioTest {
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @BeforeEach
    void setUp() {
        Editorial editorial1 = new Editorial();
        Editorial editorial2 = new Editorial();

        editorial1.setNombre("Nombre editorial 1");
        editorial1.setAlta(true);
        editorialRepositorio.save(editorial1);
        editorial2.setNombre("Nombre editorial 2");
        editorial2.setAlta(false);
        editorialRepositorio.save(editorial2);
    }

    @Test
    void findAllByAltaTrueTest() {
        List<Editorial> editoriales = editorialRepositorio.findAllByAltaTrue();

        assertNotNull(editoriales);
        assertFalse(editoriales.isEmpty());
        assertTrue(editoriales.stream().allMatch(Editorial::getAlta));
    }

    @Test
    void findAllByAltaFalseTest() {
        List<Editorial> editoriales = editorialRepositorio.findAllByAltaFalse();

        assertNotNull(editoriales);
        assertFalse(editoriales.isEmpty());
        assertTrue(editoriales.stream().noneMatch(Editorial::getAlta));
    }

    @Test
    void findByNombreTest() {
        String nombre = "Nombre editorial 1";
        Optional<Editorial> editorial = editorialRepositorio.findByNombre(nombre);

        assertTrue(editorial.isPresent());
        assertEquals(nombre, editorial.get().getNombre());
    }

    @Test
    void findByNombreAndAltaTrueTest() {
        String nombre = "Nombre editorial 1";
        Optional<Editorial> editorial = editorialRepositorio.findByNombreAndAltaTrue(nombre);

        assertTrue(editorial.isPresent());
        assertEquals(nombre, editorial.get().getNombre());
        assertTrue(editorial.get().getAlta());
    }

    @Test
    void findByNombreAndAltaFalseTest() {
        String nombre = "Nombre editorial 2";
        Optional<Editorial> editorial = editorialRepositorio.findByNombreAndAltaFalse(nombre);

        assertTrue(editorial.isPresent());
        assertEquals(nombre, editorial.get().getNombre());
        assertFalse(editorial.get().getAlta());
    }
}