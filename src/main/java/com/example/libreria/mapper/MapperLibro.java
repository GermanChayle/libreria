package com.example.libreria.mapper;

import com.example.libreria.dto.LibroDto;
import com.example.libreria.dto.LibroDtoAdmin;
import com.example.libreria.dto.LibroDtoPublic;
import com.example.libreria.model.Autor;
import com.example.libreria.model.Libro;
import java.util.stream.Collectors;

public class MapperLibro {
    public static LibroDto toDto(Libro libro) {
        LibroDto libroDto = new LibroDto();

        libroDto.setTitulo(libro.getTitulo());
        libroDto.setAlta(libro.getAlta());

        return libroDto;
    }

    public static LibroDtoAdmin toDtoAdmin(Libro libro) {
        LibroDtoAdmin libroDtoAdmin = new LibroDtoAdmin();

        libroDtoAdmin.setIsbn(libro.getIsbn());
        libroDtoAdmin.setTitulo(libro.getTitulo());
        libroDtoAdmin.setAnio(libro.getAnio());
        libroDtoAdmin.setEjemplares(libro.getEjemplares());
        libroDtoAdmin.setEjemplaresPrestados(libro.getEjemplaresPrestados());
        libroDtoAdmin.setEjemplaresRestantes(libro.getEjemplaresRestantes());
        libroDtoAdmin.setAlta(libro.getAlta());
        libroDtoAdmin.setAutores(libro.getAutores().stream().map(MapperAutor::toDto).collect(Collectors.toSet()));
        libroDtoAdmin.setEditorial(MapperEditorial.toDto(libro.getEditorial()));

        return libroDtoAdmin;
    }

    public static LibroDtoPublic toDtoPublic(Libro libro) {
        LibroDtoPublic libroDtoPublic = new LibroDtoPublic();

        libroDtoPublic.setIsbn(libro.getIsbn());
        libroDtoPublic.setTitulo(libro.getTitulo());
        libroDtoPublic.setAnio(libro.getAnio());
        libroDtoPublic.setEjemplaresRestantes(libro.getEjemplaresRestantes());
        libroDtoPublic.setAutores(libro.getAutores().stream().map(Autor::getNombre).collect(Collectors.toSet()));
        libroDtoPublic.setEditorial(libro.getEditorial().getNombre());

        return libroDtoPublic;
    }

    public static Libro toEntity(LibroDtoAdmin libroDtoAdmin) {
        Libro libro = new Libro();

        libro.setIsbn(libroDtoAdmin.getIsbn());
        libro.setTitulo(libroDtoAdmin.getTitulo());
        libro.setAnio(libroDtoAdmin.getAnio());
        libro.setEjemplares(libroDtoAdmin.getEjemplares());
        libro.setEjemplaresPrestados(libroDtoAdmin.getEjemplaresPrestados());
        libro.setEjemplaresRestantes(libroDtoAdmin.getEjemplaresRestantes());
        libro.setAlta(libroDtoAdmin.getAlta());
        libro.setAutores(libroDtoAdmin.getAutores().stream().map(MapperAutor::toEntity).collect(Collectors.toSet()));
        libro.setEditorial(MapperEditorial.toEntity(libroDtoAdmin.getEditorial()));

        return libro;
    }
}