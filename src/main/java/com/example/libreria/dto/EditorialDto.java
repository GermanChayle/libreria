package com.example.libreria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class EditorialDto {
    private String nombre;
    private Boolean alta;
}