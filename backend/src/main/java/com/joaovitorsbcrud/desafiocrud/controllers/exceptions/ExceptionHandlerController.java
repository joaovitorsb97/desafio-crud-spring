package com.joaovitorsbcrud.desafiocrud.controllers.exceptions;

import com.joaovitorsbcrud.desafiocrud.services.exceptions.DatabaseException;
import com.joaovitorsbcrud.desafiocrud.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest request){
        StandardError standardError = new StandardError(Instant.now(),
                                                HttpStatus.NOT_FOUND.value(),
                                                "Resource not found",
                                                exception.getMessage(),
                                                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> databaseError(DatabaseException exception, HttpServletRequest request){
        StandardError standardError = new StandardError(Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Resource not found",
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
    }
}
