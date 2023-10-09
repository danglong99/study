package com.example.spring.exception;

import com.example.spring.utils.ErrorDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({CustomException.class})
  public ResponseEntity<CustomErrorResponse> handleApiException(CustomException ex) {
    CustomErrorResponse customException = new CustomErrorResponse();
    customException.setMessage(ex.getMessage());
    customException.setErrorCode(ex.getErrorCode());
    customException.setStatus(ex.getStatus());
    customException.setTimestamp(Instant.now());
    return ResponseEntity.status(ex.getErrorCode()).body(customException);
  }

  @ExceptionHandler({AuthenticationException.class})
  @ResponseBody
  public ResponseEntity<CustomErrorResponse> handleAuthenticationException() {
    CustomErrorResponse customException = new CustomErrorResponse();
    customException.setMessage(ErrorDetail.UNAUTHENTICATE.getMessage());
    customException.setErrorCode(ErrorDetail.UNAUTHENTICATE.getErrorCode());
    customException.setStatus(ErrorDetail.UNAUTHENTICATE.getStatus());
    customException.setTimestamp(Instant.now());
    return ResponseEntity.status(ErrorDetail.UNAUTHENTICATE.getErrorCode()).body(customException);
  }
}
