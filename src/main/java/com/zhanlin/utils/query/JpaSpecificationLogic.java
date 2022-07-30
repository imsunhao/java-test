package com.zhanlin.utils.query;

public class JpaSpecificationLogic {
  /**
   * Operator AND
   * example: and: { a: 5 }
   * IN SQL: and ( a = 5)
   */
  public static String and = "_and";
  /**
   * Operator OR
   * or: { a: 5 }
   * IN SQL:
   * or ( a = 5)
   */
  public static String or = "_or";
}
