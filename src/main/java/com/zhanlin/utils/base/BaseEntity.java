package com.zhanlin.utils.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, columnDefinition = "int comment 'id'")
  private Long id;

  @Column(name = "created_at", columnDefinition = "datetime default CURRENT_TIMESTAMP comment '创建时间'")
  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间'")
  private LocalDateTime updatedAt = LocalDateTime.now();

  @CreatedBy
  @Column(name = "created_by", columnDefinition = "int comment '创建人id'")
  private Long createdBy;

  @LastModifiedBy
  @Column(name = "updated_by", columnDefinition = "int comment '更新人id'")
  private Long updatedBy;

  @Column(nullable = true, columnDefinition = "varchar(255) comment '备注'")
  private String remark;

  @Column(name = "enable_flag", nullable = false, columnDefinition = "int DEFAULT 1 comment '状态'")
  private Integer enableFlag = 1;
}
