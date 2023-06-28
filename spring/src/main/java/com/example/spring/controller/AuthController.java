package com.example.spring.controller;

import com.example.spring.domain.dto.AuthRequestDto;
import com.example.spring.domain.dto.AuthResponseDto;
import com.example.spring.domain.entity.UserInformation;
import com.example.spring.exception.CustomException;
import com.example.spring.jwt.JwtTokenUtil;
import com.example.spring.utils.ErrorDetail;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private static final Logger logger = LogManager.getLogger(AuthController.class);
  @Autowired(required = false)
  AuthenticationManager authManager;

  @Autowired
  JwtTokenUtil jwtTokenUtil;

  @PostMapping("/login")
  public AuthResponseDto login(@RequestBody @Valid AuthRequestDto request) {
    try {
      Authentication authentication = authManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getUsername(), request.getPassword())
      );

      UserInformation user = (UserInformation) authentication.getPrincipal();
      String accessToken = jwtTokenUtil.generateAccessToken(user);
      return new AuthResponseDto(user.getUsername(), accessToken);

    } catch (BadCredentialsException ex) {
      logger.error("User is unauthenticated");
      throw new CustomException(ErrorDetail.UNAUTHENTICATE);
    }
  }
}
