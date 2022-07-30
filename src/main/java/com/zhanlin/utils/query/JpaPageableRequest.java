package com.zhanlin.utils.query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class JpaPageableRequest {
  public static PageRequest generatorPageRequest(JpaPageQueryParam param) {
    List<Sort.Order> orderList = new ArrayList<>();
    if (param.getOrderBy() != null) {
      for (String item : param.getOrderBy()) {
        if (item.startsWith("-")) {
          orderList.add(new Sort.Order(Sort.Direction.DESC, item.substring(1)));
        } else {
          if (!item.equals("")) {
            orderList.add(new Sort.Order(Sort.Direction.ASC, item));
          }
        }
      }
    }
    Sort sort = Sort.by(orderList);
    return PageRequest.of(param.getPage(), param.getSize(), sort);
  }
}
