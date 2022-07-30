package com.zhanlin.user.controller;

import com.zhanlin.user.entity.UserEntity;
import com.zhanlin.user.pojo.UserLoginPojo;
import com.zhanlin.user.repository.UserRepository;
import com.zhanlin.utils.base.BaseResponse;
import com.zhanlin.utils.base.ResponseUtils;
import com.zhanlin.utils.jwt.JwtTokenUtils;
import com.zhanlin.utils.query.JpaPageQueryParam;
import com.zhanlin.utils.query.JpaPageableRequest;
import com.zhanlin.utils.query.JpaSpecificationBuilder;
import com.zhanlin.utils.query.PageQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtTokenUtils jwtTokenUtils;

  @Autowired
  UserRepository userRepository;

  @PostMapping("/login")
  public ResponseEntity<BaseResponse> login(@RequestBody UserLoginPojo param) {
    try {
      Authentication authenticate = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(param.getUsername(), param.getPassword())
      );
      User user = (User) authenticate.getPrincipal();
      return ResponseEntity.ok().header(
          HttpHeaders.AUTHORIZATION, jwtTokenUtils.generateToken(user.getUsername())
      ).body(new BaseResponse(user));
    } catch (BadCredentialsException e) {
      return ResponseUtils.failure(HttpStatus.UNAUTHORIZED, e.getMessage());
    }
  }

  @PostMapping("/register")
  public ResponseEntity<BaseResponse> register(@RequestBody UserEntity entity) {
    try {
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String password = passwordEncoder.encode(entity.getPassword());
      entity.setPassword(password);
      UserEntity user = userRepository.save(entity);
      return ResponseUtils.success(user);
    } catch (Exception e) {
      return ResponseUtils.failure(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse> findById(@PathVariable String id) {
    try {
      Optional<UserEntity> user = userRepository.findById(Long.valueOf(id));
      return ResponseUtils.success(user);
    } catch (Exception e) {
      return ResponseUtils.failure(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @PostMapping("/list")
  public ResponseEntity<BaseResponse> findById(@RequestBody JpaPageQueryParam param) {
    try {
      Specification<UserEntity> spec = new JpaSpecificationBuilder<>(param.getWhere());
      PageRequest pageRequest = JpaPageableRequest.generatorPageRequest(param);

      Page<UserEntity> page = userRepository.findAll(spec, pageRequest);
      PageQueryResult<UserEntity> result = new PageQueryResult<>(page.getContent(), page.getTotalElements());
      return ResponseUtils.success(result);
    } catch (Exception e) {
      return ResponseUtils.failure(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }
}
