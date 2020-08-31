package com.superman.framework.datasource;

import com.superman.common.config.DynamicDataSourceConfig;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Author: superman宁
 * @Description: 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

  public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources)
  {
    super.setDefaultTargetDataSource(defaultTargetDataSource);
    super.setTargetDataSources(targetDataSources);
    super.afterPropertiesSet();
  }

  @Override
  protected Object determineCurrentLookupKey() {
    return DynamicDataSourceConfig.getDataSourceType();
  }
}
