package com.github.menglim.sutils.exceptions;

public class BackendSuccessException extends RuntimeException {

    private String message;

    public BackendSuccessException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
