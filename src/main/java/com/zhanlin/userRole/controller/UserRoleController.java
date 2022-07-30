package com.zhanlin.userRole.controller;

import com.zhanlin.userRole.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-role")
public class UserRoleController {
  @Autowired
  UserRoleRepository userRoleRepository;
}
