package com.zhanlin.utils.query;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JpaSpecificationBuilder<T> implements Specification<T> {
  private final Map<String, Object> whereOptions;

  public JpaSpecificationBuilder(Map<String, Object> whereOptions) {
    this.whereOptions = whereOptions;
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    return generateLogicPredicate(root, criteriaBuilder, JpaSpecificationLogic.and, this.whereOptions);
  }

  /**
   * field -> value
   */
  public Predicate generatePredicate(Root<T> root, CriteriaBuilder builder, String field, Object value) {
    if (value instanceof List) {
      return builder.in(root.get(field)).value(value);
    } else {
      return builder.equal(root.get(field), value);
    }
  }

  /**
   * field -> operator -> value
   */
  public Predicate generatePredicate(Root<T> root, CriteriaBuilder builder, String field, String operator, Object value) throws NoSuchMethodError {
    if (value instanceof List) {
      if (JpaSpecificationOp.in.equals(operator)) {
        return builder.in(root.get(field)).value(value);
      } else if (JpaSpecificationOp.notIn.equals(operator)) {
        return builder.not(builder.in(root.get(field)).value(value));
      } else {
        throw new NoSuchMethodError();
      }
    } else {
      if (JpaSpecificationOp.eq.equals(operator)) {
        return builder.equal(root.get(field), value);
      } else if (JpaSpecificationOp.ne.equals(operator)) {
        return builder.notEqual(root.get(field), value);
      } else if (JpaSpecificationOp.is.equals(operator)) {
        return builder.isNull(root.get(field));
      } else if (JpaSpecificationOp.not.equals(operator)) {
        return builder.isNotNull(root.get(field));
      } else if (JpaSpecificationOp.like.equals(operator)) {
        return builder.like(root.get(field), String.valueOf(value));
      } else if (JpaSpecificationOp.notLike.equals(operator)) {
        return builder.notLike(root.get(field), String.valueOf(value));
      } else if (JpaSpecificationOp.gt.equals(operator)) {
        return builder.greaterThan(root.get(field), (Comparable) value);
      } else if (JpaSpecificationOp.gte.equals(operator)) {
        return builder.greaterThanOrEqualTo(root.get(field), (Comparable) value);
      } else if (JpaSpecificationOp.lt.equals(operator)) {
        return builder.lessThan(root.get(field), (Comparable) value);
      } else if (JpaSpecificationOp.lte.equals(operator)) {
        return builder.lessThanOrEqualTo(root.get(field), (Comparable) value);
      } else {
        throw new NoSuchMethodError();
      }
    }
  }

  /**
   * logic -> operator -> field -> value
   * logic -> field -> value
   * logic -> field -> operator -> value
   * logic -> field -> logic -> operator -> value
   */
  public Predicate generateLogicPredicate(Root<T> root, CriteriaBuilder builder, String logic, Map<String, Object> mapData) {
    List<Predicate> predicateList = new ArrayList<>();
    for (String key : mapData.keySet()) {
      Object value = mapData.get(key);
      if (key.startsWith("_")) {
        if (JpaSpecificationLogic.and.equals(key) || JpaSpecificationLogic.or.equals(key)) {
          predicateList.add(this.generateLogicPredicate(root, builder, key, (Map<String, Object>) value));
        }
      } else {
        if (value instanceof Map) {
          predicateList.add(generateOpPredicate(root, builder, key, (Map<String, Object>) value));
        } else {
          predicateList.add(generatePredicate(root, builder, key, value));
        }
      }
    }

    Predicate predicate = null;
    for (Predicate p : predicateList) {
      if (JpaSpecificationLogic.and.equals(logic)) {
        predicate = predicate == null ? p : builder.and(predicate, p);
      } else if (JpaSpecificationLogic.or.equals(logic)) {
        predicate = predicate == null ? p : builder.or(predicate, p);
      }
    }
    return predicate;
  }

  /**
   * field -> operator -> value
   * field -> logic -> operator -> value
   */
  public Predicate generateOpPredicate(Root<T> root, CriteriaBuilder builder, String field, Map<String, Object> map) {
    List<Predicate> predicateList = new ArrayList<>();
    for (String operator : map.keySet()) {
      if (operator.startsWith("_")) {
        Object value = map.get(operator);
        if (JpaSpecificationLogic.and.equals(operator) || JpaSpecificationLogic.or.equals(operator)) {
          if (value instanceof Map) {
            predicateList.add(generateLogicPredicate(root, builder, operator, field, (Map<String, Object>) value));
          } else {
            predicateList.add(generatePredicate(root, builder, field, operator, value));
          }
        } else {
          if (!(value instanceof Map)) {
            predicateList.add(generatePredicate(root, builder, field, operator, value));
          }
        }
      }
    }
    Predicate predicate = null;
    for (Predicate p : predicateList) {
      predicate = predicate == null ? p : builder.and(predicate, p);
    }
    return predicate;
  }

  public Predicate generateLogicPredicate(Root<T> root, CriteriaBuilder builder, String logic, String field, Map<String, Object> opMap) {
    List<Predicate> predicateList = new ArrayList<>();
    for (String operator : opMap.keySet()) {
      predicateList.add(generatePredicate(root, builder, field, operator, opMap.get(operator)));
    }
    Predicate predicate = null;
    for (Predicate p : predicateList) {
      if (JpaSpecificationLogic.and.equals(logic)) {
        predicate = predicate == null ? p : builder.and(predicate, p);
      } else if (JpaSpecificationLogic.or.equals(logic)) {
        predicate = predicate == null ? p : builder.or(predicate, p);
      }
    }
    return predicate;
  }
}
