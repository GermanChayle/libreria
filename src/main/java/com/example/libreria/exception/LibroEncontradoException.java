package com.example.libreria.exception;

public class LibroEncontradoException extends RuntimeException {
    public LibroEncontradoException() {
        super("El libro ya se encuentra en la base de datos");
    }
}