package com.exceptions;

public class InvalidPokemonNameFormat extends RuntimeException {
    public InvalidPokemonNameFormat(String message) {
        super(message);
    }
}
