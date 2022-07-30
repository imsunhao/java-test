package com.zhanlin.role.entity;

import com.zhanlin.utils.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {
  @Column(nullable = false, columnDefinition = "varchar(50) comment '权限名称'")
  private String roleName;

  @Column(nullable = false, unique = true, columnDefinition = "varchar(200) comment '编码'")
  private String roleCode;
}
