package com.superman.activity.config;

import java.awt.Color;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.image.ProcessDiagramGenerator;

/**
 * @description: 客户端流程图生成
 * @author: superman宁
 **/
public interface ICustomProcessDiagramGenerator extends ProcessDiagramGenerator {

  /**
   * 生成流程图
   * @param bpmnModel 流程图模型
   * @param imageType 图片类型
   * @param highLightedActivities
   * @param highLightedFlows
   * @param activityFontName
   * @param labelFontName
   * @param annotationFontName
   * @param customClassLoader
   * @param scaleFactor
   * @param colors
   * @param currIds
   * @return
   */
  InputStream generateDiagram(
      BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
      List<String> highLightedFlows, String activityFontName, String labelFontName, String annotationFontName,
      ClassLoader customClassLoader, double scaleFactor, Color[] colors, Set<String> currIds);

}
