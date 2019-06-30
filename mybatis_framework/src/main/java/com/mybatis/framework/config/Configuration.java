/**
 * Author:   LiXiaoPeng
 * Date:     2019/6/29 20:16
 * Description:
 */
package com.mybatis.framework.config;

import org.apache.commons.dbcp.BasicDataSource;

import java.util.Map;

/**
 * 全局配置文件
 */
public class Configuration {
    //这里有DataSource信息 statement类路径信息
    private BasicDataSource dataSource;

    private Map<String, MapperStatement> mapperStatementMap;

    public BasicDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MapperStatement> getMapperStatementMap() {
        return mapperStatementMap;
    }

    public void setMapperStatementMap(Map<String, MapperStatement> mapperStatementMap) {
        this.mapperStatementMap = mapperStatementMap;
    }
}
