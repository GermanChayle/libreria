package com.example.libreria.repository;

import com.example.libreria.model.Autor;
import com.example.libreria.model.Editorial;
import com.example.libreria.model.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    List<Libro> findByAltaTrue();
    List<Libro> findByAltaFalse();
    List<Libro> findByAnioAndAltaTrue(Integer anio);
    Optional<Libro> findByIsbn(Long isbn);
    Optional<Libro> findByIsbnAndAltaTrue(Long isbn);
    Optional<Libro> findByIsbnAndAltaFalse(Long isbn);
    Optional<Libro> findByTitulo(String titulo);
    Optional<Libro> findByTituloAndAltaTrue(String titulo);
    boolean existsByAutoresAndAltaTrue(Autor autor);
    boolean existsByEditorialAndAltaTrue(Editorial editorial);
}