package com.example.spring.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class CustomErrorResponse {

  private final Integer errorCode;
  private final HttpStatus status;
  private final String message;
  private final Instant timestamp;

  public Integer getErrorCode() {
    return errorCode;
  }
  public HttpStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public CustomErrorResponse(Integer errorCode, HttpStatus status, String message, Instant timestamp) {
    this.errorCode = errorCode;
    this.status = status;
    this.message = message;
    this.timestamp = timestamp;
  }
}
