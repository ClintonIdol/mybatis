/**
 * Author:   LiXiaoPeng
 * Date:     2019/6/29 20:16
 * Description:
 */
package com.mybatis.framework.config;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置文件
 */
public class Configuration {

    private DataSource dataSource;

    private Map<String, MapperStatement> mapperStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MapperStatement> getMapperStatementMap() {
        return mapperStatementMap;
    }

    public void addMapperStatementMap(String id, MapperStatement mapperStatement) {
        this.mapperStatementMap.put(id, mapperStatement);
    }
}
