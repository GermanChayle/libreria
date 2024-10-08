package com.example.libreria.service;

import com.example.libreria.dto.AutorDto;
import com.example.libreria.dto.AutorDtoAdmin;
import com.example.libreria.dto.AutorDtoPublic;
import java.util.List;

public interface AutorService {
    List<AutorDtoAdmin> findAllAutoresAdmin();
    List<AutorDtoAdmin> findAllAutoresAdminByAltaTrue();
    List<AutorDtoAdmin> findAllAutoresAdminByAltaFalse();
    List<AutorDtoPublic> findAllAutoresPublic();
    AutorDtoAdmin findAutorAdminByNombre(String nombre);
    AutorDtoPublic findAutorPublicByNombre(String nombre);
    String saveAutor(AutorDto autorDto);
    String updateAutor(String nombre, AutorDto autorDto);
    String highAutor(String nombre);
    String lowAutor(String nombre);
    String deleteAutor(String nombre);
}