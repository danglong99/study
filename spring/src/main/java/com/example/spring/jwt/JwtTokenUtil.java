package com.example.spring.jwt;


import com.example.spring.domain.entity.UserInformation;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
  private static final long EXPIRE_DURATION = 8 * 60 * 60 * 1000;

  @Value("${app.jwt.secret}")
  private String SECRET_KEY;

  public String generateAccessToken(UserInformation user) {
    return Jwts.builder()
        .setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
        .setIssuer("CodeJava")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  public boolean validateAccessToken(String token) {
    try {
      Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException | IllegalArgumentException ex) {
      System.err.println(ex.getMessage());
    }  catch (MalformedJwtException ex) {
      System.err.println("JWT is invalid");
    } catch (UnsupportedJwtException ex) {
      System.err.println("JWT is not supported");
    }
    return false;
  }

  public String getSubject(String token) {
    return parseClaims(token).getSubject();
  }

  private Claims parseClaims(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
  }
}
