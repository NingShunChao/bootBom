package com.superman.common.constant;

/**
 * @description: 代码生成通用常量
 * @author: superman宁
 **/
public class GenConstants {

  /** 单表（增删改查） */
  public static final String TPL_CRUD = "crud";

  /** 树表（增删改查） */
  public static final String TPL_TREE = "tree";

  /** 树编码字段 */
  public static final String TREE_CODE = "treeCode";

  /** 树父编码字段 */
  public static final String TREE_PARENT_CODE = "treeParentCode";

  /** 树名称字段 */
  public static final String TREE_NAME = "treeName";

  /** 数据库字符串类型 */
  public static final String[] COLUMN_TYPE_STR = { "char", "varchar", "narchar", "varchar2", "tinytext", "text",
      "mediumtext", "longtext" };

  /** 数据库时间类型 */
  public static final String[] COLUMN_TYPE_TIME = { "datetime", "time", "date", "timestamp" };

  /** 数据库数字类型 */
  public static final String[] COLUMN_TYPE_NUMBER = { "tinyint", "smallint", "mediumint", "int", "number", "integer",
      "bigint", "float", "float", "double", "decimal" };

  /** 页面不需要编辑字段 */
  public static final String[] COLUMNNAME_NOT_EDIT = { "id", "create_by", "create_time", "del_flag" };


}
