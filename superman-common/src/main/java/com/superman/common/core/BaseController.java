package com.superman.common.core;

import static com.superman.common.core.domain.AjaxResult.error;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.superman.common.core.domain.AjaxResult;
import com.superman.common.core.domain.AjaxResult.Type;
import com.superman.common.core.page.PageDomain;
import com.superman.common.core.page.TableDataInfo;
import com.superman.common.core.page.TableSupport;
import com.superman.common.utils.DateUtils;
import com.superman.common.utils.ServletUtils;
import com.superman.common.utils.StringUtils;
import com.superman.common.utils.sql.SqlUtil;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @Author: superman宁
 * @Description: web层通用数据处理
 */
public class BaseController {

  protected final Logger logger = LoggerFactory.getLogger(BaseController.class);


  /**
   * 将前台传递过来的日期格式的字符串，自动转化为Date类型
   */
  @InitBinder
  public void initBinder(WebDataBinder binder){
    // Date类型转换
    binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text)
      {
        setValue(DateUtils.parseDate(text));
      }
    });
  }

  /**
   * 设置请求分页数据
   */
  protected void startPage(){
    PageDomain pageDomain = TableSupport.buildPageRequest();
    Integer pageNum = pageDomain.getPageNum();
    Integer pageSize = pageDomain.getPageSize();
    if(StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)){
      String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
      PageHelper.startPage(pageNum,pageSize,orderBy);
    }
  }

  /**
   * 设置请求排序数据
   */
  protected void startOrderBy(){
    PageDomain pageDomain = TableSupport.getPageDomain();
    if(StringUtils.isNotEmpty(pageDomain.getOrderBy())){
      String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
      PageHelper.orderBy(orderBy);
    }
  }

  /**
   * 获取request
   * @return
   */
  public HttpServletRequest getRequest(){
    return ServletUtils.getRequest();
  }

  /**
   * 获取response
   * @return
   */
  public HttpServletResponse getResponse(){
    return ServletUtils.getResponse();
  }

  /**
   * 获取session
   * @return
   */
  public HttpSession getSession(){
    return getRequest().getSession();
  }

  /**
   * 获取cookie
   * @return
   */
  public Cookie[] getCokie(){
    return getRequest().getCookies();
  }

  /**
   * 响应请求分页数据
   * @param list
   * @return
   */
  protected TableDataInfo getDataTabelInfo(List<?> list){
    TableDataInfo responseData = new TableDataInfo();
    responseData.setCode(0);
    responseData.setRows(list);
    responseData.setTotal(new PageInfo(list).getTotal());
    return responseData;
  }

  /**
   * 响应返回结果
   *
   * @param rows 影响行数
   * @return 操作结果
   */
  protected AjaxResult toAjax(int rows){
    return rows > 0 ? success() : error();
  }

  /**
   * 响应返回结果
   *
   * @param result 结果
   * @return 操作结果
   */
  protected AjaxResult toAjax(boolean result)
  {
    return result ? success() : error();
  }

  /**
   * 返回成功
   */
  public AjaxResult success()
  {
    return AjaxResult.success();
  }

  /**
   * 返回失败消息
   */
  public AjaxResult error()
  {
    return AjaxResult.error();
  }

  /**
   * 返回成功消息
   */
  public AjaxResult success(String message)
  {
    return AjaxResult.success(message);
  }

  /**
   * 返回失败消息
   */
  public AjaxResult error(String message)
  {
    return AjaxResult.error(message);
  }

  /**
   * 返回错误码消息
   */
  public AjaxResult error(Type type, String message)
  {
    return new AjaxResult(type, message);
  }

  public String redirect(String url){
    return StringUtils.format("redirect:{}", url);
  }


}
