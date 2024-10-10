package com.example.libreria.service;

import com.example.libreria.dto.AutorDto;
import com.example.libreria.dto.AutorDtoAdmin;
import com.example.libreria.dto.AutorDtoPublic;
import com.example.libreria.exception.AutorEncontradoException;
import com.example.libreria.exception.AutorNoEncontradoException;
import com.example.libreria.mapper.MapperAutor;
import com.example.libreria.model.Autor;
import com.example.libreria.model.Libro;
import com.example.libreria.repository.AutorRepositorio;
import com.example.libreria.repository.EditorialRepositorio;
import com.example.libreria.repository.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class AutorServicio implements AutorService {
    private final AutorRepositorio autorRepositorio;
    private final EditorialRepositorio editorialRepositorio;
    private final LibroRepositorio libroRepositorio;

    @Override
    public List<AutorDtoAdmin> findAllAutoresAdmin() {
        return autorRepositorio.findAll().stream().map(MapperAutor::toDtoAdmin).collect(Collectors.toList());
    }

    @Override
    public List<AutorDtoAdmin> findAllAutoresAdminByAltaTrue() {
        return autorRepositorio.findAllByAltaTrue().stream().map(MapperAutor::toDtoAdmin).collect(Collectors.toList());
    }

    @Override
    public List<AutorDtoAdmin> findAllAutoresAdminByAltaFalse() {
        return autorRepositorio.findAllByAltaFalse().stream().map(MapperAutor::toDtoAdmin).collect(Collectors.toList());
    }

    @Override
    public List<AutorDtoPublic> findAllAutoresPublic() {
        return autorRepositorio.findAllByAltaTrue().stream().map(MapperAutor::toDtoPublic).collect(Collectors.toList());
    }

    @Override
    public AutorDtoAdmin findAutorAdminByNombre(String nombre) {
        Autor autor = autorRepositorio.findByNombre(nombre).orElseThrow(AutorNoEncontradoException::new);

        return MapperAutor.toDtoAdmin(autor);
    }

    @Override
    public AutorDtoPublic findAutorPublicByNombre(String nombre) {
        Autor autor = autorRepositorio.findByNombreAndAltaTrue(nombre).orElseThrow(AutorNoEncontradoException::new);

        return MapperAutor.toDtoPublic(autor);
    }

    @Override
    public String saveAutor(AutorDto autorDto) {
        Optional<Autor> autor = autorRepositorio.findByNombre(autorDto.getNombre());

        if (autor.isPresent()) {
            throw new AutorEncontradoException();
        }

        autorRepositorio.save(MapperAutor.toEntity(autorDto));

        return "Autor guardado exitosamente";
    }

    @Override
    public String updateAutor(String nombre, AutorDto autorDto) {
        Autor autor = autorRepositorio.findByNombre(nombre).orElseThrow(AutorNoEncontradoException::new);
        Optional<Autor> newAutor = autorRepositorio.findByNombre(autorDto.getNombre());

        if (newAutor.isPresent()) {
            throw new AutorEncontradoException();
        }

        autor.setNombre(autorDto.getNombre());
        autorRepositorio.save(autor);

        return "Autor actualizado exitosamente";
    }

    @Override
    public String highAutor(String nombre) {
        Autor autor = autorRepositorio.findByNombreAndAltaFalse(nombre).orElseThrow(AutorNoEncontradoException::new);

        autor.setAlta(true);
        autorRepositorio.save(autor);

        for (Libro libro : autor.getLibros()) {
            if (!libro.getAlta()) {
                libro.setAlta(true);
                libroRepositorio.save(libro);

                for (Autor autorLibro : libro.getAutores()) {
                    if (!autorLibro.getAlta()) {
                        autorLibro.setAlta(true);
                        autorRepositorio.save(autorLibro);
                    }
                }

                if (!libro.getEditorial().getAlta()) {
                    libro.getEditorial().setAlta(true);
                    editorialRepositorio.save(libro.getEditorial());
                }
            }
        }

        return "Autor dado de alta exitosamente";
    }

    @Override
    public String lowAutor(String nombre) {
        Autor autor = autorRepositorio.findByNombreAndAltaTrue(nombre).orElseThrow(AutorNoEncontradoException::new);

        for (Libro libro : autor.getLibros()) {
            if (libro.getAlta()) {
                libro.setAlta(false);
                libroRepositorio.save(libro);

                for (Autor autorLibro : libro.getAutores()) {
                    if (!libroRepositorio.existsByAutoresAndAltaTrue(autorLibro)) {
                        autorLibro.setAlta(false);
                        autorRepositorio.save(autorLibro);
                    }
                }

                if (!libroRepositorio.existsByEditorialAndAltaTrue(libro.getEditorial())) {
                    libro.getEditorial().setAlta(false);
                    editorialRepositorio.save(libro.getEditorial());
                }
            }
        }

        return "Autor dado de baja exitosamente";
    }

    @Override
    public String deleteAutor(String nombre) {
        Autor autor = autorRepositorio.findByNombre(nombre).orElseThrow(AutorNoEncontradoException::new);

        if (!autor.getLibros().isEmpty()) {
            throw new IllegalStateException("El autor no se puede eliminar está vinculado a uno o más libros");
        }

        autorRepositorio.delete(autor);

        return "Autor eliminado exitosamente";
    }
}