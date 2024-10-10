package com.example.libreria.service;

import com.example.libreria.dto.EditorialDto;
import com.example.libreria.dto.EditorialDtoAdmin;
import com.example.libreria.dto.EditorialDtoPublic;
import java.util.List;

public interface EditorialService {
    List<EditorialDtoAdmin> findAllEditorialesAdmin();
    List<EditorialDtoAdmin> findAllEditorialesAdminByAltaTrue();
    List<EditorialDtoAdmin> findAllEditorialesAdminByAltaFalse();
    List<EditorialDtoPublic> findAllEditorialesPublic();
    EditorialDtoAdmin findEditorialAdminByNombre(String nombre);
    EditorialDtoPublic findEditorialPublicByNombre(String nombre);
    String saveEditorial(EditorialDto editorialDto);
    String updateEditorial(String nombre, EditorialDto editorialDto);
    String highEditorial(String nombre);
    String lowEditorial(String nombre);
    String deleteEditorial(String nombre);
}