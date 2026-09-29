package com.exceptions;

public class DuplicateAttackEntryException extends RuntimeException {
    public DuplicateAttackEntryException(String message) {
        super(message);
    }
}
