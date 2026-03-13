package com.aradhyagupta25.tasks.controllers;

import com.aradhyagupta25.tasks.domain.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice   // tells spring this class handles exception across all controllers.
public class GlobalExceptionHandler {   // will catch exceptions and convert it into appropriate http responses.

    @ExceptionHandler({IllegalArgumentException.class})     // specifies that this method handles illegal argument exception.
    public ResponseEntity<ErrorResponse> handleException(
            RuntimeException ex, WebRequest request
    ){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), //for status
                ex.getMessage(),    // for message
                request.getDescription(false)   // for detail.
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
