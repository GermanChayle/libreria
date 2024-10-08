package com.example.libreria.repository;

import com.example.libreria.model.Editorial;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EditorialRepositorio extends JpaRepository<Editorial, Long> {
    List<Editorial> findAllByAltaTrue();
    List<Editorial> findAllByAltaFalse();
    Optional<Editorial> findByNombre(String nombre);
    Optional<Editorial> findByNombreAndAltaTrue(String nombre);
    Optional<Editorial> findByNombreAndAltaFalse(String nombre);
}