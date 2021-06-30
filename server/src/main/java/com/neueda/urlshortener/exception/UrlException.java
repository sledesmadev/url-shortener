package com.neueda.urlshortener.exception;

import com.neueda.urlshortener.error.ErrorMessage;

public class UrlException extends RuntimeException {
    public UrlException(ErrorMessage error) {
        super(error.getMessage());
    }
}
