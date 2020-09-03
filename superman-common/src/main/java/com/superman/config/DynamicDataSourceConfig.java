package com.superman.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: superman宁
 * @Description: 数据源切换处理
 */
public class DynamicDataSourceConfig {

  public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceConfig.class);

  /**
   * 使用threadlocal维护变量，threadlocal为每个使用该变量的线程提供独立的变量副本
   * 所以每一个线程都可以独立的改变自己的副本，而不会影响其他线程所对应的副本
   */
  private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

  /**
   * 设置数据源的变量
   * @param dsType
   */
  public static void setDataSourceType(String dsType){
    log.info("切换到{}数据源", dsType);
    CONTEXT_HOLDER.set(dsType);
  }

  /**
   * 获得数据源的变量
   */
  public static String getDataSourceType()
  {
    return CONTEXT_HOLDER.get();
  }

  /**
   * 清空数据源变量
   */
  public static void clearDataSourceType()
  {
    CONTEXT_HOLDER.remove();
  }

}
