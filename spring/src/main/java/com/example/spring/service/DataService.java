package com.example.spring.service;

import com.example.spring.domain.dto.DataRequestDto;
import com.example.spring.domain.dto.DataResponseDto;

import java.util.List;

public interface DataService {
  DataResponseDto getById(Integer id);

  List<DataResponseDto> getAll();

  DataResponseDto create(DataRequestDto dataRequestDto);

  DataResponseDto update(Integer id, DataRequestDto dataRequestDto);

  void delete(Integer id);
}
