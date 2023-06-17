package com.example.spring.exception;


import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

  private final HttpStatus status;

  private final Integer errorCode;

  public CustomException(Integer errorCode, HttpStatus status, String message) {
    super(message);
    this.errorCode = errorCode;
    this.status = status;
  }

  public Integer getErrorCode() {
    return this.errorCode;
  }

  public HttpStatus getStatus() {
    return this.status;
  }
}
