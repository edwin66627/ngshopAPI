package com.ngshop.exception.domain;

public class UnsupportedContentTypeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnsupportedContentTypeException(String message) {
        super(message);
    }
}
