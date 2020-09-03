package com.superman.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @description: Entity基类
 * @author: nonesuch's
 * @create: 2020-08-26 11:24
 **/
public class BaseEntity<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private String searchValue;

  public String getSearchValue() {
    return searchValue;
  }

  public void setSearchValue(String searchValue) {
    this.searchValue = searchValue;
  }

  /**
   * 实体编号（唯一标识）
   */
  private String id;

  /**
   * 创建者
   */
  private String createBy;

  /**
   * 创建时间
   * timezone设置时区（不设置有可能出现八小时的误差）
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;

  /**
   * 修改者
   */
  private String updateBy;

  /**
   * 修改时间
   * timezone设置时区（不设置有可能出现八小时的误差）
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date updateTime;

  /**
   * 删除标记（0：正常；1：删除；)
   */
  private String delFlag;

  /**
   * @Value("${}")
   * 数据库类型，用于在mapper中定义编写的sql适应各种数据库的语句
   */
  private String dbName;

  /**
   * 备注
   */
  private String remark;

  /**
   * 请求参数
   */
  private Map<String, Object> params;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCreateBy() {
    return createBy;
  }

  public void setCreateBy(String createBy) {
    this.createBy = createBy;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getUpdateBy() {
    return updateBy;
  }

  public void setUpdateBy(String updateBy) {
    this.updateBy = updateBy;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getDelFlag() {
    return delFlag;
  }

  public void setDelFlag(String delFlag) {
    this.delFlag = delFlag;
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }
}
