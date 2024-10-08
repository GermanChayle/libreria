package com.example.libreria.exception;

public class AutorNoEncontradoException extends RuntimeException {
    public AutorNoEncontradoException() {
        super("El autor no se encuentra en la base de datos");
    }
}