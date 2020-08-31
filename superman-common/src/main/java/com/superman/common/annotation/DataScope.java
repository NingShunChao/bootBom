package com.superman.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 数据权限过滤注解
 * @author: superman宁
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

  /**
   * 部门表的别名
   */
   String deptAlias() default "";

  /**
   * 用户表的别名
   */
   String userAlias() default "";

}
