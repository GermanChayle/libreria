package com.example.libreria.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class LibroDtoAdmin {
    @NotNull(message = "El ISBN no puede ser nulo")
    @Min(value = 1000000000000L, message = "El ISBN debe tener al menos 13 dígitos")
    @Max(value = 9999999999999L, message = "El ISBN debe tener como máximo 13 dígitos")
    private Long isbn;

    @NotNull(message = "El título no puede ser nulo")
    @Size(min = 1, max = 255, message = "El título debe tener entre 1 y 255 caracteres")
    private String titulo;

    @NotNull(message = "El año no puede ser nulo")
    @Min(value = 1450, message = "El año debe ser posterior a la invención de la imprenta (1450)")
    @Max(value = 2024, message = "El año no puede ser posterior al actual")
    private Integer anio;

    @NotNull(message = "El número de ejemplares no puede ser nulo")
    @Min(value = 1, message = "Debe haber al menos un ejemplar disponible")
    private Integer ejemplares;

    @NotNull(message = "El número de ejemplares prestados no puede ser nulo")
    @Min(value = 0, message = "El número de ejemplares prestados no puede ser negativo")
    private Integer ejemplaresPrestados;

    @NotNull(message = "El número de ejemplares restantes no puede ser nulo")
    @Min(value = 0, message = "El número de ejemplares restantes no puede ser negativo")
    private Integer ejemplaresRestantes;

    @NotNull(message = "El estado de alta no puede ser nulo")
    private Boolean alta;

    @NotNull(message = "Los autores no pueden ser nulos")
    @Size(min = 1, message = "Debe haber al menos un autor")
    @Valid
    private Set<AutorDto> autores = new HashSet<>();

    @NotNull(message = "La editorial no puede ser nula")
    @Valid
    private EditorialDto editorial;
}