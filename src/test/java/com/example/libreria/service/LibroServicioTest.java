package com.example.libreria.service;

import com.example.libreria.dto.LibroDtoAdmin;
import com.example.libreria.dto.LibroDtoPublic;
import com.example.libreria.mapper.MapperLibro;
import com.example.libreria.model.Autor;
import com.example.libreria.model.Editorial;
import com.example.libreria.model.Libro;
import com.example.libreria.repository.AutorRepositorio;
import com.example.libreria.repository.EditorialRepositorio;
import com.example.libreria.repository.LibroRepositorio;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest

class LibroServicioTest {
    @Mock
    private AutorRepositorio autorRepositorio;

    @Mock
    private EditorialRepositorio editorialRepositorio;

    @Mock
    private LibroRepositorio libroRepositorio;

    @InjectMocks
    private LibroServicio libroServicio;

    private Autor autor;

    private Editorial editorial;

    private Libro libro;

    @BeforeEach
    void setUp() {
        autor = new Autor();

        autor.setId(1L);
        autor.setNombre("Nombre autor");
        autor.setAlta(true);

        Set<Autor> autores = new HashSet<>();

        autores.add(autor);

        editorial = new Editorial();

        editorial.setId(1L);
        editorial.setNombre("Nombre editorial");
        editorial.setAlta(true);

        libro = new Libro();

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
    void findAllLibrosAdminTest() {
        when(libroRepositorio.findAll()).thenReturn(Collections.singletonList(libro));

        List<LibroDtoAdmin> listaLibrosDtoAdmin = libroServicio.findAllLibrosAdmin();

        assertNotNull(listaLibrosDtoAdmin);
        assertEquals(Collections.singletonList(MapperLibro.toDtoAdmin(libro)), listaLibrosDtoAdmin);

        verify(libroRepositorio, times(1)).findAll();
    }

    @Test
    void findAllLibrosAdminByAltaTrueTest() {
        when(libroRepositorio.findAllByAltaTrue()).thenReturn(Collections.singletonList(libro));

        List<LibroDtoAdmin> listaLibrosDtoAdmin = libroServicio.findAllLibrosAdminByAltaTrue();

        assertNotNull(listaLibrosDtoAdmin);
        assertEquals(Collections.singletonList(libro).size(), listaLibrosDtoAdmin.size());
        assertTrue(listaLibrosDtoAdmin.stream().allMatch(LibroDtoAdmin::getAlta));

        verify(libroRepositorio, times(1)).findAllByAltaTrue();
    }

    @Test
    void findAllLibrosAdminByAltaFalseTest() {
        libro.setAlta(false);

        when(libroRepositorio.findAllByAltaFalse()).thenReturn(Collections.singletonList(libro));

        List<LibroDtoAdmin> listaLibrosDtoAdmin = libroServicio.findAllLibrosAdminByAltaFalse();

        assertNotNull(listaLibrosDtoAdmin);
        assertEquals(Collections.singletonList(libro).size(), listaLibrosDtoAdmin.size());
        assertTrue(listaLibrosDtoAdmin.stream().noneMatch(LibroDtoAdmin::getAlta));

        verify(libroRepositorio, times(1)).findAllByAltaFalse();
    }

    @Test
    void findAllLibrosPublicTest() {
        when(libroRepositorio.findAllByAltaTrue()).thenReturn(Collections.singletonList(libro));

        List<LibroDtoPublic> listaLibrosDtoPublic = libroServicio.findAllLibrosPublic();

        assertNotNull(listaLibrosDtoPublic);
        assertEquals(Collections.singletonList(MapperLibro.toDtoPublic(libro)), listaLibrosDtoPublic);

        verify(libroRepositorio, times(1)).findAllByAltaTrue();
    }

    @Test
    void findAllLibrosPublicByAnioTest() {
        when(libroRepositorio.findAllByAnioAndAltaTrue(libro.getAnio())).thenReturn(Collections.singletonList(libro));

        List<LibroDtoPublic> listaLibrosDtoPublic = libroServicio.findAllLibrosPublicByAnio(libro.getAnio());

        assertNotNull(listaLibrosDtoPublic);
        assertEquals(Collections.singletonList(MapperLibro.toDtoPublic(libro)), listaLibrosDtoPublic);

        verify(libroRepositorio, times(1)).findAllByAnioAndAltaTrue(libro.getAnio());
    }

    @Test
    void findLibroAdminByIsbnTest() {
        when(libroRepositorio.findByIsbn(libro.getIsbn())).thenReturn(Optional.of(libro));

        LibroDtoAdmin libroDtoAdmin = libroServicio.findLibroAdminByIsbn(libro.getIsbn());

        assertNotNull(libroDtoAdmin);
        assertEquals(libro.getIsbn(), libroDtoAdmin.getIsbn());

        verify(libroRepositorio, times(1)).findByIsbn(libro.getIsbn());
    }

    @Test
    void findLibroPublicByIsbnTest() {
        when(libroRepositorio.findByIsbnAndAltaTrue(libro.getIsbn())).thenReturn(Optional.of(libro));

        LibroDtoPublic libroDtoPublic = libroServicio.findLibroPublicByIsbn(libro.getIsbn());

        assertNotNull(libroDtoPublic);
        assertEquals(libro.getIsbn(), libroDtoPublic.getIsbn());

        verify(libroRepositorio, times(1)).findByIsbnAndAltaTrue(libro.getIsbn());
    }

    @Test
    void findLibroAdminByTituloTest() {
        when(libroRepositorio.findByTitulo(libro.getTitulo())).thenReturn(Optional.of(libro));

        LibroDtoAdmin libroDtoAdmin = libroServicio.findLibroAdminByTitulo(libro.getTitulo());

        assertNotNull(libroDtoAdmin);
        assertEquals(libro.getTitulo(), libroDtoAdmin.getTitulo());

        verify(libroRepositorio, times(1)).findByTitulo(libro.getTitulo());
    }

    @Test
    void findLibroPublicByTituloTest() {
        when(libroRepositorio.findByTituloAndAltaTrue(libro.getTitulo())).thenReturn(Optional.of(libro));

        LibroDtoPublic libroDtoPublic = libroServicio.findLibroPublicByTitulo(libro.getTitulo());

        assertNotNull(libroDtoPublic);
        assertEquals(libro.getTitulo(), libroDtoPublic.getTitulo());

        verify(libroRepositorio, times(1)).findByTituloAndAltaTrue(libro.getTitulo());
    }

    @Test
    void saveLibroTest() {
        LibroDtoAdmin libroDtoAdmin = MapperLibro.toDtoAdmin(libro);
        Libro newLibro = MapperLibro.toEntity(libroDtoAdmin);
        Autor newAutor = newLibro.getAutores().iterator().next();
        Editorial newEditorial = newLibro.getEditorial();

        when(libroRepositorio.findByIsbn(libroDtoAdmin.getIsbn())).thenReturn(Optional.empty());
        when(autorRepositorio.findByNombre(newAutor.getNombre())).thenReturn(Optional.empty());
        when(autorRepositorio.save(newAutor)).thenReturn(newAutor);
        when(editorialRepositorio.findByNombre(newEditorial.getNombre())).thenReturn(Optional.empty());
        when(editorialRepositorio.save(newEditorial)).thenReturn(newEditorial);
        when(libroRepositorio.save(newLibro)).thenReturn(newLibro);

        String mensaje = libroServicio.saveLibro(libroDtoAdmin);

        assertEquals("Libro guardado exitosamente", mensaje);

        verify(libroRepositorio, times(1)).findByIsbn(libroDtoAdmin.getIsbn());
        verify(autorRepositorio, times(1)).findByNombre(newAutor.getNombre());
        verify(autorRepositorio, times(1)).save(newAutor);
        verify(editorialRepositorio, times(1)).findByNombre(newEditorial.getNombre());
        verify(editorialRepositorio, times(1)).save(newEditorial);
        verify(libroRepositorio, times(1)).save(newLibro);
    }

    @Test
    void updateLibroTest() {
        LibroDtoAdmin libroDtoAdmin = MapperLibro.toDtoAdmin(libro);

        libroDtoAdmin.setTitulo("Nuevo título libro");
        libroDtoAdmin.setAnio(2001);
        libroDtoAdmin.setEjemplares(20);
        libroDtoAdmin.setEjemplaresPrestados(10);
        libroDtoAdmin.setEjemplaresRestantes(10);

        when(libroRepositorio.findByIsbn(libroDtoAdmin.getIsbn())).thenReturn(Optional.of(libro));
        when(libroRepositorio.save(libro)).thenReturn(libro);

        String mensaje = libroServicio.updateLibro(libroDtoAdmin);

        assertEquals("Libro actualizado exitosamente", mensaje);

        verify(libroRepositorio, times(1)).findByIsbn(libroDtoAdmin.getIsbn());
        verify(libroRepositorio, times(1)).save(libro);
    }

    @Test
    void lendLibroTest() {
        when(libroRepositorio.findByIsbnAndAltaTrue(libro.getIsbn())).thenReturn(Optional.of(libro));
        when(libroRepositorio.save(libro)).thenReturn(libro);

        String mensaje = libroServicio.lendLibro(libro.getIsbn());

        assertEquals("Libro prestado exitosamente", mensaje);

        verify(libroRepositorio, times(1)).findByIsbnAndAltaTrue(libro.getIsbn());
        verify(libroRepositorio, times(1)).save(libro);
    }

    @Test
    void returnLibroTest() {
        when(libroRepositorio.findByIsbnAndAltaTrue(libro.getIsbn())).thenReturn(Optional.of(libro));
        when(libroRepositorio.save(libro)).thenReturn(libro);

        String mensaje = libroServicio.returnLibro(libro.getIsbn());

        assertEquals("Libro devuelto exitosamente", mensaje);

        verify(libroRepositorio, times(1)).findByIsbnAndAltaTrue(libro.getIsbn());
        verify(libroRepositorio, times(1)).save(libro);
    }

    @Test
    void highLibroTest() {
        autor.setAlta(false);
        editorial.setAlta(false);
        libro.setAlta(false);

        when(libroRepositorio.findByIsbnAndAltaFalse(libro.getIsbn())).thenReturn(Optional.of(libro));
        when(libroRepositorio.save(libro)).thenReturn(libro);
        when(autorRepositorio.save(autor)).thenReturn(autor);
        when(editorialRepositorio.save(editorial)).thenReturn(editorial);

        String mensaje = libroServicio.highLibro(libro.getIsbn());

        assertEquals("Libro dado de alta exitosamente", mensaje);

        verify(libroRepositorio, times(1)).findByIsbnAndAltaFalse(libro.getIsbn());
        verify(libroRepositorio, times(1)).save(libro);
        verify(autorRepositorio, times(1)).save(autor);
        verify(editorialRepositorio, times(1)).save(editorial);
    }

    @Test
    void lowLibroTest() {
        when(libroRepositorio.findByIsbnAndAltaTrue(libro.getIsbn())).thenReturn(Optional.of(libro));
        when(libroRepositorio.save(libro)).thenReturn(libro);
        when(libroRepositorio.existsByAutoresAndAltaTrue(autor)).thenReturn(false);
        when(autorRepositorio.save(autor)).thenReturn(autor);
        when(libroRepositorio.existsByEditorialAndAltaTrue(editorial)).thenReturn(false);
        when(editorialRepositorio.save(editorial)).thenReturn(editorial);

        String mensaje = libroServicio.lowLibro(libro.getIsbn());

        assertEquals("Libro dado de baja exitosamente", mensaje);

        verify(libroRepositorio, times(1)).findByIsbnAndAltaTrue(libro.getIsbn());
        verify(libroRepositorio, times(1)).save(libro);
        verify(libroRepositorio, times(1)).existsByAutoresAndAltaTrue(autor);
        verify(autorRepositorio, times(1)).save(autor);
        verify(libroRepositorio, times(1)).existsByEditorialAndAltaTrue(editorial);
        verify(editorialRepositorio, times(1)).save(editorial);
    }

    @Test
    void deleteLibroTest() {
        when(libroRepositorio.findByIsbn(libro.getIsbn())).thenReturn(Optional.of(libro));
        doNothing().when(libroRepositorio).delete(libro);
        doNothing().when(autorRepositorio).delete(autor);
        doNothing().when(editorialRepositorio).delete(editorial);

        String mensaje = libroServicio.deleteLibro(libro.getIsbn());

        assertEquals("Libro eliminado exitosamente", mensaje);

        verify(libroRepositorio, times(1)).findByIsbn(libro.getIsbn());
        verify(libroRepositorio, times(1)).delete(libro);
        verify(autorRepositorio, times(1)).delete(autor);
        verify(editorialRepositorio, times(1)).delete(editorial);
    }
}