package com.ngshop.exception.domain;

public class FileSizeNotAllowedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileSizeNotAllowedException(String message) {
        super(message);
    }
}
