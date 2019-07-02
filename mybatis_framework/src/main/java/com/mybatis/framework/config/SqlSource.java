/**
 * Author:   LiXiaoPeng
 * Date:     2019/7/2 14:38
 * Description:
 */
package com.mybatis.framework.config;

import com.mybatis.framework.utils.GenericTokenParser;
import com.mybatis.framework.utils.ParameterMappingTokenHandler;

public class SqlSource {
    private String sqlText;

    public SqlSource(String sqlText) {
        this.sqlText = sqlText;
    }

    public BoundSql getBoundSql() {
        /**
         * 这里引用Mybatis源码中的解析包进行解析 重在思想
         */
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = genericTokenParser.parse(sqlText);
        return new BoundSql(sql, tokenHandler.getParameterMappings());
    }
}
