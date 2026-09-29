package com.exceptions;

public class NumberNotInIntervalException extends RuntimeException {
    public NumberNotInIntervalException(String message) {
        super(message);
    }
}
