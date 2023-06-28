package com.example.spring.service.impl;

import com.example.spring.exception.CustomException;
import com.example.spring.repository.DataRepository;
import com.example.spring.domain.dto.DataRequestDto;
import com.example.spring.domain.dto.DataResponseDto;
import com.example.spring.domain.entity.DataInformation;
import com.example.spring.service.DataService;
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
public class DataServiceImpl implements DataService {
  private static final Logger logger = LogManager.getLogger(DataServiceImpl.class);

  @Autowired
  private DataRepository dataRepository;

  @Bean
  private ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Override
  public DataResponseDto getById(Integer id) {
    Optional<DataInformation> dataInformationOpt = dataRepository.findById(id);
    if (dataInformationOpt.isEmpty()) {
      logger.error("Data can't be found");
      throw new CustomException(ErrorDetail.DATA_NOT_FOUND);
    }
    return modelMapper().map(dataInformationOpt.get(), DataResponseDto.class);
  }

  @Override
  public List<DataResponseDto> getAll() {
    List<DataInformation> dataInformations = dataRepository.findAll();
    return dataInformations.stream().map(dataInformation -> modelMapper().map(dataInformation, DataResponseDto.class)).toList();
  }

  @Override
  public DataInformation create(DataRequestDto dataRequestDto) {
    DataInformation dataInformation = modelMapper().map(dataRequestDto, DataInformation.class);
    return dataRepository.save(dataInformation);
  }

  @Override
  public DataInformation update(Integer id, DataRequestDto dataRequestDto) throws CustomException {
    Optional<DataInformation> dataInformationOpt = dataRepository.findById(id);
    if (dataInformationOpt.isEmpty()) {
      logger.error("Data can't be found");
      throw new CustomException(ErrorDetail.DATA_NOT_FOUND);
    }
    modelMapper().map(dataRequestDto, dataInformationOpt.get());
    return dataRepository.save(dataInformationOpt.get());
  }

  @Override
  public void delete(Integer id) {
    dataRepository.deleteById(id);
  }
}
