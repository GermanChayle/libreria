package com.example.libreria.exception;

public class EditorialEncontradaException extends RuntimeException {
    public EditorialEncontradaException() {
        super("La editorial ya se encuentra en la base de datos");
    }
}