package com.zhanlin.utils.query;

import lombok.Data;

import java.util.List;

@Data
public class PageQueryResult<T> {
  private List<T> list;
  private Long count;

  public PageQueryResult(List<T> list, Long count) {
    this.list = list;
    this.count = count;
  }
}
