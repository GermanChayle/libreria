package com.example.libreria.exception;

public class AutorEncontradoException extends RuntimeException {
    public AutorEncontradoException() {
        super("El autor ya se encuentra en la base de datos");
    }
}