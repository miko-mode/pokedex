package com.exceptions;

public class DuplicatePokemonException extends RuntimeException {
    public DuplicatePokemonException(String message) {
        super(message);
    }
}
