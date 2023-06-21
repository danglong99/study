package com.example.spring.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Service
public class S3Service {
  private final AmazonS3 amazonS3;

  @Value("${aws.s3.bucket}")
  private String bucket;


  @Autowired
  public S3Service(AmazonS3 amazonS3) {
    this.amazonS3 = amazonS3;
  }

  @Async
  public void upload(String key, MultipartFile file) throws IOException {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());
    metadata.setContentLength(file.getSize());
    amazonS3.putObject(bucket, key, file.getInputStream(), metadata);
  }

  public S3Object getObject(String key) {
    return amazonS3.getObject(bucket, key);
  }

  public ResponseEntity<byte[]> download(String key) {
    try {
      S3Object object = amazonS3.getObject(bucket, key);
      S3ObjectInputStream objectInputStream = object.getObjectContent();
      byte[] bytes = IOUtils.toByteArray(objectInputStream);

      String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");

      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      httpHeaders.setContentLength(bytes.length);
      httpHeaders.setContentDispositionFormData("attachment", fileName);
      return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    } catch (AmazonServiceException | IOException e) {
      throw new IllegalStateException("Failed to download the file", e);
    }
  }

  public List<S3ObjectSummary> listObject() {
    ObjectListing listing = amazonS3.listObjects(bucket);
    return listing.getObjectSummaries();
  }
}
