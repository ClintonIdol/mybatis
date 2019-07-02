package com.mybatis.framework.sqlsession;

import com.mybatis.framework.config.Configuration;
import com.mybatis.framework.config.MapperStatement;

public interface Executor {
    <T> T query(Configuration configuration, MapperStatement mapperStatement, Object object);
}
