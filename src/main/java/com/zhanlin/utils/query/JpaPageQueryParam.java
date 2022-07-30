package com.zhanlin.utils.query;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JpaPageQueryParam {
  private Map<String, Object> where;
  // example: -created_at,id
  private List<String> orderBy;
  private Integer page = 0;
  private Integer size = 1000;
}