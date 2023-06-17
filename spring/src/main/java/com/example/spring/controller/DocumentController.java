package com.example.spring.controller;

import com.example.spring.domain.dto.DocumentRequestDto;
import com.example.spring.domain.dto.DocumentResponseDto;
import com.example.spring.domain.entity.DocumentInformation;
import com.example.spring.other.CustomAnnotation;
import com.example.spring.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {
  @Autowired
  DocumentService documentService;

  @GetMapping("/{id}")
  @CustomAnnotation
  public DocumentResponseDto getById(@PathVariable String id) {
    return documentService.getById(id);
  }

  @GetMapping("")
  public List<DocumentResponseDto> getAll() {
    return documentService.getAll();
  }

  @PostMapping("")
  public DocumentInformation create(@RequestBody DocumentRequestDto documentRequestDto) {
    return documentService.create(documentRequestDto);
  }

  @PutMapping("/{id}")
  @CustomAnnotation
  public DocumentInformation update(@RequestBody DocumentRequestDto documentRequestDto, @PathVariable String id) {
    return documentService.update(id, documentRequestDto);
  }

  @DeleteMapping("/{id}")
  @CustomAnnotation
  public void delete(@PathVariable String id) {
    documentService.delete(id);
  }
}
