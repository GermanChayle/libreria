package com.example.libreria.service;

import com.example.libreria.dto.AutorDto;
import com.example.libreria.dto.AutorDtoAdmin;
import com.example.libreria.dto.AutorDtoPublic;
import com.example.libreria.mapper.MapperAutor;
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

class AutorServicioTest {
    @Mock
    private AutorRepositorio autorRepositorio;

    @Mock
    private EditorialRepositorio editorialRepositorio;

    @Mock
    private LibroRepositorio libroRepositorio;

    @InjectMocks
    private AutorServicio autorServicio;

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

        Set<Libro> libros = new HashSet<>();

        libros.add(libro);
        autor.setLibros(libros);
    }

    @Test
    void findAllAutoresAdminTest() {
        when(autorRepositorio.findAll()).thenReturn(Collections.singletonList(autor));

        List<AutorDtoAdmin> listaAutoresDtoAdmin = autorServicio.findAllAutoresAdmin();

        assertNotNull(listaAutoresDtoAdmin);
        assertEquals(Collections.singletonList(MapperAutor.toDtoAdmin(autor)), listaAutoresDtoAdmin);

        verify(autorRepositorio, times(1)).findAll();
    }

    @Test
    void findAllAutoresAdminByAltaTrueTest() {
        when(autorRepositorio.findAllByAltaTrue()).thenReturn(Collections.singletonList(autor));

        List<AutorDtoAdmin> listaAutoresDtoAdmin = autorServicio.findAllAutoresAdminByAltaTrue();

        assertNotNull(listaAutoresDtoAdmin);
        assertEquals(Collections.singletonList(autor).size(), listaAutoresDtoAdmin.size());
        assertTrue(listaAutoresDtoAdmin.stream().allMatch(AutorDtoAdmin::getAlta));

        verify(autorRepositorio, times(1)).findAllByAltaTrue();
    }

    @Test
    void findAllAutoresAdminByAltaFalseTest() {
        autor.setAlta(false);

        when(autorRepositorio.findAllByAltaFalse()).thenReturn(Collections.singletonList(autor));

        List<AutorDtoAdmin> listaAutoresDtoAdmin = autorServicio.findAllAutoresAdminByAltaFalse();

        assertNotNull(listaAutoresDtoAdmin);
        assertEquals(Collections.singletonList(autor).size(), listaAutoresDtoAdmin.size());
        assertTrue(listaAutoresDtoAdmin.stream().noneMatch(AutorDtoAdmin::getAlta));

        verify(autorRepositorio, times(1)).findAllByAltaFalse();
    }

    @Test
    void findAllAutoresPublicTest() {
        when(autorRepositorio.findAllByAltaTrue()).thenReturn(Collections.singletonList(autor));

        List<AutorDtoPublic> listaAutoresDtoPublic = autorServicio.findAllAutoresPublic();

        assertNotNull(listaAutoresDtoPublic);
        assertEquals(Collections.singletonList(MapperAutor.toDtoPublic(autor)), listaAutoresDtoPublic);

        verify(autorRepositorio, times(1)).findAllByAltaTrue();
    }

    @Test
    void findAutorAdminByNombreTest() {
        when(autorRepositorio.findByNombre(autor.getNombre())).thenReturn(Optional.of(autor));

        AutorDtoAdmin autorDtoAdmin = autorServicio.findAutorAdminByNombre(autor.getNombre());

        assertNotNull(autorDtoAdmin);
        assertEquals(autor.getNombre(), autorDtoAdmin.getNombre());

        verify(autorRepositorio, times(1)).findByNombre(autor.getNombre());
    }

    @Test
    void findAutorPublicByNombreTest() {
        when(autorRepositorio.findByNombreAndAltaTrue(autor.getNombre())).thenReturn(Optional.of(autor));

        AutorDtoPublic autorDtoPublic = autorServicio.findAutorPublicByNombre(autor.getNombre());

        assertNotNull(autorDtoPublic);
        assertEquals(autor.getNombre(), autorDtoPublic.getNombre());

        verify(autorRepositorio, times(1)).findByNombreAndAltaTrue(autor.getNombre());
    }

    @Test
    void saveAutorTest() {
        AutorDto autorDto = MapperAutor.toDto(autor);
        Autor newAutor = MapperAutor.toEntity(autorDto);

        when(autorRepositorio.findByNombre(autorDto.getNombre())).thenReturn(Optional.empty());
        when(autorRepositorio.save(newAutor)).thenReturn(newAutor);

        String mensaje = autorServicio.saveAutor(autorDto);

        assertEquals("Autor guardado exitosamente", mensaje);

        verify(autorRepositorio, times(1)).findByNombre(autorDto.getNombre());
        verify(autorRepositorio, times(1)).save(newAutor);
    }

    @Test
    void updateAutorTest() {
        AutorDto autorDto = new AutorDto();

        autorDto.setNombre("Nuevo nombre autor");

        when(autorRepositorio.findByNombre(autor.getNombre())).thenReturn(Optional.of(autor));
        when(autorRepositorio.findByNombre(autorDto.getNombre())).thenReturn(Optional.empty());
        when(autorRepositorio.save(autor)).thenReturn(autor);

        String mensaje = autorServicio.updateAutor(autor.getNombre(), autorDto);

        assertEquals("Autor actualizado exitosamente", mensaje);

        verify(autorRepositorio, times(1)).findByNombre(autor.getNombre());
        verify(autorRepositorio, times(1)).findByNombre(autorDto.getNombre());
        verify(autorRepositorio, times(1)).save(autor);
    }

    @Test
    void highAutorTest() {
        autor.setAlta(false);
        editorial.setAlta(false);
        libro.setAlta(false);

        when(autorRepositorio.findByNombreAndAltaFalse(autor.getNombre())).thenReturn(Optional.of(autor));
        when(autorRepositorio.save(autor)).thenReturn(autor);
        when(libroRepositorio.save(libro)).thenReturn(libro);
        when(editorialRepositorio.save(editorial)).thenReturn(editorial);

        String mensaje = autorServicio.highAutor(autor.getNombre());

        assertEquals("Autor dado de alta exitosamente", mensaje);

        verify(autorRepositorio, times(1)).findByNombreAndAltaFalse(autor.getNombre());
        verify(autorRepositorio, times(1)).save(autor);
        verify(libroRepositorio, times(1)).save(libro);
        verify(editorialRepositorio, times(1)).save(editorial);
    }

    @Test
    void lowAutorTest() {
        when(autorRepositorio.findByNombreAndAltaTrue(autor.getNombre())).thenReturn(Optional.of(autor));
        when(libroRepositorio.save(libro)).thenReturn(libro);
        when(libroRepositorio.existsByAutoresAndAltaTrue(autor)).thenReturn(false);
        when(autorRepositorio.save(autor)).thenReturn(autor);
        when(libroRepositorio.existsByEditorialAndAltaTrue(editorial)).thenReturn(false);
        when(editorialRepositorio.save(editorial)).thenReturn(editorial);

        String mensaje = autorServicio.lowAutor(autor.getNombre());

        assertEquals("Autor dado de baja exitosamente", mensaje);

        verify(autorRepositorio, times(1)).findByNombreAndAltaTrue(autor.getNombre());
        verify(libroRepositorio, times(1)).save(libro);
        verify(libroRepositorio, times(1)).existsByAutoresAndAltaTrue(autor);
        verify(autorRepositorio, times(1)).save(autor);
        verify(libroRepositorio, times(1)).existsByEditorialAndAltaTrue(editorial);
        verify(editorialRepositorio, times(1)).save(editorial);
    }

    @Test
    void deleteAutorTest() {
        autor.getLibros().clear();

        when(autorRepositorio.findByNombre(autor.getNombre())).thenReturn(Optional.of(autor));
        doNothing().when(autorRepositorio).delete(autor);

        String mensaje = autorServicio.deleteAutor(autor.getNombre());

        assertEquals("Autor eliminado exitosamente", mensaje);

        verify(autorRepositorio, times(1)).findByNombre(autor.getNombre());
        verify(autorRepositorio, times(1)).delete(autor);
    }
}