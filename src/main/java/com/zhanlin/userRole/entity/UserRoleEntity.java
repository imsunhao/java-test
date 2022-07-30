package com.zhanlin.userRole.entity;

import com.zhanlin.role.entity.RoleEntity;
import com.zhanlin.user.entity.UserEntity;
import com.zhanlin.utils.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_role")
public class UserRoleEntity extends BaseEntity {

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", columnDefinition = "int comment '用户ID'")
  private UserEntity userIdObj;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "role_id", columnDefinition = "int comment '权限ID'")
  private RoleEntity roleIdObj;
}
