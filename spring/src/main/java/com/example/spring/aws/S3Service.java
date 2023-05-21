package com.example.spring.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

  public void download(String key) {
    try {
      S3Object object = amazonS3.getObject(bucket, key);
      S3ObjectInputStream objectContent = object.getObjectContent();
      FileOutputStream fos = new FileOutputStream(key);
      byte[] bufferedByte = new byte[1024];
      int length = 0;
      while ((length = objectContent.read(bufferedByte)) > 0) {
        fos.write(bufferedByte, 0, length);
      }
      objectContent.close();
      fos.close();
    } catch (AmazonServiceException | IOException e) {
      throw new IllegalStateException("Failed to download the file", e);
    }
  }

  public InputStream getObject(String key) {
    S3Object s3Object = amazonS3.getObject(bucket, key);
    return s3Object.getObjectContent();
  }

  public byte[] getFile(String key) {
    try {
      S3Object object = amazonS3.getObject(bucket, key);
      return IOUtils.toByteArray(object.getObjectContent());
    } catch (AmazonServiceException | IOException e) {
      throw new IllegalStateException("Failed to download the file", e);
    }
  }
}
