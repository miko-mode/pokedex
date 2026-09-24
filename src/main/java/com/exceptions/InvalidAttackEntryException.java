package com.exceptions;

public class InvalidAttackEntryException extends RuntimeException {
    public InvalidAttackEntryException(String message) {
        super(message);
    }
}
