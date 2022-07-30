package com.zhanlin.user.entity;

import com.zhanlin.utils.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {
  @Column(columnDefinition = "varchar(50) comment '手机号'")
  private String phone;

  @Column(nullable = false, unique = true, columnDefinition = "varchar(50) comment '用户名'")
  private String username;

  @Column(nullable = false, columnDefinition = "varchar(200) comment '密码'")
  private String password;

  @Column(nullable = false, columnDefinition = "varchar(50) comment '昵称'")
  private String nickname;

  @Column(columnDefinition = "datetime comment '生日'")
  private LocalDateTime birthday;
}
