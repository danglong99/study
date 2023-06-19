package com.example.spring.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequestDto {
  @NotNull
  private String username;

  @NotNull String password;
}
