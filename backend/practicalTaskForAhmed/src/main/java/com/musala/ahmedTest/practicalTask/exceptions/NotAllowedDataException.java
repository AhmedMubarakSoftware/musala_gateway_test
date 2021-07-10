package com.musala.ahmedTest.practicalTask.exceptions;


public class NotAllowedDataException extends Exception {

    private static final long serialVersionUID = 268266244879822L;

    public NotAllowedDataException(String message) {
        super(message);
    }

    public NotAllowedDataException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
