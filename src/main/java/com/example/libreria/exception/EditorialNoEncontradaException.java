package com.example.libreria.exception;

public class EditorialNoEncontradaException extends RuntimeException {
    public EditorialNoEncontradaException() {
        super("La editorial no se encuentra en la base de datos");
    }
}