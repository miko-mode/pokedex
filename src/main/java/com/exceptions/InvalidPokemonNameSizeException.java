package com.exceptions;

public class InvalidPokemonNameSizeException extends RuntimeException {
    public InvalidPokemonNameSizeException(String message) {
        super(message);
    }
}
