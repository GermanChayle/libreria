package com.example.libreria.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.*;

@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Setter

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long isbn;

    private String titulo;

    private Integer anio;

    private Integer ejemplares;

    private Integer ejemplaresPrestados;

    private Integer ejemplaresRestantes;

    private Boolean alta;

    @ManyToMany
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    @JsonIgnoreProperties("libros")
    private Set<Autor> autores = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "editorial_id")
    @JsonIgnoreProperties("libros")
    private Editorial editorial;
}