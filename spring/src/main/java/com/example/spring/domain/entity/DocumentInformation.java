package com.example.spring.domain.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("document")
public class DocumentInformation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  String id;
  String name;
  Integer number;
  Boolean available;
  String description;
}
