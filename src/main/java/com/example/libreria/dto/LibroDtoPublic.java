package com.example.libreria.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class LibroDtoPublic {
    private Long isbn;
    private String titulo;
    private Integer anio;
    private Integer ejemplaresRestantes;
    private Set<String> autores = new HashSet<>();
    private String editorial;
}