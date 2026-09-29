package com.exceptions;

public class AttackNotFoundException extends RuntimeException {
    public AttackNotFoundException(String message) {
        super(message);
    }
}
