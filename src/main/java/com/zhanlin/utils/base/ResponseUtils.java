package com.zhanlin.utils.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {
  public static ResponseEntity<BaseResponse> success(Object object) {
    BaseResponse response = new BaseResponse(object);
    return ResponseEntity.ok().body(response);
  }

  public static ResponseEntity<BaseResponse> failure(Integer code, String msg) {
    BaseResponse response = new BaseResponse(code, msg);
    return ResponseEntity.status(code).body(response);
  }

  public static ResponseEntity<BaseResponse> failure(HttpStatus code, String msg) {
    BaseResponse response = new BaseResponse(code.value(), msg);
    return ResponseEntity.status(code).body(response);
  }
}
