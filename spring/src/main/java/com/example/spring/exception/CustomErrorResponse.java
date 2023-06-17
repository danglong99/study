package com.example.spring.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class CustomErrorResponse {

  private final HttpStatus status;
  private final String message;
  private final Instant timestamp;

  public HttpStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public CustomErrorResponse(HttpStatus status, String message, Instant timestamp) {
    this.status = status;
    this.message = message;
    this.timestamp = timestamp;
  }
}
