package com.superman.quartz.util;

import com.superman.quartz.domain.SysJob;
import org.quartz.Job;

/**
 * @description: 定时任务执行类
 * @author: superman宁
 **/
public class ScheduleUtils {

  /**
   * 获取quartz任务类
   * @param sysJob
   * @return
   */
  public static Class<? extends Job> getQuartzJobClass(SysJob sysJob){
    boolean isConcurrent = "0".equals(sysJob.getConcurrent());
    return isConcurrent ? QuartzJobExecution
  }

}
