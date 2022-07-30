package com.zhanlin.utils.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
  @Autowired
  JwtTokenUtils jwtTokenUtils;

  @Autowired
  JwtUserDetailsService jwtUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || header.isEmpty() || !header.startsWith("Bearer")) {
      chain.doFilter(request, response);
      return;
    }
    String token = header.split(" ")[1].trim();
    if (!jwtTokenUtils.validateToken(token)) {
      chain.doFilter(request, response);
      return;
    }
    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
    UsernamePasswordAuthenticationToken
        authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null,
        userDetails == null ? Collections.emptyList() : userDetails.getAuthorities()
    );
    authentication.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request)
    );
    chain.doFilter(request, response);
  }
}

