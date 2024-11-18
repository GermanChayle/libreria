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

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Boolean alta;

    @ManyToMany(mappedBy = "autores")
    @JsonIgnoreProperties("autores")
    private Set<Libro> libros = new HashSet<>();
}