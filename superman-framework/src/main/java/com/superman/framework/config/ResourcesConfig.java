package com.superman.framework.config;

import com.superman.config.Global;
import com.superman.constant.CommonConstant;
import com.superman.framework.interceptor.RepeatSubmitInterceptor;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: superman宁
 * @Description: 通用配置
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

  /**
   * 首页地址
   */
  @Value("${shiro.user.indexUrl}")
  private String indexUrl;

  @Resource
  private RepeatSubmitInterceptor repeatSubmitInterceptor;

  /**
   * 默认首页的设置，当输入域名时可以自动跳转到默认的指定网页
   * @param registry
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("forward:" + indexUrl);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
/** 本地文件上传路径 */
    registry.addResourceHandler(CommonConstant.RESOURCE_PREFIX + "/**").addResourceLocations("file:" + Global
        .getProfile() + "/");

    /** swagger配置 */
    registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
  }
}
