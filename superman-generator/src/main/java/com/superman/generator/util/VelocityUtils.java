package com.superman.generator.util;

import com.superman.constant.GenConstants;
import com.superman.utils.DateUtils;
import com.superman.utils.StringUtils;
import com.superman.generator.domain.GenTable;
import com.superman.generator.domain.GenTableColumn;
import java.util.HashSet;
import java.util.List;
import org.apache.velocity.VelocityContext;

/**
 * @description: 引擎工具
 * @author: superman宁
 **/
public class VelocityUtils {

  /** 项目空间路径 */
  private static final String PROJECT_PATH = "main/java";

  /** mybatis空间路径 */
  private static final String MYBATIS_PATH = "main/resources/mapper";

  /** html空间路径 */
  private static final String TEMPLATES_PATH = "main/resources/templates";

  /**
   * 设置模板变量信息
   * @param genTable
   * @return 模板列表
   */
  public static VelocityContext prepareContext(GenTable genTable){
    String moduleName = genTable.getModuleName();
    String businessName = genTable.getBusinessName();
    String packageName = genTable.getPackageName();
    String tplCategory = genTable.getTplCategory();
    String functionName = genTable.getFunctionName();

    VelocityContext velocityContext = new VelocityContext();
    velocityContext.put("tplCategory",tplCategory);
    velocityContext.put("tableName",genTable.getTableName());
    velocityContext.put("functionName", StringUtils.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
    velocityContext.put("ClassName",genTable.getClassName());
    velocityContext.put("className",StringUtils.uncapitalize(genTable.getClassName()));
    velocityContext.put("moduleName",moduleName);
    velocityContext.put("businessName",businessName);
    velocityContext.put("packageName",packageName);
    velocityContext.put("basePackage",getPackagePrefix(packageName));
    velocityContext.put("author",genTable.getFunctionAuthor());
    velocityContext.put("datetime", DateUtils.getDate());
    velocityContext.put("pkColumn",genTable.getPkColumn());
    velocityContext.put("columns",genTable.getColumns());
    velocityContext.put("table",genTable);
    velocityContext.put("importList",getImportList(genTable.getColumns()));
    velocityContext.put("permissionPrefix",getPermissionPrefix(moduleName, businessName));

    return velocityContext;
  }

  /**
   * 获取权限前缀
   * @param moduleName 模块名称
   * @param businessName 业务名称
   * @return 返回权限前缀
   */
  public static Object getPermissionPrefix(String moduleName, String businessName) {
    return StringUtils.format("{}:{}", moduleName, businessName);
  }

  /**
   * 根据列类型获取导入包
   * @param columns 列集合
   * @return 返回需要导入的包列表
   */
  public static HashSet<String> getImportList(List<GenTableColumn> columns) {
    HashSet<String> importList = new HashSet<String>();
    columns.forEach(column -> {
      if(!column.isSuperColumn() && GenConstants.TYPE_DATE.equals(column.getJavaType())){
        importList.add("java.util.Date");
      }else if (!column.isSuperColumn() && GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
        importList.add("java.math.BigDecimal");
      }
    });
    return importList;
  }

  /**
   * 获取包前缀
   * @param packageName 包名称
   * @return 包前缀名称
   */
  public static String getPackagePrefix(String packageName) {
    return StringUtils.substring(packageName,0,packageName.lastIndexOf("."));
  }

}
