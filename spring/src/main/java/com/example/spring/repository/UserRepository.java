package com.example.spring.repository;

import com.example.spring.domain.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInformation, Integer> {
  UserInformation findByUsername(String userName);
}
