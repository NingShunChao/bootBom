package com.superman.xss;


import com.superman.utils.html.EscapeUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @description:
 * @author: superman宁
 **/
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

  public XssHttpServletRequestWrapper(HttpServletRequest request) {
    super(request);
  }

  @Override
  public String[] getParameterValues(String name) {
    String[] values = super.getParameterValues(name);
    if (values != null) {
      int length = values.length;
      String[] escapeValues = new String[length];
      for (int i = 0; i < length; i++) {
        // 防xss攻击和过滤前后空格
        escapeValues[i] = EscapeUtil.clean(values[i]).trim();
      }
      return escapeValues;
    }
    return super.getParameterValues(name);
  }



}
