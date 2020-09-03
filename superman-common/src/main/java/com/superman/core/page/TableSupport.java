package com.superman.core.page;

import com.superman.constant.CommonConstant;
import com.superman.utils.ServletUtils;

/**
 * @Author: superman宁
 * @Description: 表格数据处理
 */
public class TableSupport {

  /**
   *  封装分页对象
   * @return
   */
  public static PageDomain getPageDomain(){
    PageDomain pageDomain = new PageDomain();
    pageDomain.setPageNum(ServletUtils.getParameterToInt(CommonConstant.PAGE_NUM));
    pageDomain.setPageSize(ServletUtils.getParameterToInt(CommonConstant.PAGE_SIZE));
    pageDomain.setOrderByColumn(ServletUtils.getParameter(CommonConstant.ORDER_BY_COLUMN));
    pageDomain.setOrderByWay(ServletUtils.getParameter(CommonConstant.ORDER_BY_WAY));
    return pageDomain;
  }


  public static PageDomain buildPageRequest(){
    return getPageDomain();
  }



}
