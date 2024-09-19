package com.example.libreria.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class EditorialDtoPublic {
    private String nombre;
    private Set<String> libros = new HashSet<>();
}