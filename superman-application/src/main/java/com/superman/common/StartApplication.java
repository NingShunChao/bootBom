package com.superman.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @description: 项目启动入口类
 * @author: ningshunchao
 * @create: 2020-08-26 09:49
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@ComponentScan
public class StartApplication {

  public static void main(String[] args) {
    SpringApplication.run(StartApplication.class, args);
  }

}
