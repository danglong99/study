package com.example.spring.utils;

public enum ErrorDetail {
  UNAUTHENTICATE(403, "UNAUTHENTICATE", "User is not authorize"),
  UNAUTHORIZE(401, "UNAUTHORIZE", "User doesn't have permission"),
  S3_CONNECT_FAILED(401, "S3_CONNECT_FAILED", "Can't connect to S3"),
  DOCUMENT_NOT_FOUND(404,"DOCUMENT_NOT_FOUND", "Document can't be found"),
  DATA_NOT_FOUND(404, "DATA_NOT_FOUND", "Data can't be found");

  private final Integer errorCode;
  private final String status;
  private final String message;
  ErrorDetail(Integer errorCode, String status, String message) {
    this.errorCode = errorCode;
    this.status = status;
    this.message = message;
  }

  public Integer getErrorCode() {
    return errorCode;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }
}
