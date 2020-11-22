package com.audibene.covidalert.controller;

import com.audibene.covidalert.controller.exception.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handleException(Exception exception) {
        if (exception instanceof NotFoundException) {
            return buildErrorResponse(404, "Not found");
        }

        return buildErrorResponse(500, "Unknown error");
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Integer code, String message) {
        return ResponseEntity
                .status(code)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(message));
    }
}
