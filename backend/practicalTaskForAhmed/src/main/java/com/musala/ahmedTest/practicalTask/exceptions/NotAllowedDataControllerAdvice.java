package com.musala.ahmedTest.practicalTask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotAllowedDataControllerAdvice {

    @ResponseBody
    @ExceptionHandler(NotAllowedDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String NotFoundControllerAdvice(NotAllowedDataException ex) {
        return ex.getMessage();
    }
}