package com.example.spring.controller;

import com.example.spring.domain.dto.AuthRequestDto;
import com.example.spring.domain.dto.AuthResponseDto;
import com.example.spring.domain.entity.UserInformation;
import com.example.spring.exception.CustomException;
import com.example.spring.jwt.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
      AuthResponseDto response = new AuthResponseDto(user.getUsername(), accessToken);

      return response;

    } catch (BadCredentialsException ex) {
      throw new CustomException(401, HttpStatus.UNAUTHORIZED, "User is unauthorized");
    }
  }
}
