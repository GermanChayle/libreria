package com.example.libreria.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class EditorialDtoAdmin {
    private String nombre;
    private Boolean alta;
    private Set<LibroDto> libros = new HashSet<>();
}