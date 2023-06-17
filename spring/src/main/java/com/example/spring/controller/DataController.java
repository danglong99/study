package com.example.spring.controller;

import com.example.spring.domain.dto.DataRequestDto;
import com.example.spring.domain.dto.DataResponseDto;
import com.example.spring.domain.entity.DataInformation;
import com.example.spring.other.CustomAnnotation;
import com.example.spring.service.DataService;
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
@RequestMapping("/data")
public class DataController {
  @Autowired
  DataService dataService;

  @GetMapping("/{id}")
  @CustomAnnotation
  public DataResponseDto getById(@PathVariable Integer id) {
    return dataService.getById(id);
  }

  @GetMapping("")
  public List<DataResponseDto> getAll() {
    return dataService.getAll();
  }

  @PostMapping("")
  public DataInformation create(@RequestBody DataRequestDto dataRequestDto) {
    return dataService.create(dataRequestDto);
  }

  @PutMapping("/{id}")
  @CustomAnnotation
  public DataInformation update(@RequestBody DataRequestDto dataRequestDto, @PathVariable Integer id) {
    return dataService.update(id, dataRequestDto);
  }

  @DeleteMapping("/{id}")
  @CustomAnnotation
  public void delete(@PathVariable Integer id) {
    dataService.delete(id);
  }
}
