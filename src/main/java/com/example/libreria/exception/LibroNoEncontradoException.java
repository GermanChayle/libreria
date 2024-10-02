package com.example.libreria.exception;

public class LibroNoEncontradoException extends RuntimeException {
    public LibroNoEncontradoException() {
        super("El libro no se encuentra en la base de datos");
    }
}