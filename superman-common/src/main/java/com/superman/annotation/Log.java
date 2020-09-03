package com.superman.annotation;

import com.superman.enums.BusinessType;
import com.superman.enums.OperatorType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 自定义操作日志记录注解
 * @author: superman宁
 **/
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

  /**
   * 模块
   */
  String title() default "";

  /**
   * 业务类型，默认为其他
   */
  BusinessType businessType() default BusinessType.OTHER;

  /**
   * 操作人类别,默认为后台用户
   */
  OperatorType operatorType() default OperatorType.MANAGE;

  /**
   * 是否记录请求的参数,默认为true
   */
  boolean isSaveRequestData() default true;



}
