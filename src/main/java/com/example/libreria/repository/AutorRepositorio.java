package com.example.libreria.repository;

import com.example.libreria.model.Autor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AutorRepositorio extends JpaRepository<Autor, Long> {
    List<Autor> findAllByAltaTrue();
    List<Autor> findAllByAltaFalse();
    Optional<Autor> findByNombre(String nombre);
    Optional<Autor> findByNombreAndAltaTrue(String nombre);
    Optional<Autor> findByNombreAndAltaFalse(String nombre);
}