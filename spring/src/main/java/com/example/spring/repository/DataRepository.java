package com.example.spring.repository;

import com.example.spring.domain.entity.DataInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DataInformation, Integer> {
}
