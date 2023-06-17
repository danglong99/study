package com.example.spring.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler({CustomException.class})
  protected ResponseEntity<CustomErrorResponse> handleApiException(CustomException ex) {
    return new ResponseEntity<>(new CustomErrorResponse(ex.getStatus(), ex.getMessage(), Instant.now()), ex.getStatus());
  }
}
