package com.superman.framework.config;

import com.superman.common.utils.StringUtils;
import com.superman.common.xss.XssFilter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: superman宁
 * @Description: xss过滤器配置
 */
@Configuration
public class XssFilterConfig {

  @Value("${xss.enabled}")
  private String enabled;

  @Value("${xss.excludes}")
  private String excludes;

  @Value("${xss.urlPatterns}")
  private String urlPatterns;

  @Bean
  public FilterRegistrationBean xssFilterRegistration()
  {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setDispatcherTypes(DispatcherType.REQUEST);
    registration.setFilter(new XssFilter());
    registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
    registration.setName("xssFilter");
    registration.setOrder(Integer.MAX_VALUE);
    Map<String, String> initParameters = new HashMap<String, String>();
    initParameters.put("excludes", excludes);
    initParameters.put("enabled", enabled);
    registration.setInitParameters(initParameters);
    return registration;
  }

}
