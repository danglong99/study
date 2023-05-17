package com.example.spring.domain.dto;

import lombok.Data;

@Data
public class DataResponseDto {
  String name;
  Integer number;
  Boolean available;
  String description;
}
