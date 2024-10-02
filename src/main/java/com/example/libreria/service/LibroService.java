package com.example.libreria.service;

import com.example.libreria.dto.LibroDtoAdmin;
import com.example.libreria.dto.LibroDtoPublic;
import java.util.List;

public interface LibroService {
    List<LibroDtoAdmin> findAllLibrosAdmin();
    List<LibroDtoAdmin> findAllLibrosAdminByAltaTrue();
    List<LibroDtoAdmin> findAllLibrosAdminByAltaFalse();
    List<LibroDtoPublic> findAllLibrosPublic();
    List<LibroDtoPublic> findAllLibrosPublicByAnio(Integer anio);
    LibroDtoAdmin findLibroAdminByIsbn(Long isbn);
    LibroDtoPublic findLibroPublicByIsbn(Long isbn);
    LibroDtoAdmin findLibroAdminByTitulo(String titulo);
    LibroDtoPublic findLibroPublicByTitulo(String titulo);
    String saveLibro(LibroDtoAdmin libroDtoAdmin);
    String updateLibro(LibroDtoAdmin libroDtoAdmin);
    String lendLibro(Long isbn);
    String returnLibro(Long isbn);
    String highLibro(Long isbn);
    String lowLibro(Long isbn);
    String deleteLibro(Long isbn);
}