package com.github.menglim.sutils.exceptions;

public class BackendErrorException extends RuntimeException {

    private String errorMessage;

    public BackendErrorException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
