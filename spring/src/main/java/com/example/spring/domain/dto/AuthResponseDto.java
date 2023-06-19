package com.example.spring.domain.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
  private String username;
  private String accessToken;

  public AuthResponseDto() {}

  public AuthResponseDto(String username, String accessToken) {
    this.username = username;
    this.accessToken = accessToken;
  }
}
