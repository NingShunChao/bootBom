package com.superman.core.domain;

/**
 * @Author: superman宁
 * @Description: Tree 基类
 */
public class TreeEntity extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 父级菜单名称
   */
  private String parentName;

  /**
   * 父菜单ID
   */
  private String parentId;

  /**
   * 显示顺序
   */
  private Integer orderNum;

  /**
   * 父级列表
   */
  private String ancestors;

  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public Integer getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(Integer orderNum) {
    this.orderNum = orderNum;
  }

  public String getAncestors() {
    return ancestors;
  }

  public void setAncestors(String ancestors) {
    this.ancestors = ancestors;
  }
}
