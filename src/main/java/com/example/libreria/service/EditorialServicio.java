package com.example.libreria.service;

import com.example.libreria.dto.EditorialDto;
import com.example.libreria.dto.EditorialDtoAdmin;
import com.example.libreria.dto.EditorialDtoPublic;
import com.example.libreria.exception.EditorialEncontradaException;
import com.example.libreria.exception.EditorialNoEncontradaException;
import com.example.libreria.mapper.MapperEditorial;
import com.example.libreria.model.Autor;
import com.example.libreria.model.Editorial;
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

public class EditorialServicio implements EditorialService {
    private final AutorRepositorio autorRepositorio;
    private final EditorialRepositorio editorialRepositorio;
    private final LibroRepositorio libroRepositorio;

    @Override
    public List<EditorialDtoAdmin> findAllEditorialesAdmin() {
        return editorialRepositorio.findAll().stream().map(MapperEditorial::toDtoAdmin).collect(Collectors.toList());
    }

    @Override
    public List<EditorialDtoAdmin> findAllEditorialesAdminByAltaTrue() {
        List<Editorial> listaEditoriales = editorialRepositorio.findAllByAltaTrue();

        return listaEditoriales.stream().map(MapperEditorial::toDtoAdmin).collect(Collectors.toList());
    }

    @Override
    public List<EditorialDtoAdmin> findAllEditorialesAdminByAltaFalse() {
        List<Editorial> listaEditoriales = editorialRepositorio.findAllByAltaFalse();

        return listaEditoriales.stream().map(MapperEditorial::toDtoAdmin).collect(Collectors.toList());
    }

    @Override
    public List<EditorialDtoPublic> findAllEditorialesPublic() {
        List<Editorial> listaEditoriales = editorialRepositorio.findAllByAltaTrue();

        return listaEditoriales.stream().map(MapperEditorial::toDtoPublic).collect(Collectors.toList());
    }

    @Override
    public EditorialDtoAdmin findEditorialAdminByNombre(String nombre) {
        Editorial editorial = editorialRepositorio.findByNombre(nombre)
                                                  .orElseThrow(EditorialNoEncontradaException::new);

        return MapperEditorial.toDtoAdmin(editorial);
    }

    @Override
    public EditorialDtoPublic findEditorialPublicByNombre(String nombre) {
        Editorial editorial = editorialRepositorio.findByNombreAndAltaTrue(nombre)
                                                  .orElseThrow(EditorialNoEncontradaException::new);

        return MapperEditorial.toDtoPublic(editorial);
    }

    @Override
    public String saveEditorial(EditorialDto editorialDto) {
        Optional<Editorial> editorial = editorialRepositorio.findByNombre(editorialDto.getNombre());

        if (editorial.isPresent()) {
            throw new EditorialEncontradaException();
        }

        editorialRepositorio.save(MapperEditorial.toEntity(editorialDto));

        return "Editorial guardada exitosamente";
    }

    @Override
    public String updateEditorial(String nombre, EditorialDto editorialDto) {
        Editorial editorial = editorialRepositorio.findByNombre(nombre)
                                                  .orElseThrow(EditorialNoEncontradaException::new);
        Optional<Editorial> newEditorial = editorialRepositorio.findByNombre(editorialDto.getNombre());

        if (newEditorial.isPresent()) {
            throw new EditorialEncontradaException();
        }

        editorial.setNombre(editorialDto.getNombre());
        editorialRepositorio.save(editorial);

        return "Editorial actualizada exitosamente";
    }

    @Override
    public String highEditorial(String nombre) {
        Editorial editorial = editorialRepositorio.findByNombreAndAltaFalse(nombre)
                                                  .orElseThrow(EditorialNoEncontradaException::new);

        editorial.setAlta(true);
        editorialRepositorio.save(editorial);

        for (Libro libro : editorial.getLibros()) {
            if (!libro.getAlta()) {
                libro.setAlta(true);
                libroRepositorio.save(libro);

                for (Autor autorLibro : libro.getAutores()) {
                    if (!autorLibro.getAlta()) {
                        autorLibro.setAlta(true);
                        autorRepositorio.save(autorLibro);
                    }
                }
            }
        }

        return "Editorial dada de alta exitosamente";
    }

    @Override
    public String lowEditorial(String nombre) {
        Editorial editorial = editorialRepositorio.findByNombreAndAltaTrue(nombre)
                                                  .orElseThrow(EditorialNoEncontradaException::new);

        for (Libro libro : editorial.getLibros()) {
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

        return "Editorial dada de baja exitosamente";
    }

    @Override
    public String deleteEditorial(String nombre) {
        Editorial editorial = editorialRepositorio.findByNombre(nombre)
                                                  .orElseThrow(EditorialNoEncontradaException::new);

        if (!editorial.getLibros().isEmpty()) {
            throw new IllegalStateException("La editorial no se puede eliminar está vinculada a uno o más libros");
        }

        editorialRepositorio.delete(editorial);

        return "Editorial eliminada exitosamente";
    }
}