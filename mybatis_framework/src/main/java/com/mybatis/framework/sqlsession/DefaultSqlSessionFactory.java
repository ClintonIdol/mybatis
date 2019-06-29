/**
 * Author:   LiXiaoPeng
 * Date:     2019/6/29 20:11
 * Description:
 */
package com.mybatis.framework.sqlsession;

import com.mybatis.framework.config.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    /**
     * 真正实现SqlSession接口的代码
     * 这里会使用jdbc那套东西
     * @return
     */
    public SqlSession openSession() {
        //TODO
        return null;
    }

    //构造方法注入Configuration对象
    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }
}
