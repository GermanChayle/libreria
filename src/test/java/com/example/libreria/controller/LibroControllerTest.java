package com.example.libreria.controller;

import com.example.libreria.dto.LibroDtoAdmin;
import com.example.libreria.dto.LibroDtoPublic;
import com.example.libreria.mapper.MapperLibro;
import com.example.libreria.model.Autor;
import com.example.libreria.model.Editorial;
import com.example.libreria.model.Libro;
import com.example.libreria.service.LibroServicio;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibroController.class)

class LibroControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibroServicio libroServicio;

    private Libro libro;

    private LibroDtoAdmin libroDtoAdmin;

    private LibroDtoPublic libroDtoPublic;

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

        libroDtoAdmin = MapperLibro.toDtoAdmin(libro);
        libroDtoPublic = MapperLibro.toDtoPublic(libro);
    }

    @Test
    void findAllLibrosAdminTest() throws Exception {
        List<LibroDtoAdmin> listaLibrosDtoAdmin = Collections.singletonList(libroDtoAdmin);

        when(libroServicio.findAllLibrosAdmin()).thenReturn(listaLibrosDtoAdmin);

        String listaLibrosDtoAdminJson = new ObjectMapper().writeValueAsString(listaLibrosDtoAdmin);

        mockMvc.perform(get("/libros/findAll/admin").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaLibrosDtoAdminJson));

        verify(libroServicio, times(1)).findAllLibrosAdmin();
    }

    @Test
    void findAllLibrosAdminByAltaTrueTest() throws Exception {
        List<LibroDtoAdmin> listaLibrosDtoAdmin = Collections.singletonList(libroDtoAdmin);

        when(libroServicio.findAllLibrosAdminByAltaTrue()).thenReturn(listaLibrosDtoAdmin);

        String listaLibrosDtoAdminJson = new ObjectMapper().writeValueAsString(listaLibrosDtoAdmin);

        mockMvc.perform(get("/libros/findAll/admin/altaTrue").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaLibrosDtoAdminJson));

        verify(libroServicio, times(1)).findAllLibrosAdminByAltaTrue();
    }

    @Test
    void findAllLibrosAdminByAltaFalseTest() throws Exception {
        libroDtoAdmin.setAlta(false);

        List<LibroDtoAdmin> listaLibrosDtoAdmin = Collections.singletonList(libroDtoAdmin);

        when(libroServicio.findAllLibrosAdminByAltaFalse()).thenReturn(listaLibrosDtoAdmin);

        String listaLibrosDtoAdminJson = new ObjectMapper().writeValueAsString(listaLibrosDtoAdmin);

        mockMvc.perform(get("/libros/findAll/admin/altaFalse").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaLibrosDtoAdminJson));

        verify(libroServicio, times(1)).findAllLibrosAdminByAltaFalse();
    }

    @Test
    void findAllLibrosPublicTest() throws Exception {
        List<LibroDtoPublic> listaLibrosDtoPublic = Collections.singletonList(libroDtoPublic);

        when(libroServicio.findAllLibrosPublic()).thenReturn(listaLibrosDtoPublic);

        String listaLibrosDtoPublicJson = new ObjectMapper().writeValueAsString(listaLibrosDtoPublic);

        mockMvc.perform(get("/libros/findAll/public").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaLibrosDtoPublicJson));

        verify(libroServicio, times(1)).findAllLibrosPublic();
    }

    @Test
    void findAllLibrosPublicByAnioTest() throws Exception {
        List<LibroDtoPublic> listaLibrosDtoPublic = Collections.singletonList(libroDtoPublic);

        when(libroServicio.findAllLibrosPublicByAnio(libro.getAnio())).thenReturn(listaLibrosDtoPublic);

        String listaLibrosDtoPublicJson = new ObjectMapper().writeValueAsString(listaLibrosDtoPublic);

        mockMvc.perform(get("/libros/findAll/public/anio/{anio}", libro.getAnio())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaLibrosDtoPublicJson));

        verify(libroServicio, times(1)).findAllLibrosPublicByAnio(libro.getAnio());
    }

    @Test
    void findLibroAdminByIsbnTest() throws Exception {
        when(libroServicio.findLibroAdminByIsbn(libro.getIsbn())).thenReturn(libroDtoAdmin);

        mockMvc.perform(get("/libros/find/admin/isbn/{isbn}", libro.getIsbn())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(libro.getIsbn()));

        verify(libroServicio, times(1)).findLibroAdminByIsbn(libro.getIsbn());
    }

    @Test
    void findLibroPublicByIsbnTest() throws Exception {
        when(libroServicio.findLibroPublicByIsbn(libro.getIsbn())).thenReturn(libroDtoPublic);

        mockMvc.perform(get("/libros/find/public/isbn/{isbn}", libro.getIsbn())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(libro.getIsbn()));

        verify(libroServicio, times(1)).findLibroPublicByIsbn(libro.getIsbn());
    }

    @Test
    void findLibroAdminByTituloTest() throws Exception {
        when(libroServicio.findLibroAdminByTitulo(libro.getTitulo())).thenReturn(libroDtoAdmin);

        mockMvc.perform(get("/libros/find/admin/titulo/{titulo}", libro.getTitulo())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value(libro.getTitulo()));

        verify(libroServicio, times(1)).findLibroAdminByTitulo(libro.getTitulo());
    }

    @Test
    void findLibroPublicByTituloTest() throws Exception {
        when(libroServicio.findLibroPublicByTitulo(libro.getTitulo())).thenReturn(libroDtoPublic);

        mockMvc.perform(get("/libros/find/public/titulo/{titulo}", libro.getTitulo())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value(libro.getTitulo()));

        verify(libroServicio, times(1)).findLibroPublicByTitulo(libro.getTitulo());
    }

    @Test
    void saveLibroTest() throws Exception {
        String mensaje = "Libro guardado exitosamente";

        when(libroServicio.saveLibro(libroDtoAdmin)).thenReturn(mensaje);

        mockMvc.perform(post("/libros/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(libroDtoAdmin))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(mensaje));

        verify(libroServicio, times(1)).saveLibro(libroDtoAdmin);
    }

    @Test
    void updateLibroTest() throws Exception {
        LibroDtoAdmin newLibroDtoAdmin = new LibroDtoAdmin();

        newLibroDtoAdmin.setIsbn(1234567890123L);
        newLibroDtoAdmin.setTitulo("Nuevo título libro");
        newLibroDtoAdmin.setAnio(2001);
        newLibroDtoAdmin.setEjemplares(20);
        newLibroDtoAdmin.setEjemplaresPrestados(10);
        newLibroDtoAdmin.setEjemplaresRestantes(10);

        String mensaje = "Libro actualizado exitosamente";

        when(libroServicio.updateLibro(newLibroDtoAdmin)).thenReturn(mensaje);

        mockMvc.perform(put("/libros/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newLibroDtoAdmin))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(libroServicio, times(1)).updateLibro(newLibroDtoAdmin);
    }

    @Test
    void lendLibroTest() throws Exception {
        String mensaje = "Libro prestado exitosamente";

        when(libroServicio.lendLibro(libro.getIsbn())).thenReturn(mensaje);

        mockMvc.perform(put("/libros/lend/isbn/{isbn}", libro.getIsbn())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(libroServicio, times(1)).lendLibro(libro.getIsbn());
    }

    @Test
    void returnLibroTest() throws Exception {
        String mensaje = "Libro devuelto exitosamente";

        when(libroServicio.returnLibro(libro.getIsbn())).thenReturn(mensaje);

        mockMvc.perform(put("/libros/return/isbn/{isbn}", libro.getIsbn())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(libroServicio, times(1)).returnLibro(libro.getIsbn());
    }

    @Test
    void highLibroTest() throws Exception {
        libro.setAlta(false);

        String mensaje = "Libro dado de alta exitosamente";

        when(libroServicio.highLibro(libro.getIsbn())).thenReturn(mensaje);

        mockMvc.perform(put("/libros/high/isbn/{isbn}", libro.getIsbn())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(libroServicio, times(1)).highLibro(libro.getIsbn());
    }

    @Test
    void lowLibroTest() throws Exception {
        String mensaje = "Libro dado de baja exitosamente";

        when(libroServicio.lowLibro(libro.getIsbn())).thenReturn(mensaje);

        mockMvc.perform(put("/libros/low/isbn/{isbn}", libro.getIsbn())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(libroServicio, times(1)).lowLibro(libro.getIsbn());
    }

    @Test
    void deleteLibroTest() throws Exception {
        String mensaje = "Libro eliminado exitosamente";

        when(libroServicio.deleteLibro(libro.getIsbn())).thenReturn(mensaje);

        mockMvc.perform(delete("/libros/delete/isbn/{isbn}", libro.getIsbn())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(libroServicio, times(1)).deleteLibro(libro.getIsbn());
    }
}