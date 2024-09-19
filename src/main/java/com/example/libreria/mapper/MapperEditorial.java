package com.example.libreria.mapper;

import com.example.libreria.dto.EditorialDto;
import com.example.libreria.dto.EditorialDtoAdmin;
import com.example.libreria.dto.EditorialDtoPublic;
import com.example.libreria.model.Editorial;
import com.example.libreria.model.Libro;
import java.util.stream.Collectors;

public class MapperEditorial {
    public static EditorialDto toDto(Editorial editorial) {
        EditorialDto editorialDto = new EditorialDto();

        editorialDto.setNombre(editorial.getNombre());
        editorialDto.setAlta(editorial.getAlta());

        return editorialDto;
    }

    public static EditorialDtoAdmin toDtoAdmin(Editorial editorial) {
        EditorialDtoAdmin editorialDtoAdmin = new EditorialDtoAdmin();

        editorialDtoAdmin.setNombre(editorial.getNombre());
        editorialDtoAdmin.setAlta(editorial.getAlta());
        editorialDtoAdmin.setLibros(editorial.getLibros().stream().map(MapperLibro::toDto).collect(Collectors.toSet()));

        return editorialDtoAdmin;
    }

    public static EditorialDtoPublic toDtoPublic(Editorial editorial) {
        EditorialDtoPublic editorialDtoPublic = new EditorialDtoPublic();

        editorialDtoPublic.setNombre(editorial.getNombre());
        editorialDtoPublic.setLibros(editorial.getLibros().stream().map(Libro::getTitulo).collect(Collectors.toSet()));

        return editorialDtoPublic;
    }

    public static Editorial toEntity(EditorialDto editorialDto) {
        Editorial editorial = new Editorial();

        editorial.setNombre(editorialDto.getNombre());
        editorial.setAlta(editorialDto.getAlta());

        return editorial;
    }
}