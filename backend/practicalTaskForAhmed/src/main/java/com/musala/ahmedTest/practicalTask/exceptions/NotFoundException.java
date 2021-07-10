package com.musala.ahmedTest.practicalTask.exceptions;

public class NotFoundException extends  RuntimeException  {
 
    private static final long serialVersionUID = -3606509494328908116L;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
