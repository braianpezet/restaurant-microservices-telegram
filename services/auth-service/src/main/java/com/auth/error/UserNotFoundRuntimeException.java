package com.auth.error;

public class UserNotFoundRuntimeException extends RuntimeException {

    private String description;

    public UserNotFoundRuntimeException(String description) {
        this.description = description;
    }
}
