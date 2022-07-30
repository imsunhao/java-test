package com.zhanlin.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
@Configuration
public class JwtTokenUtils {
  @Value("${jwt.property.expire}")
  private Long expire;
  @Value("${jwt.property.secret}")
  private String secret;

  public String generateToken(String username) {
    Date nowDate = new Date();
    Date expireDate = new Date(nowDate.getTime() + 1000 * expire);
    return Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setSubject(username)
        .setIssuedAt(nowDate)
        .setExpiration(expireDate)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public Boolean validateToken(String token) {
    String username = getUsernameFromToken(token);
    return (username != null) && !isTokenExpired(token);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }
}
