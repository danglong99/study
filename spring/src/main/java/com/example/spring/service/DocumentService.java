package com.example.spring.service;

import com.example.spring.domain.dto.DocumentRequestDto;
import com.example.spring.domain.dto.DocumentResponseDto;

import java.util.List;

public interface DocumentService {
  DocumentResponseDto getById(String id);

  List<DocumentResponseDto> getAll();

  DocumentResponseDto create(DocumentRequestDto documentRequestDto);

  DocumentResponseDto update(String id, DocumentRequestDto documentRequestDto);

  void delete(String id);
}
