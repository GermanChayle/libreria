package com.example.libreria.controller;

import com.example.libreria.dto.EditorialDto;
import com.example.libreria.dto.EditorialDtoAdmin;
import com.example.libreria.dto.EditorialDtoPublic;
import com.example.libreria.mapper.MapperEditorial;
import com.example.libreria.model.Autor;
import com.example.libreria.model.Editorial;
import com.example.libreria.model.Libro;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.libreria.service.EditorialServicio;
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

@WebMvcTest(EditorialController.class)

class EditorialControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EditorialServicio editorialServicio;

    private Editorial editorial;

    private EditorialDtoAdmin editorialDtoAdmin;

    private EditorialDtoPublic editorialDtoPublic;

    @BeforeEach
    void setUp() {
        Autor autor = new Autor();

        autor.setId(1L);
        autor.setNombre("Nombre autor");
        autor.setAlta(true);

        Set<Autor> autores = new HashSet<>();

        autores.add(autor);

        editorial = new Editorial();

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
        editorial.setLibros(libros);

        editorialDtoAdmin = MapperEditorial.toDtoAdmin(editorial);
        editorialDtoPublic = MapperEditorial.toDtoPublic(editorial);
    }

    @Test
    void findAllEditorialesAdminTest() throws Exception {
        List<EditorialDtoAdmin> listaEditorialesDtoAdmin = Collections.singletonList(editorialDtoAdmin);

        when(editorialServicio.findAllEditorialesAdmin()).thenReturn(listaEditorialesDtoAdmin);

        String listaEditorialesDtoAdminJson = new ObjectMapper().writeValueAsString(listaEditorialesDtoAdmin);

        mockMvc.perform(get("/editoriales/findAll/admin").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaEditorialesDtoAdminJson));

        verify(editorialServicio, times(1)).findAllEditorialesAdmin();
    }

    @Test
    void findAllEditorialesAdminByAltaTrueTest() throws Exception {
        List<EditorialDtoAdmin> listaEditorialesDtoAdmin = Collections.singletonList(editorialDtoAdmin);

        when(editorialServicio.findAllEditorialesAdminByAltaTrue()).thenReturn(listaEditorialesDtoAdmin);

        String listaEditorialesDtoAdminJson = new ObjectMapper().writeValueAsString(listaEditorialesDtoAdmin);

        mockMvc.perform(get("/editoriales/findAll/admin/altaTrue").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaEditorialesDtoAdminJson));

        verify(editorialServicio, times(1)).findAllEditorialesAdminByAltaTrue();
    }

    @Test
    void findAllEditorialesAdminByAltaFalseTest() throws Exception {
        editorialDtoAdmin.setAlta(false);

        List<EditorialDtoAdmin> listaEditorialesDtoAdmin = Collections.singletonList(editorialDtoAdmin);

        when(editorialServicio.findAllEditorialesAdminByAltaFalse()).thenReturn(listaEditorialesDtoAdmin);

        String listaEditorialesDtoAdminJson = new ObjectMapper().writeValueAsString(listaEditorialesDtoAdmin);

        mockMvc.perform(get("/editoriales/findAll/admin/altaFalse").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaEditorialesDtoAdminJson));

        verify(editorialServicio, times(1)).findAllEditorialesAdminByAltaFalse();
    }

    @Test
    void findAllEditorialesPublicTest() throws Exception {
        List<EditorialDtoPublic> listaEditorialesDtoPublic = Collections.singletonList(editorialDtoPublic);

        when(editorialServicio.findAllEditorialesPublic()).thenReturn(listaEditorialesDtoPublic);

        String listaEditorialesDtoPublicJson = new ObjectMapper().writeValueAsString(listaEditorialesDtoPublic);

        mockMvc.perform(get("/editoriales/findAll/public").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listaEditorialesDtoPublicJson));

        verify(editorialServicio, times(1)).findAllEditorialesPublic();
    }

    @Test
    void findEditorialAdminByNombreTest() throws Exception {
        when(editorialServicio.findEditorialAdminByNombre(editorial.getNombre())).thenReturn(editorialDtoAdmin);

        mockMvc.perform(get("/editoriales/find/admin/nombre/{nombre}", editorial.getNombre())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(editorial.getNombre()));

        verify(editorialServicio, times(1)).findEditorialAdminByNombre(editorial.getNombre());
    }

    @Test
    void findEditorialPublicByNombreTest() throws Exception {
        when(editorialServicio.findEditorialPublicByNombre(editorial.getNombre())).thenReturn(editorialDtoPublic);

        mockMvc.perform(get("/editoriales/find/public/nombre/{nombre}", editorial.getNombre())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(editorial.getNombre()));

        verify(editorialServicio, times(1)).findEditorialPublicByNombre(editorial.getNombre());
    }

    @Test
    void saveEditorialTest() throws Exception {
        EditorialDto editorialDto = MapperEditorial.toDto(editorial);
        String mensaje = "Editorial guardada exitosamente";

        when(editorialServicio.saveEditorial(editorialDto)).thenReturn(mensaje);

        mockMvc.perform(post("/editoriales/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(editorialDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(mensaje));

        verify(editorialServicio, times(1)).saveEditorial(editorialDto);
    }

    @Test
    void updateEditorialTest() throws Exception {
        EditorialDto editorialDto = new EditorialDto();

        editorialDto.setNombre("Nuevo nombre editorial");

        String mensaje = "Editorial actualizada exitosamente";

        when(editorialServicio.updateEditorial(editorial.getNombre(), editorialDto)).thenReturn(mensaje);

        mockMvc.perform(put("/editoriales/update/nombre/{nombre}", editorial.getNombre())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(editorialDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(editorialServicio, times(1)).updateEditorial(editorial.getNombre(), editorialDto);
    }

    @Test
    void highEditorialTest() throws Exception {
        editorial.setAlta(false);

        String mensaje = "Editorial dada de alta exitosamente";

        when(editorialServicio.highEditorial(editorial.getNombre())).thenReturn(mensaje);

        mockMvc.perform(put("/editoriales/high/nombre/{nombre}", editorial.getNombre())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(editorialServicio, times(1)).highEditorial(editorial.getNombre());
    }

    @Test
    void lowEditorialTest() throws Exception {
        String mensaje = "Editorial dada de baja exitosamente";

        when(editorialServicio.lowEditorial(editorial.getNombre())).thenReturn(mensaje);

        mockMvc.perform(put("/editoriales/low/nombre/{nombre}", editorial.getNombre())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(editorialServicio, times(1)).lowEditorial(editorial.getNombre());
    }

    @Test
    void deleteEditorialTest() throws Exception {
        editorial.getLibros().clear();

        String mensaje = "Editorial eliminada exitosamente";

        when(editorialServicio.deleteEditorial(editorial.getNombre())).thenReturn(mensaje);

        mockMvc.perform(delete("/editoriales/delete/nombre/{nombre}", editorial.getNombre())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mensaje));

        verify(editorialServicio, times(1)).deleteEditorial(editorial.getNombre());
    }
}