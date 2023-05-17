package com.example.spring.domain.dto;

import lombok.Data;

@Data
public class DataRequestDto {
  String name;
  Integer number;
  Boolean available;
  String description;
}
