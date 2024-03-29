package com.example.spring.jwt;

import com.example.spring.domain.entity.UserInformation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtTokenUtil jwtUtil;

  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request,
                                  @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
      throws ServletException, IOException {

    if (!hasAuthorizationBearer(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = getAccessToken(request);

    if (!jwtUtil.validateAccessToken(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    setAuthenticationContext(token, request);
    filterChain.doFilter(request, response);
  }

  private boolean hasAuthorizationBearer(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    return !ObjectUtils.isEmpty(header) && header.startsWith("Bearer");
  }

  private String getAccessToken(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    return header.split(" ")[1].trim();
  }

  private void setAuthenticationContext(String token, HttpServletRequest request) {
    UserDetails userDetails = getUserDetails(token);

    UsernamePasswordAuthenticationToken
        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);

    authentication.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private UserDetails getUserDetails(String token) {
    UserInformation userDetails = new UserInformation();
    String[] jwtSubject = jwtUtil.getSubject(token).split(",");

    userDetails.setId(Integer.parseInt(jwtSubject[0]));
    userDetails.setEmail(jwtSubject[1]);

    return userDetails;
  }
}
