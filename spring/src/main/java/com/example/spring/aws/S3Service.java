package com.example.spring.aws;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.example.spring.exception.CustomException;
import com.example.spring.utils.ErrorDetail;
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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@Service
public class S3Service {
  private static final long EXPIRE_DURATION = 60 * 60 * 1000;

  private static final Date expiration = new Date();
  private final AmazonS3 amazonS3;

  @Value("${aws.s3.bucket}")
  private String bucket;


  @Autowired
  public S3Service(AmazonS3 amazonS3) {
    this.amazonS3 = amazonS3;
  }

  @Async
  public void upload(String key, MultipartFile file) throws IOException {
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType(file.getContentType());
      metadata.setContentLength(file.getSize());
      amazonS3.putObject(bucket, key, file.getInputStream(), metadata);
    } catch (CustomException exception) {
      throw new CustomException(ErrorDetail.S3_CONNECT_FAILED);
    }
  }

  public S3Object getObject(String key) {
    try {
      return amazonS3.getObject(bucket, key);
    } catch (CustomException exception) {
      throw new CustomException(ErrorDetail.S3_CONNECT_FAILED);
    }
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
    } catch (CustomException | UnsupportedEncodingException exception) {
      throw new CustomException(ErrorDetail.S3_CONNECT_FAILED);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<String> listObject() {
    try {
      ObjectListing listing = amazonS3.listObjects(bucket);
      return listing.getObjectSummaries().stream().map(S3ObjectSummary::getKey).toList();
    } catch (CustomException exception) {
      throw new CustomException(ErrorDetail.S3_CONNECT_FAILED);
    }
  }

  public URL presignedUrl(String key) {
    try {
      expiration.setTime(EXPIRE_DURATION);
      GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, key)
          .withMethod(HttpMethod.GET)
          .withExpiration(expiration);
      return amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
    } catch (CustomException exception) {
      throw new CustomException(ErrorDetail.S3_CONNECT_FAILED);
    }
  }
}
