package com.superman.config;

import com.superman.utils.ServletUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * @Author: superman宁
 * @Description: 服务相关配置
 */
@Component
public class ServerConfig {

  /**
   * 获取完整的请求路径，包括：域名，端口，上下文访问路径
   *
   * @return 服务地址
   */
  public String getUrl(){
    HttpServletRequest request = ServletUtils.getRequest();
    StringBuffer url = request.getRequestURL();
    String contextPath = request.getServletContext().getContextPath();
    return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
  }


}
