package com.superman.common.xss;

import com.superman.common.utils.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;

/**
 * @description: Xss过滤器
 * @author: superman宁
 **/
public class XssFilter implements Filter {

  /**
   * 排除链接(从配置文件中获取)
   */
  public List<String > excludes = new ArrayList<>();

  /**
   * xss过滤开关(从配置文件中获取)
   */
  public boolean enabled = false;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    String tempExcludes = filterConfig.getInitParameter("excludes");
    String tempEnabled = filterConfig.getInitParameter("enabled");
    if(StringUtils.isNotEmpty(tempExcludes)){
      excludes = Arrays.asList(tempExcludes.split(","));
    }
    if(StringUtils.isNotEmpty(tempEnabled)){
      enabled = Boolean.parseBoolean(tempEnabled);
    }
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest)servletRequest;
    HttpServletResponse response = (HttpServletResponse)servletResponse;
    // 排除的链接直接放行
    if(handleExcludeURL(request, response)){
      filterChain.doFilter(request, response);
    }
    // 处理未排除的链接
    XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(request);
    filterChain.doFilter(xssRequest, response);
  }

  /**
   * 处理排除的url
   * @param request
   * @param response
   * @return boolean
   */
  private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
    if(!enabled){
      return true;
    }
    if(CollectionUtils.isNotEmpty(excludes)){
      String url = request.getServletPath();
      for (String pattern : excludes) {
        Pattern p = Pattern.compile("^" + pattern);
        Matcher m = p.matcher(url);
        if (m.find()) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void destroy() {

  }
}
