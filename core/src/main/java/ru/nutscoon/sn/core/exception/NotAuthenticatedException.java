package ru.nutscoon.sn.core.exception;

public class NotAuthenticatedException extends RuntimeException {
    public NotAuthenticatedException() {
    }

    public NotAuthenticatedException(String message) {
        super(message);
    }
}
