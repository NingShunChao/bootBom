package com.superman.activity.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 工作流配置
 * @author: superman宁
 **/
@Configuration
public class ActivityConfig implements ProcessEngineConfigurationConfigurer {

  @Autowired
  private ICustomProcessDiagramGenerator customProcessDiagramGenerator;


  @Override
  public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {

  }
}
