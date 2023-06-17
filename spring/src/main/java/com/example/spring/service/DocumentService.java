package com.example.spring.service;

import com.example.spring.domain.dto.DocumentRequestDto;
import com.example.spring.domain.dto.DocumentResponseDto;
import com.example.spring.domain.entity.DocumentInformation;

import java.util.List;

public interface DocumentService {
  DocumentResponseDto getById(String id);

  List<DocumentResponseDto> getAll();

  DocumentInformation create(DocumentRequestDto documentRequestDto);

  DocumentInformation update(String id, DocumentRequestDto documentRequestDto);

  void delete(String id);
}
