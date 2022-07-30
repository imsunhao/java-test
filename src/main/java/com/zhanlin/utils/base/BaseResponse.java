package com.zhanlin.utils.base;

import lombok.Data;

@Data
public class BaseResponse {
  Integer code;
  String msg;
  Object data;

  public BaseResponse(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public BaseResponse(Object data) {
    this.code = 200;
    this.msg = "";
    this.data = data;
  }
}
