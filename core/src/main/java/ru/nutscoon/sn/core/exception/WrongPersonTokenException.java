package ru.nutscoon.sn.core.exception;

public class WrongPersonTokenException extends RuntimeException {
    public WrongPersonTokenException() {
    }

    public WrongPersonTokenException(String message) {
        super(message);
    }
}
