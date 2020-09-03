package com.superman.core.page;

import com.superman.utils.StringUtils;

/**
 * @description: 分页数据
 * @author: superman宁
 **/
public class PageDomain {

  /** 当前记录起始索引 */
  private Integer pageNum;
  /** 每页显示记录数 */
  private Integer pageSize;
  /** 排序列 */
  private String orderByColumn;
  /** 排序的方向 "desc" 或者 "asc". */
  private String orderByWay;

  public Integer getPageNum() {
    return pageNum;
  }

  public void setPageNum(Integer pageNum) {
    this.pageNum = pageNum;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getOrderByColumn() {
    return orderByColumn;
  }

  public void setOrderByColumn(String orderByColumn) {
    this.orderByColumn = orderByColumn;
  }

  public String getOrderByWay() {
    return orderByWay;
  }

  public void setOrderByWay(String orderByWay) {
    this.orderByWay = orderByWay;
  }

  public String getOrderBy(){
    if(StringUtils.isNotEmpty(this.orderByColumn)){
      return StringUtils.toUnderScoreCase(this.orderByColumn+" "+this.orderByWay);
    }else {
      return "";
    }
  }
}
