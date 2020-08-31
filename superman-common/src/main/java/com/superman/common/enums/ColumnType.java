package com.superman.common.enums;

/**
 * @description: 字段类型
 * @author: superman宁
 **/
public enum ColumnType {

  /**
   * 数字
   */
  NUMERIC(0),
  /**
   * 字符串
   */
  STRING(1);

  private final int value;

  ColumnType(int value) {
    this.value = value;
  }

  public int getValue(){
    return this.value;
  }


}
