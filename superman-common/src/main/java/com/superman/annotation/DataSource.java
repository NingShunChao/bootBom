package com.superman.annotation;

import com.superman.enums.DataSourceType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 自定义多数据源切换注解
 * @author: superman宁
 **/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

  /**
   * 切换数据源名称
   */
  DataSourceType value() default DataSourceType.MASTER;

}
