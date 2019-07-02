/**
 * Author:   LiXiaoPeng
 * Date:     2019/7/2 15:05
 * Description:
 */
package com.mybatis.framework.config;

import java.util.ArrayList;
import java.util.List;

public class BoundSql {
    /**
     * 解析出的sql eg: select id, name from user where id = ?
     */
    private String sql;

    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

    public BoundSql(String sql, List<ParameterMapping> parameterMappingList) {
        this.sql = sql;
        this.parameterMappingList = parameterMappingList;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void addParameterMappingList(ParameterMapping parameterMapping) {
        this.parameterMappingList.add(parameterMapping);
    }
}

