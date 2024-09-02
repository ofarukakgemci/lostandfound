package com.ofa.lostandfound.exception;

public class DocumentNotProcessedException extends IllegalArgumentException {
    public DocumentNotProcessedException(String message) {
        super(message);
    }
}