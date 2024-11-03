package com.example.libreria.service;

import com.example.libreria.dto.EditorialDto;
import com.example.libreria.dto.EditorialDtoAdmin;
import com.example.libreria.dto.EditorialDtoPublic;
import com.example.libreria.mapper.MapperEditorial;
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

class EditorialServicioTest {
    @Mock
    private AutorRepositorio autorRepositorio;

    @Mock
    private EditorialRepositorio editorialRepositorio;

    @Mock
    private LibroRepositorio libroRepositorio;

    @InjectMocks
    private EditorialServicio editorialServicio;

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
        libro.setTitulo("Título libro");
        libro.setAlta(true);
        libro.setAutores(autores);
        libro.setEditorial(editorial);

        Set<Libro> libros = new HashSet<>();

        libros.add(libro);
        editorial.setLibros(libros);
    }

    @Test
    void findAllEditorialesAdminTest() {
        when(editorialRepositorio.findAll()).thenReturn(Collections.singletonList(editorial));

        List<EditorialDtoAdmin> listaEditorialesDtoAdmin = editorialServicio.findAllEditorialesAdmin();

        assertNotNull(listaEditorialesDtoAdmin);
        assertEquals(Collections.singletonList(MapperEditorial.toDtoAdmin(editorial)), listaEditorialesDtoAdmin);

        verify(editorialRepositorio, times(1)).findAll();
    }

    @Test
    void findAllEditorialesAdminByAltaTrueTest() {
        when(editorialRepositorio.findAllByAltaTrue()).thenReturn(Collections.singletonList(editorial));

        List<EditorialDtoAdmin> listaEditorialesDtoAdmin = editorialServicio.findAllEditorialesAdminByAltaTrue();

        assertNotNull(listaEditorialesDtoAdmin);
        assertEquals(Collections.singletonList(editorial).size(), listaEditorialesDtoAdmin.size());
        assertTrue(listaEditorialesDtoAdmin.stream().allMatch(EditorialDtoAdmin::getAlta));

        verify(editorialRepositorio, times(1)).findAllByAltaTrue();
    }

    @Test
    void findAllEditorialesAdminByAltaFalseTest() {
        editorial.setAlta(false);

        when(editorialRepositorio.findAllByAltaFalse()).thenReturn(Collections.singletonList(editorial));

        List<EditorialDtoAdmin> listaEditorialesDtoAdmin = editorialServicio.findAllEditorialesAdminByAltaFalse();

        assertNotNull(listaEditorialesDtoAdmin);
        assertEquals(Collections.singletonList(editorial).size(), listaEditorialesDtoAdmin.size());
        assertTrue(listaEditorialesDtoAdmin.stream().noneMatch(EditorialDtoAdmin::getAlta));

        verify(editorialRepositorio, times(1)).findAllByAltaFalse();
    }

    @Test
    void findAllEditorialesPublicTest() {
        when(editorialRepositorio.findAllByAltaTrue()).thenReturn(Collections.singletonList(editorial));

        List<EditorialDtoPublic> listaEditorialesDtoPublic = editorialServicio.findAllEditorialesPublic();

        assertNotNull(listaEditorialesDtoPublic);
        assertEquals(Collections.singletonList(MapperEditorial.toDtoPublic(editorial)), listaEditorialesDtoPublic);

        verify(editorialRepositorio, times(1)).findAllByAltaTrue();
    }

    @Test
    void findEditorialAdminByNombreTest() {
        when(editorialRepositorio.findByNombre(editorial.getNombre())).thenReturn(Optional.of(editorial));

        EditorialDtoAdmin editorialDtoAdmin = editorialServicio.findEditorialAdminByNombre(editorial.getNombre());

        assertNotNull(editorialDtoAdmin);
        assertEquals(editorial.getNombre(), editorialDtoAdmin.getNombre());

        verify(editorialRepositorio, times(1)).findByNombre(editorial.getNombre());
    }

    @Test
    void findEditorialPublicByNombreTest() {
        when(editorialRepositorio.findByNombreAndAltaTrue(editorial.getNombre())).thenReturn(Optional.of(editorial));

        EditorialDtoPublic editorialDtoPublic = editorialServicio.findEditorialPublicByNombre(editorial.getNombre());

        assertNotNull(editorialDtoPublic);
        assertEquals(editorial.getNombre(), editorialDtoPublic.getNombre());

        verify(editorialRepositorio, times(1)).findByNombreAndAltaTrue(editorial.getNombre());
    }

    @Test
    void saveEditorialTest() {
        EditorialDto editorialDto = MapperEditorial.toDto(editorial);
        Editorial newEditorial = MapperEditorial.toEntity(editorialDto);

        when(editorialRepositorio.findByNombre(editorialDto.getNombre())).thenReturn(Optional.empty());
        when(editorialRepositorio.save(newEditorial)).thenReturn(newEditorial);

        String mensaje = editorialServicio.saveEditorial(editorialDto);

        assertEquals("Editorial guardada exitosamente", mensaje);

        verify(editorialRepositorio, times(1)).findByNombre(editorialDto.getNombre());
        verify(editorialRepositorio, times(1)).save(newEditorial);
    }

    @Test
    void updateEditorialTest() {
        EditorialDto editorialDto = new EditorialDto();

        editorialDto.setNombre("Nuevo nombre editorial");

        when(editorialRepositorio.findByNombre(editorial.getNombre())).thenReturn(Optional.of(editorial));
        when(editorialRepositorio.findByNombre(editorialDto.getNombre())).thenReturn(Optional.empty());
        when(editorialRepositorio.save(editorial)).thenReturn(editorial);

        String mensaje = editorialServicio.updateEditorial(editorial.getNombre(), editorialDto);

        assertEquals("Editorial actualizada exitosamente", mensaje);

        verify(editorialRepositorio, times(1)).findByNombre(editorial.getNombre());
        verify(editorialRepositorio, times(1)).findByNombre(editorialDto.getNombre());
        verify(editorialRepositorio, times(1)).save(editorial);
    }

    @Test
    void highEditorialTest() {
        autor.setAlta(false);
        editorial.setAlta(false);
        libro.setAlta(false);

        when(editorialRepositorio.findByNombreAndAltaFalse(editorial.getNombre())).thenReturn(Optional.of(editorial));
        when(editorialRepositorio.save(editorial)).thenReturn(editorial);
        when(libroRepositorio.save(libro)).thenReturn(libro);
        when(autorRepositorio.save(autor)).thenReturn(autor);

        String mensaje = editorialServicio.highEditorial(editorial.getNombre());

        assertEquals("Editorial dada de alta exitosamente", mensaje);

        verify(editorialRepositorio, times(1)).findByNombreAndAltaFalse(editorial.getNombre());
        verify(editorialRepositorio, times(1)).save(editorial);
        verify(libroRepositorio, times(1)).save(libro);
        verify(autorRepositorio, times(1)).save(autor);
    }

    @Test
    void lowEditorialTest() {
        when(editorialRepositorio.findByNombreAndAltaTrue(editorial.getNombre())).thenReturn(Optional.of(editorial));
        when(libroRepositorio.save(libro)).thenReturn(libro);
        when(libroRepositorio.existsByAutoresAndAltaTrue(autor)).thenReturn(false);
        when(autorRepositorio.save(autor)).thenReturn(autor);
        when(libroRepositorio.existsByEditorialAndAltaTrue(editorial)).thenReturn(false);
        when(editorialRepositorio.save(editorial)).thenReturn(editorial);

        String mensaje = editorialServicio.lowEditorial(editorial.getNombre());

        assertEquals("Editorial dada de baja exitosamente", mensaje);

        verify(editorialRepositorio, times(1)).findByNombreAndAltaTrue(editorial.getNombre());
        verify(libroRepositorio, times(1)).save(libro);
        verify(libroRepositorio, times(1)).existsByAutoresAndAltaTrue(autor);
        verify(autorRepositorio, times(1)).save(autor);
        verify(libroRepositorio, times(1)).existsByEditorialAndAltaTrue(editorial);
        verify(editorialRepositorio, times(1)).save(editorial);
    }

    @Test
    void deleteEditorialTest() {
        editorial.getLibros().clear();

        when(editorialRepositorio.findByNombre(editorial.getNombre())).thenReturn(Optional.of(editorial));
        doNothing().when(editorialRepositorio).delete(editorial);

        String mensaje = editorialServicio.deleteEditorial(editorial.getNombre());

        assertEquals("Editorial eliminada exitosamente", mensaje);

        verify(editorialRepositorio, times(1)).findByNombre(editorial.getNombre());
        verify(editorialRepositorio, times(1)).delete(editorial);
    }
}