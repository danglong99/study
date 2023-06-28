package com.example.spring.exception;


import com.example.spring.utils.ErrorDetail;

public class CustomException extends RuntimeException {

  private final String status;

  private final Integer errorCode;
  private final String message;

  public CustomException(Integer errorCode, String status, String message) {
    super(message);
    this.errorCode = errorCode;
    this.status = status;
    this.message = message;
  }

  public CustomException(ErrorDetail errorDetail) {
    this.errorCode = errorDetail.getErrorCode();
    this.status = errorDetail.getStatus();
    this.message = errorDetail.getMessage();
  }

  public Integer getErrorCode() {
    return this.errorCode;
  }

  public String getStatus() {
    return this.status;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
