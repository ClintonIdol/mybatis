/**
 * Author:   LiXiaoPeng
 * Date:     2019/7/2 15:15
 * Description:
 */
package com.mybatis.framework.sqlsession;

import com.mybatis.framework.config.Configuration;
import com.mybatis.framework.config.MapperStatement;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    @Override
    public <T> T selectOne(String statementId, Object object) {
        // 真正与数据库 进行交互
        Executor executor = new SimpleExecutor();
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        return executor.query(configuration, mapperStatement, object);
    }

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }
}
