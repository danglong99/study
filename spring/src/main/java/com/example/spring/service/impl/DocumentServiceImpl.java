package com.example.spring.service.impl;

import com.example.spring.domain.dto.DocumentRequestDto;
import com.example.spring.domain.dto.DocumentResponseDto;
import com.example.spring.domain.entity.DocumentInformation;
import com.example.spring.exception.CustomException;
import com.example.spring.repository.DocumentRepository;
import com.example.spring.service.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {
  @Autowired
  private DocumentRepository documentRepository;

  @Bean
  private ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Override
  public DocumentResponseDto getById(String id) {
    Optional<DocumentInformation> documentInformationOpt = documentRepository.findById(id);
    if (documentInformationOpt.isEmpty()) {
      throw new CustomException(404, HttpStatus.NOT_FOUND, "Document not found");
    }
    return modelMapper().map(documentInformationOpt.get(), DocumentResponseDto.class);
  }

  @Override
  public List<DocumentResponseDto> getAll() {
    List<DocumentInformation> documentInformations = documentRepository.findAll();
    return documentInformations.stream().map(documentInformation -> modelMapper().map(documentInformation, DocumentResponseDto.class)).toList();
  }

  @Override
  public DocumentInformation create(DocumentRequestDto documentRequestDto) {
    DocumentInformation documentInformation = modelMapper().map(documentRequestDto, DocumentInformation.class);
    return documentRepository.save(documentInformation);
  }

  @Override
  public DocumentInformation update(String id, DocumentRequestDto documentRequestDto) {
    Optional<DocumentInformation> documentInformationOpt = documentRepository.findById(id);
    if (documentInformationOpt.isEmpty()) {
      throw new CustomException(404, HttpStatus.NOT_FOUND, "Document not found");
    }
    modelMapper().map(documentRequestDto, documentInformationOpt.get());
    return documentRepository.save(documentInformationOpt.get());
  }

  @Override
  public void delete(String id) {
    documentRepository.deleteById(id);
  }
}
