package com.example.spring.service;

import com.example.spring.domain.entity.UserInformation;
import com.example.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserInformation userInformation = userRepository.findByUsername(username);
    if (userInformation == null) {
      throw new UsernameNotFoundException(username);
    }
    return userInformation;
  }

  public UserInformation register(UserInformation userInformation) {
    String encodedPassword = passwordEncoder().encode(userInformation.getPassword());
    userInformation.setPassword(encodedPassword);
    userRepository.save(userInformation);
    return userInformation;
  }
}
