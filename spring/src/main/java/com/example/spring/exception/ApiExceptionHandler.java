package com.example.spring.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.ZoneId;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler({CustomException.class})
  protected ResponseEntity<CustomErrorResponse> handleApiException(CustomException ex) {
    CustomErrorResponse customException = new CustomErrorResponse();
    customException.setMessage(ex.getMessage());
    customException.setErrorCode(ex.getErrorCode());
    customException.setStatus(ex.getStatus());
    customException.setTimestamp(Instant.now());
    return ResponseEntity.status(ex.getErrorCode()).body(customException);
  }
}
