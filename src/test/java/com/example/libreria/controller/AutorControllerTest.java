package com.example.libreria.controller;

import com.example.libreria.dto.AutorDto;
import com.example.libreria.dto.AutorDtoAdmin;
import com.example.libreria.dto.AutorDtoPublic;
import com.example.libreria.mapper.MapperAutor;
import com.example.libreria.model.Autor;
import com.example.libreria.model.Editorial;
import com.example.libreria.model.Libro;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.libreria.service.AutorServicio;
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

@WebMvcTest(AutorController.class)

class AutorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AutorServicio autorServicio;

    private Autor autor;

    private AutorDtoAdmin autorDtoAdmin;

    private AutorDtoPublic autorDtoPublic;

    @BeforeEach
    void setUp() {
        autor = new Autor();

        autor.setId(1L);
        autor.setNombre("Nombre autor");
        autor.setAlta(true);

        Set<Autor> autores = new HashSet<>();

        autores.add(autor);

        Editorial editorial = new Editorial();

        editorial.setId(1L);
        editorial.setNombre("Nombre editorial");
        editorial.setAlta(true);

        Libro libro = new Libro();

        libro.setId(1L);
        libro.setTitulo("Título libro");
        libro.setAlta(true);
        libro.setAutores(autores);
        libro.setEditorial(editorial);

        Set<Libro> libros = new HashSet<>();

        libros.add(libro);
        autor.setLibros(libros);

        autorDtoAdmin = MapperAutor.toDtoAdmin(autor);
        autorDtoPublic = MapperAutor.toDtoPublic(autor);
    }

    @Test
    void findAllAutoresAdminTest() throws Exception {
        List<AutorDtoAdmin> listaAutoresDtoAdmin = Collections.singletonList(autorDtoAdmin);

        when(autorServicio.findAllAutoresAdmin()).thenReturn(listaAutoresDtoAdmin);

        String listaAutoresDtoAdminJson = new ObjectMapper().writeValueAsString(listaAutoresDtoAdmin);

        mockMvc.perform(get("/autores/findAll/admin").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaAutoresDtoAdminJson));

        verify(autorServicio, times(1)).findAllAutoresAdmin();
    }

    @Test
    void findAllAutoresAdminByAltaTrueTest() throws Exception {
        List<AutorDtoAdmin> listaAutoresDtoAdmin = Collections.singletonList(autorDtoAdmin);

        when(autorServicio.findAllAutoresAdminByAltaTrue()).thenReturn(listaAutoresDtoAdmin);

        String listaAutoresDtoAdminJson = new ObjectMapper().writeValueAsString(listaAutoresDtoAdmin);

        mockMvc.perform(get("/autores/findAll/admin/altaTrue").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaAutoresDtoAdminJson));

        verify(autorServicio, times(1)).findAllAutoresAdminByAltaTrue();
    }

    @Test
    void findAllAutoresAdminByAltaFalseTest() throws Exception {
        autorDtoAdmin.setAlta(false);

        List<AutorDtoAdmin> listaAutoresDtoAdmin = Collections.singletonList(autorDtoAdmin);

        when(autorServicio.findAllAutoresAdminByAltaFalse()).thenReturn(listaAutoresDtoAdmin);

        String listaAutoresDtoAdminJson = new ObjectMapper().writeValueAsString(listaAutoresDtoAdmin);

        mockMvc.perform(get("/autores/findAll/admin/altaFalse").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaAutoresDtoAdminJson));

        verify(autorServicio, times(1)).findAllAutoresAdminByAltaFalse();
    }

    @Test
    void findAllAutoresPublicTest() throws Exception {
        List<AutorDtoPublic> listaAutoresDtoPublic = Collections.singletonList(autorDtoPublic);

        when(autorServicio.findAllAutoresPublic()).thenReturn(listaAutoresDtoPublic);

        String listaAutoresDtoPublicJson = new ObjectMapper().writeValueAsString(listaAutoresDtoPublic);

        mockMvc.perform(get("/autores/findAll/public").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaAutoresDtoPublicJson));

        verify(autorServicio, times(1)).findAllAutoresPublic();
    }

    @Test
    void findAutorAdminByNombreTest() throws Exception {
        when(autorServicio.findAutorAdminByNombre(autor.getNombre())).thenReturn(autorDtoAdmin);

        mockMvc.perform(get("/autores/find/admin/nombre/{nombre}", autor.getNombre())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(autor.getNombre()));

        verify(autorServicio, times(1)).findAutorAdminByNombre(autor.getNombre());
    }

    @Test
    void findAutorPublicByNombreTest() throws Exception {
        when(autorServicio.findAutorPublicByNombre(autor.getNombre())).thenReturn(autorDtoPublic);

        mockMvc.perform(get("/autores/find/public/nombre/{nombre}", autor.getNombre())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(autor.getNombre()));

        verify(autorServicio, times(1)).findAutorPublicByNombre(autor.getNombre());
    }

    @Test
    void saveAutorTest() throws Exception {
        AutorDto autorDto = MapperAutor.toDto(autor);
        String mensaje = "Autor guardado exitosamente";

        when(autorServicio.saveAutor(autorDto)).thenReturn(mensaje);

        mockMvc.perform(post("/autores/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(autorDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(mensaje));

        verify(autorServicio, times(1)).saveAutor(autorDto);
    }

    @Test
    void updateAutorTest() throws Exception {
        AutorDto autorDto = new AutorDto();

        autorDto.setNombre("Nuevo nombre autor");

        String mensaje = "Autor actualizado exitosamente";

        when(autorServicio.updateAutor(autor.getNombre(), autorDto)).thenReturn(mensaje);

        mockMvc.perform(put("/autores/update/nombre/{nombre}", autor.getNombre())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(autorDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(autorServicio, times(1)).updateAutor(autor.getNombre(), autorDto);
    }

    @Test
    void highAutorTest() throws Exception {
        autor.setAlta(false);

        String mensaje = "Autor dado de alta exitosamente";

        when(autorServicio.highAutor(autor.getNombre())).thenReturn(mensaje);

        mockMvc.perform(put("/autores/high/nombre/{nombre}", autor.getNombre())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(autorServicio, times(1)).highAutor(autor.getNombre());
    }

    @Test
    void lowAutorTest() throws Exception {
        String mensaje = "Autor dado de baja exitosamente";

        when(autorServicio.lowAutor(autor.getNombre())).thenReturn(mensaje);

        mockMvc.perform(put("/autores/low/nombre/{nombre}", autor.getNombre())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(autorServicio, times(1)).lowAutor(autor.getNombre());
    }

    @Test
    void deleteAutorTest() throws Exception {
        autor.getLibros().clear();

        String mensaje = "Autor eliminado exitosamente";

        when(autorServicio.deleteAutor(autor.getNombre())).thenReturn(mensaje);

        mockMvc.perform(delete("/autores/delete/nombre/{nombre}", autor.getNombre())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(autorServicio, times(1)).deleteAutor(autor.getNombre());
    }
}