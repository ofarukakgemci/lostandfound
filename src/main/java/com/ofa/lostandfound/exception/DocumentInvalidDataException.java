package com.ofa.lostandfound.exception;

public class DocumentInvalidDataException extends IllegalArgumentException {
    public DocumentInvalidDataException() {
        super("Invalid data format");
    }
}