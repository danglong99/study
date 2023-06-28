package com.example.spring.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {

  private Integer errorCode;
  private String status;
  private String message;
  private Instant timestamp;

  public Integer getErrorCode() {
    return errorCode;
  }
  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setErrorCode(Integer errorCode) {
    this.errorCode = errorCode;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }
}
