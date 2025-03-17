package com.zeroblog.zerolog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class zerologException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();


    public zerologException(String message) {
        super(message);
    }

    public zerologException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
