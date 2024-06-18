package com.dragan.emuson.api.auth.exception;

public class DuplicatedNameException extends BadRequestException {
    public DuplicatedNameException(String message) {
        super(message);
    }

    public DuplicatedNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
