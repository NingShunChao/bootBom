package com.superman.generator.util;

import com.superman.constant.GenConstants;
import com.superman.utils.StringUtils;
import com.superman.generator.config.GenConfig;
import com.superman.generator.domain.GenTable;
import com.superman.generator.domain.GenTableColumn;
import java.util.Arrays;
import org.apache.commons.lang3.RegExUtils;

/**
 * @description: 代码生成器工具
 * @author: superman宁
 **/
public class GenUtils {

  /**
   * 初始化表信息
   * @param genTable
   * @param operateName
   */
  public static void initTable(GenTable genTable, String operateName){
    genTable.setClassName(convertClassName(genTable.getTableName()));
    genTable.setPackageName(GenConfig.getPackageName());
    genTable.setModuleName(getModuleName(GenConfig.getPackageName()));
    genTable.setBusinessName(getBusinessName(genTable.getTableName()));
    genTable.setFunctionName(replaceText(genTable.getTableComment()));
    genTable.setFunctionAuthor(GenConfig.getAuthor());
    genTable.setCreateBy(operateName);
  }

  /**
   * 初始化列属性字段
   * @param genColumn
   * @param genTable
   */
  public static void initColumnField(GenTableColumn genColumn,GenTable genTable){
    String dataType = getDbType(genColumn.getColumnType());
    String columnName = genColumn.getColumnName();
    genColumn.setTableId(genTable.getTableId());
    // 设置java字段名
    genColumn.setJavaField(StringUtils.toCamelCase(columnName));
    // 字符类型
    if(arraysContains(GenConstants.COLUMNTYPE_STR,dataType)){
      genColumn.setJavaType(GenConstants.TYPE_STRING);
      // 字符串长度超过500设置为文本域
      Integer columnLength = getColumnLength(genColumn.getColumnType());
      String htmlType = columnLength >= 500 ? GenConstants.HTML_TEXTAREA : GenConstants.HTML_INPUT;
      genColumn.setHtmlType(htmlType);
    }
    // 时间类型
    else if(arraysContains(GenConstants.COLUMNTYPE_TIME, dataType)){
      genColumn.setJavaType(GenConstants.TYPE_DATE);
      genColumn.setHtmlType(GenConstants.HTML_DATETIME);
    }
    // 数字类型
    else if(arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType)){
      genColumn.setHtmlType(GenConstants.HTML_INPUT);
      // 如果是浮点型
      String[] str = StringUtils.split(StringUtils.substringBetween(genColumn.getColumnType(), "(", ")"), ",");
      if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
        genColumn.setJavaType(GenConstants.TYPE_DOUBLE);
      }
      // 如果是整形
      else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
        genColumn.setJavaType(GenConstants.TYPE_INTEGER);
      }
      // 长整形
      else {
        genColumn.setJavaType(GenConstants.TYPE_LONG);
      }
    }

    // 插入字段（默认所有字段都需要插入）
    genColumn.setIsInsert(GenConstants.REQUIRE);

    // 编辑字段
    if(!arraysContains(GenConstants.COLUMNNAME_NOT_EDIT, columnName) && !genColumn.isPk()){
      genColumn.setIsEdit(GenConstants.REQUIRE);
    }

    // 列表字段
    if(!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !genColumn.isPk()){
      genColumn.setIsList(GenConstants.REQUIRE);
    }

    // 查询字段
    if(!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !genColumn.isPk()){
      genColumn.setIsList(GenConstants.REQUIRE);
    }

    // 查询字段类型
    if(!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !genColumn.isPk()){
      genColumn.setQueryType(GenConstants.QUERY_LIKE);
    }

    // 状态字段设置单选框
//    if (StringUtils.endsWithIgnoreCase(columnName, "status"))
//    {
//      genColumn.setHtmlType(GenConstants.HTML_RADIO);
//    }
    // 类型&性别字段设置下拉框
//    else if (StringUtils.endsWithIgnoreCase(columnName, "type")
//        || StringUtils.endsWithIgnoreCase(columnName, "sex"))
//    {
//      genColumn.setHtmlType(GenConstants.HTML_SELECT);
//    }


  }

  /**
   * 获取字段长度
   * @param columnType 列类型
   * @return 截取后的列类型
   */
  public static Integer getColumnLength(String columnType) {
    if (StringUtils.indexOf(columnType, "(") > 0) {
      String length = StringUtils.substringBetween(columnType, "(", ")");
      return Integer.valueOf(length);
    } else {
      return 0;
    }
  }

  /**
   * 校验数组是否包含指定值
   * @param arr 数组
   * @param targetValue 目标值
   * @return 是否包含
   */
  public static boolean arraysContains(String[] arr, String targetValue){
    return Arrays.asList(arr).contains(targetValue);
  }

  /**
   * 获取数据库类型字段
   * @param columnType  列类型
   * @return 截取后的列类型
   */
  public static String getDbType(String columnType) {
    if (StringUtils.indexOf(columnType, "(") > 0) {
      return StringUtils.substringBefore(columnType, "(");
    }
    else {
      return columnType;
    }
  }

  /**
   * 关键字替换
   * @param tableComment 需要被替换的名字
   * @return
   */
  public static String replaceText(String tableComment) {
    return RegExUtils.replaceAll(tableComment,"(?:表|其他)","");
  }

  /**
   * 获取业务名
   * @param tableName 表名
   * @return 业务名
   */
  public static String getBusinessName(String tableName) {
    return StringUtils.substring(tableName,tableName.lastIndexOf("_")+1,tableName.length());
  }

  /**
   * 获取模块名
   * @param packageName 包名
   * @return 模块名
   */
  public static String getModuleName(String packageName) {
    int lastIndex = packageName.lastIndexOf(".");
    return StringUtils.substring(packageName,packageName.lastIndexOf(".")+1,packageName.length());
  }

  /**
   * 表名转换成Java类名
   * @param tableName 表名称
   * @return 类名
   */
  public static String convertClassName(String tableName) {
    boolean autoRemovePre = GenConfig.getAutoRemovePre();
    String tablePrefix = GenConfig.getTablePrefix();
    // 如果自动去除表前缀并且表前缀不为空
    if(autoRemovePre && StringUtils.isNotEmpty(tablePrefix)){
      String[] searchList = StringUtils.split(tablePrefix, ",");
      tableName = replaceFirst(tableName, searchList);
    }
    return StringUtils.convertToCamelCase(tableName);
  }

  /**
   * 批量替换前缀
   * @param tableName 替换值
   * @param searchList 替换列表
   * @return
   */
  public static String replaceFirst(String tableName, String[] searchList) {
    String newText = tableName;
    for(String search : searchList){
      if(tableName.startsWith(search)){
        newText = tableName.replaceFirst(search,"");
      }
    }
    return newText;
  }

}
