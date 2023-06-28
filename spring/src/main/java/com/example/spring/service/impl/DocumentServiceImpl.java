package com.example.spring.service.impl;

import com.example.spring.domain.dto.DocumentRequestDto;
import com.example.spring.domain.dto.DocumentResponseDto;
import com.example.spring.domain.entity.DocumentInformation;
import com.example.spring.exception.CustomException;
import com.example.spring.repository.DocumentRepository;
import com.example.spring.service.DocumentService;
import com.example.spring.utils.ErrorDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {
  private static final Logger logger = LogManager.getLogger(DocumentServiceImpl.class);
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
      logger.error("Document can't be found");
      throw new CustomException(ErrorDetail.DOCUMENT_NOT_FOUND);
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
      logger.error("Document can't be found");
      throw new CustomException(ErrorDetail.DOCUMENT_NOT_FOUND);
    }
    modelMapper().map(documentRequestDto, documentInformationOpt.get());
    return documentRepository.save(documentInformationOpt.get());
  }

  @Override
  public void delete(String id) {
    documentRepository.deleteById(id);
  }
}
