package com.superman.framework.interceptor;

import com.superman.common.annotation.RepeatSubmit;
import com.superman.common.core.domain.AjaxResult;
import com.superman.common.json.JSON;
import com.superman.common.utils.ServletUtils;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @Author: superman宁
 * @Description: 防止重复提交拦截器
 */
@Component
public abstract class RepeatSubmitInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
  {
    if (handler instanceof HandlerMethod)
    {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      Method method = handlerMethod.getMethod();
      RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
      if (annotation != null)
      {
        if (this.isRepeatSubmit(request))
        {
          AjaxResult ajaxResult = AjaxResult.error("不允许重复提交，请稍后再试");
          ServletUtils.renderString(response, JSON.marshal(ajaxResult));
          return false;
        }
      }
      return true;
    }
    else
    {
      return super.preHandle(request, response, handler);
    }
  }

  /**
   * 验证是否重复提交由子类实现具体的防重复提交的规则
   *
   * @param request
   * @return
   * @throws Exception
   */
  public abstract boolean isRepeatSubmit(HttpServletRequest request) throws Exception;

}