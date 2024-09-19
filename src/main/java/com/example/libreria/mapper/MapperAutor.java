package com.example.libreria.mapper;

import com.example.libreria.dto.AutorDto;
import com.example.libreria.dto.AutorDtoAdmin;
import com.example.libreria.dto.AutorDtoPublic;
import com.example.libreria.model.Autor;
import com.example.libreria.model.Libro;
import java.util.stream.Collectors;

public class MapperAutor {
    public static AutorDto toDto(Autor autor) {
        AutorDto autorDto = new AutorDto();

        autorDto.setNombre(autor.getNombre());
        autorDto.setAlta(autor.getAlta());

        return autorDto;
    }

    public static AutorDtoAdmin toDtoAdmin(Autor autor) {
        AutorDtoAdmin autorDtoAdmin = new AutorDtoAdmin();

        autorDtoAdmin.setNombre(autor.getNombre());
        autorDtoAdmin.setAlta(autor.getAlta());
        autorDtoAdmin.setLibros(autor.getLibros().stream().map(MapperLibro::toDto).collect(Collectors.toSet()));

        return autorDtoAdmin;
    }

    public static AutorDtoPublic toDtoPublic(Autor autor) {
        AutorDtoPublic autorDtoPublic = new AutorDtoPublic();

        autorDtoPublic.setNombre(autor.getNombre());
        autorDtoPublic.setLibros(autor.getLibros().stream().map(Libro::getTitulo).collect(Collectors.toSet()));

        return autorDtoPublic;
    }

    public static Autor toEntity(AutorDto autorDto) {
        Autor autor = new Autor();

        autor.setNombre(autorDto.getNombre());
        autor.setAlta(autorDto.getAlta());

        return autor;
    }
}