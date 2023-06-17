package com.example.spring.service;

import com.example.spring.domain.dto.DataRequestDto;
import com.example.spring.domain.dto.DataResponseDto;
import com.example.spring.domain.entity.DataInformation;
import com.example.spring.exception.CustomException;

import java.util.List;

public interface DataService {
  DataResponseDto getById(Integer id);

  List<DataResponseDto> getAll();

  DataInformation create(DataRequestDto dataRequestDto);

  DataInformation update(Integer id, DataRequestDto dataRequestDto) throws CustomException;

  void delete(Integer id);
}
