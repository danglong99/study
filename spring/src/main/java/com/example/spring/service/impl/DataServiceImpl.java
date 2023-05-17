package com.example.spring.service.impl;

import com.example.spring.repository.DataRepository;
import com.example.spring.domain.dto.DataRequestDto;
import com.example.spring.domain.dto.DataResponseDto;
import com.example.spring.domain.entity.DataInformation;
import com.example.spring.service.DataService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class DataServiceImpl implements DataService {
  @Autowired
  private DataRepository dataRepository;

  @Bean
  private ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Override
  public DataResponseDto getById(Integer id) {
    DataInformation dataInformation = dataRepository.getById(id);
    return modelMapper().map(dataInformation, DataResponseDto.class);
  }

  @Override
  public List<DataResponseDto> getAll() {
    List<DataInformation> dataInformations = dataRepository.findAll();
    return dataInformations.stream().map(dataInformation -> modelMapper().map(dataInformation, DataResponseDto.class)).toList();
  }

  @Override
  public DataResponseDto create(DataRequestDto dataRequestDto) {
    DataInformation dataInformation = modelMapper().map(dataRequestDto, DataInformation.class);
    dataRepository.save(dataInformation);
    return modelMapper().map(dataInformation, DataResponseDto.class);
  }

  @Override
  public DataResponseDto update(Integer id, DataRequestDto dataRequestDto) {
    DataInformation dataInformation = dataRepository.getById(id);
    if (ObjectUtils.isEmpty(dataInformation)) {
      return null;
    }
    modelMapper().map(dataRequestDto, dataInformation);
    dataRepository.save(dataInformation);
    return modelMapper().map(dataInformation, DataResponseDto.class);
  }

  @Override
  public void delete(Integer id) {
    dataRepository.deleteById(id);
  }
}
