package com.superman.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: superman宁
 * @Description: 全局配置类
 */
@Component
@ConfigurationProperties(prefix = "superman")
public class Global {

  /** 项目名称 */
  private static String name;

  /** 版本 */
  private static String version;

  /** 版权年份 */
  private static String copyrightYear;

  /** 实例演示开关 */
  private static boolean demoEnabled;

  /** 上传路径 */
  private static String profile;

  /** 获取地址开关 */
  private static boolean addressEnabled;

  /**
   * 获取头像上传路径
   */
  public static String getAvatarPath()
  {
    return getProfile() + "/avatar";
  }

  /**
   * 获取下载路径
   */
  public static String getDownloadPath()
  {
    return getProfile() + "/download/";
  }

  /**
   * 获取上传路径
   */
  public static String getUploadPath()
  {
    return getProfile() + "/upload";
  }

  public static String getName() {
    return name;
  }

  public static void setName(String name) {
    Global.name = name;
  }

  public static String getVersion() {
    return version;
  }

  public static void setVersion(String version) {
    Global.version = version;
  }

  public static String getCopyrightYear() {
    return copyrightYear;
  }

  public static void setCopyrightYear(String copyrightYear) {
    Global.copyrightYear = copyrightYear;
  }

  public static boolean isDemoEnabled() {
    return demoEnabled;
  }

  public static void setDemoEnabled(boolean demoEnabled) {
    Global.demoEnabled = demoEnabled;
  }

  public static String getProfile() {
    return profile;
  }

  public static void setProfile(String profile) {
    Global.profile = profile;
  }

  public static boolean isAddressEnabled() {
    return addressEnabled;
  }

  public static void setAddressEnabled(boolean addressEnabled) {
    Global.addressEnabled = addressEnabled;
  }
}
