package com.example.spring.controller;

import com.example.spring.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/s3")
public class AWSController {
  @Autowired
  S3Service s3Service;

  @PostMapping("/upload")
  public void upload(@RequestBody MultipartFile file) throws IOException {
    s3Service.upload(file.getOriginalFilename(), file);
  }

  @GetMapping("/download")
  public void download(@RequestParam("file") String key) {
    s3Service.download(key);
  }

  @GetMapping("/get")
  public InputStream get(@RequestParam("file") String key) {
    return s3Service.getObject(key);
  }

  @GetMapping("/test")
  public byte[] test(@RequestParam("file") String key) {
    return s3Service.getFile(key);
  }
}
