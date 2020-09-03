package com.superman.framework.aspectj;


import com.superman.annotation.Log;
import com.superman.enums.BusinessStatus;
import com.superman.json.JSON;
import com.superman.utils.ServletUtils;
import com.superman.utils.StringUtils;
import com.superman.framework.manager.AsyncManager;
import com.superman.framework.manager.factory.AsyncFactory;
import com.superman.framework.util.ShiroUtils;
import com.superman.system.domain.SysOperLog;
import com.superman.system.domain.SysUser;
import java.lang.reflect.Method;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: superman宁
 * @Description: 操作日志记录处理
 */
@Aspect
@Component
public class LogAspect {

  private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

  /**
   * 配置织入点
   */
  @Pointcut("@annotation(com.superman.annotation.Log)")
  public void logPointCut(){
  }

  /**
   * 处理完请求后执行
   * @param joinPoint 切点
   * @param jsonResult
   */
  @AfterReturning(pointcut = "logPointCut()",returning = "jsonResult")
  public void doAfterReturn(JoinPoint joinPoint, Object jsonResult){
    handleLog(joinPoint,null,jsonResult);
  }

  /**
   * 拦截异常操作
   *
   * @param joinPoint 切点
   * @param e 异常
   */
  @AfterThrowing(value = "logPointCut()", throwing = "e")
  public void doAfterThrowing(JoinPoint joinPoint, Exception e)
  {
    handleLog(joinPoint, e, null);
  }


  protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult)
  {
    try
    {
      // 获得注解
      Log controllerLog = getAnnotationLog(joinPoint);
      if (controllerLog == null)
      {
        return;
      }

      // 获取当前的用户
      SysUser currentUser = ShiroUtils.getSysUser();

      // *========数据库日志=========*//
      SysOperLog operLog = new SysOperLog();
      operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
      // 请求的地址
      String ip = ShiroUtils.getIp();
      operLog.setOperIp(ip);
      // 返回参数
      operLog.setJsonResult(JSON.marshal(jsonResult));

      operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
      if (currentUser != null)
      {
        operLog.setOperName(currentUser.getLoginName());
        if (StringUtils.isNotNull(currentUser.getDept())
            && StringUtils.isNotEmpty(currentUser.getDept().getDeptName()))
        {
          operLog.setDeptName(currentUser.getDept().getDeptName());
        }
      }

      if (e != null)
      {
        operLog.setStatus(BusinessStatus.FAIL.ordinal());
        operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
      }
      // 设置方法名称
      String className = joinPoint.getTarget().getClass().getName();
      String methodName = joinPoint.getSignature().getName();
      operLog.setMethod(className + "." + methodName + "()");
      // 设置请求方式
      operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
      // 处理设置注解上的参数
      getControllerMethodDescription(controllerLog, operLog);
      // 保存数据库
      AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
    }
    catch (Exception exp)
    {
      // 记录本地异常日志
      log.error("==前置通知异常==");
      log.error("异常信息:{}", exp.getMessage());
      exp.printStackTrace();
    }
  }

  /**
   * 获取注解中对方法的描述信息，用于controller层注解
   * @param log 日志注解
   * @param operLog 操作日志
   */
  public void getControllerMethodDescription(Log log, SysOperLog operLog) throws Exception {
    // 设置action 动作
    operLog.setBusinessType(log.businessType().ordinal());
    // 设置标题
    operLog.setTitle(log.title());
    // 设置操作人类别
    operLog.setOperatorType(log.operatorType().ordinal());
    // 是否需要保存request，参数和值
    if(log.isSaveRequestData()){
      // 获取参数的信息，传入到数据库中
      setRequestValue(operLog);
    }
  }

  /**
   * 获取请求的参数，放到log中
   * @param operLog 操作日志
   */
  private void setRequestValue(SysOperLog operLog) throws Exception {
    Map<String, String[]> parameterMap = ServletUtils.getRequest().getParameterMap();
    String params = JSON.marshal(parameterMap);
    operLog.setOperParam(StringUtils.substring(params,0,2000));
  }


  /**
   * 是否存在注解，如果存在就获取
   * @param joinPoint
   * @return
   */
  private Log getAnnotationLog(JoinPoint joinPoint){
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();

    if (method != null) {
      return method.getAnnotation(Log.class);
    }
    return null;
  }


}
