package com.example.spring.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.spring.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/s3")
public class AWSController {
  @Autowired
  S3Service s3Service;

  @PostMapping("/upload")
  public void upload(@RequestBody MultipartFile file) throws IOException {
    s3Service.upload(file.getOriginalFilename(), file);
  }

  @GetMapping("/get")
  public S3Object get(@RequestParam("file") String key) {
    return s3Service.getObject(key);
  }

  @GetMapping("/download")
  public ResponseEntity<byte[]> download(@RequestParam("file") String key) {
    return s3Service.download(key);
  }

  @GetMapping("/list-objects")
  public List<String> listObject() {
    return s3Service.listObject();
  }

  @GetMapping("/presigned-url")
  public URL presignedUrl(@RequestParam String key) {
    return s3Service.presignedUrl(key);
  }
}
