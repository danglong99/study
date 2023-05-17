package com.example.spring.repository;

import com.example.spring.domain.entity.DocumentInformation;
import com.example.spring.repository.impl.DocumentRepositoryCustomImpl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<DocumentInformation, String>, DocumentRepositoryCustom {
}
