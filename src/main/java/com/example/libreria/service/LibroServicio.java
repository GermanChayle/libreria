package com.example.libreria.service;

import com.example.libreria.dto.LibroDtoAdmin;
import com.example.libreria.dto.LibroDtoPublic;
import com.example.libreria.exception.LibroEncontradoException;
import com.example.libreria.exception.LibroNoEncontradoException;
import com.example.libreria.mapper.MapperLibro;
import com.example.libreria.model.Autor;
import com.example.libreria.model.Editorial;
import com.example.libreria.model.Libro;
import com.example.libreria.repository.AutorRepositorio;
import com.example.libreria.repository.EditorialRepositorio;
import com.example.libreria.repository.LibroRepositorio;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class LibroServicio implements LibroService {
    private final AutorRepositorio autorRepositorio;
    private final EditorialRepositorio editorialRepositorio;
    private final LibroRepositorio libroRepositorio;

    @Override
    public List<LibroDtoAdmin> findAllLibrosAdmin() {
        return libroRepositorio.findAll().stream().map(MapperLibro::toDtoAdmin).collect(Collectors.toList());
    }

    @Override
    public List<LibroDtoAdmin> findAllLibrosAdminByAltaTrue() {
        return libroRepositorio.findAllByAltaTrue().stream().map(MapperLibro::toDtoAdmin).collect(Collectors.toList());
    }

    @Override
    public List<LibroDtoAdmin> findAllLibrosAdminByAltaFalse() {
        return libroRepositorio.findAllByAltaFalse().stream().map(MapperLibro::toDtoAdmin).collect(Collectors.toList());
    }

    @Override
    public List<LibroDtoPublic> findAllLibrosPublic() {
        return libroRepositorio.findAllByAltaTrue().stream().map(MapperLibro::toDtoPublic).collect(Collectors.toList());
    }

    @Override
    public List<LibroDtoPublic> findAllLibrosPublicByAnio(Integer anio) {
        List<Libro> listaLibros = libroRepositorio.findAllByAnioAndAltaTrue(anio);

        return listaLibros.stream().map(MapperLibro::toDtoPublic).collect(Collectors.toList());
    }

    @Override
    public LibroDtoAdmin findLibroAdminByIsbn(Long isbn) {
        Libro libro = libroRepositorio.findByIsbn(isbn).orElseThrow(LibroNoEncontradoException::new);

        return MapperLibro.toDtoAdmin(libro);
    }

    @Override
    public LibroDtoPublic findLibroPublicByIsbn(Long isbn) {
        Libro libro = libroRepositorio.findByIsbnAndAltaTrue(isbn).orElseThrow(LibroNoEncontradoException::new);

        return MapperLibro.toDtoPublic(libro);
    }

    @Override
    public LibroDtoAdmin findLibroAdminByTitulo(String titulo) {
        Libro libro = libroRepositorio.findByTitulo(titulo).orElseThrow(LibroNoEncontradoException::new);

        return MapperLibro.toDtoAdmin(libro);
    }

    @Override
    public LibroDtoPublic findLibroPublicByTitulo(String titulo) {
        Libro libro = libroRepositorio.findByTituloAndAltaTrue(titulo).orElseThrow(LibroNoEncontradoException::new);

        return MapperLibro.toDtoPublic(libro);
    }

    @Override
    public String saveLibro(LibroDtoAdmin libroDtoAdmin) {
        Optional<Libro> libro = libroRepositorio.findByIsbn(libroDtoAdmin.getIsbn());

        if (libro.isPresent()) {
            throw new LibroEncontradoException();
        }

        Libro newLibro = MapperLibro.toEntity(libroDtoAdmin);
        Set<Autor> autores = new HashSet<>();

        for (Autor newAutor : newLibro.getAutores()) {
            Optional<Autor> autor = autorRepositorio.findByNombre(newAutor.getNombre());

            if (autor.isPresent()) {
                if (!autor.get().getAlta()) {
                    autor.get().setAlta(true);
                    autorRepositorio.save(autor.get());
                }

                autores.add(autor.get());
            } else {
                autores.add(autorRepositorio.save(newAutor));
            }
        }

        newLibro.setAutores(autores);

        Optional<Editorial> editorial = editorialRepositorio.findByNombre(newLibro.getEditorial().getNombre());

        if (editorial.isPresent()) {
            if (!editorial.get().getAlta()) {
                editorial.get().setAlta(true);
                editorialRepositorio.save(editorial.get());
            }

            newLibro.setEditorial(editorial.get());
        } else {
            newLibro.setEditorial(editorialRepositorio.save(newLibro.getEditorial()));
        }

        libroRepositorio.save(newLibro);

        return "Libro guardado exitosamente";
    }

    @Override
    public String updateLibro(LibroDtoAdmin libroDtoAdmin) {
        Libro libro = libroRepositorio.findByIsbn(libroDtoAdmin.getIsbn()).orElseThrow(LibroNoEncontradoException::new);

        libro.setTitulo(libroDtoAdmin.getTitulo());
        libro.setAnio(libroDtoAdmin.getAnio());
        libro.setEjemplares(libroDtoAdmin.getEjemplares());
        libro.setEjemplaresPrestados(libroDtoAdmin.getEjemplaresPrestados());
        libro.setEjemplaresRestantes(libroDtoAdmin.getEjemplaresRestantes());
        libroRepositorio.save(libro);

        return "Libro actualizado exitosamente";
    }

    @Override
    public String lendLibro(Long isbn) {
        Libro libro = libroRepositorio.findByIsbnAndAltaTrue(isbn).orElseThrow(LibroNoEncontradoException::new);

        if (libro.getEjemplaresRestantes() == 0) {
            throw new IllegalStateException("No hay ejemplares disponibles para prestar");
        }

        libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - 1);
        libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + 1);
        libroRepositorio.save(libro);

        return "Libro prestado exitosamente";
    }

    @Override
    public String returnLibro(Long isbn) {
        Libro libro = libroRepositorio.findByIsbnAndAltaTrue(isbn).orElseThrow(LibroNoEncontradoException::new);

        if (libro.getEjemplaresPrestados() == 0) {
            throw new IllegalStateException("No hay ejemplares prestados para devolver");
        }

        libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() - 1);
        libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() + 1);
        libroRepositorio.save(libro);

        return "Libro devuelto exitosamente";
    }

    @Override
    public String highLibro(Long isbn) {
        Libro libro = libroRepositorio.findByIsbnAndAltaFalse(isbn).orElseThrow(LibroNoEncontradoException::new);

        libro.setAlta(true);
        libroRepositorio.save(libro);

        for (Autor autor : libro.getAutores()) {
            if (!autor.getAlta()) {
                autor.setAlta(true);
                autorRepositorio.save(autor);
            }
        }

        if (!libro.getEditorial().getAlta()) {
            libro.getEditorial().setAlta(true);
            editorialRepositorio.save(libro.getEditorial());
        }

        return "Libro dado de alta exitosamente";
    }

    @Override
    public String lowLibro(Long isbn) {
        Libro libro = libroRepositorio.findByIsbnAndAltaTrue(isbn).orElseThrow(LibroNoEncontradoException::new);

        libro.setAlta(false);
        libroRepositorio.save(libro);

        for (Autor autor : libro.getAutores()) {
            if (!libroRepositorio.existsByAutoresAndAltaTrue(autor)) {
                autor.setAlta(false);
                autorRepositorio.save(autor);
            }
        }

        if (!libroRepositorio.existsByEditorialAndAltaTrue(libro.getEditorial())) {
            libro.getEditorial().setAlta(false);
            editorialRepositorio.save(libro.getEditorial());
        }

        return "Libro dado de baja exitosamente";
    }

    @Override
    public String deleteLibro(Long isbn) {
        Libro libro = libroRepositorio.findByIsbn(isbn).orElseThrow(LibroNoEncontradoException::new);

        Hibernate.initialize(libro.getAutores());
        Hibernate.initialize(libro.getEditorial());
        libroRepositorio.delete(libro);

        for (Autor autor : libro.getAutores()) {
            if (!libroRepositorio.existsByAutoresAndAltaTrue(autor)) {
                autorRepositorio.delete(autor);
            }
        }

        if (!libroRepositorio.existsByEditorialAndAltaTrue(libro.getEditorial())) {
            editorialRepositorio.delete(libro.getEditorial());
        }

        return "Libro eliminado exitosamente";
    }
}