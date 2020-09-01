package com.superman.generator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @description: 读取代码生成相关配置
 * @author: superman宁
 **/
@Component
@ConfigurationProperties(prefix = "gen")
@PropertySource(value = {"classpath:generator.yml"})
public class GenConfig {

  /** 作者 */
  @Value("${author}")
  private static String author;

  /** 生成包路径 */
  @Value("${packageName}")
  private static  String packageName;

  /** 自动去除表前缀，默认是false */
  @Value("${autoRemovePre}")
  private static boolean autoRemovePre;

  /** 表前缀(类名不会包含表前缀) */
  @Value("${tablePrefix}")
  private static String tablePrefix;

  public static String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public static String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public static boolean getAutoRemovePre() {
    return autoRemovePre;
  }

  public void setAutoRemovePre(boolean autoRemovePre) {
    this.autoRemovePre = autoRemovePre;
  }

  public static String getTablePrefix() {
    return tablePrefix;
  }

  public void setTablePrefix(String tablePrefix) {
    this.tablePrefix = tablePrefix;
  }
}
