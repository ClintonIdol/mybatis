/**
 * Author:   LiXiaoPeng
 * Date:     2019/6/29 19:00
 * Description:
 */
package com.test.mybatis.dao;

import com.mybatis.framework.sqlsession.SqlSession;
import com.mybatis.framework.sqlsession.SqlSessionFactory;
import com.test.mybatis.po.User;

public class UserDaoImpl implements UserDao {

    private SqlSessionFactory sqlSessionFactory;

    //  注入SqlSessionFactory
    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public User queryUserById(User reqUser) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        String statementId = "test.findUserById";
        User user = sqlSession.selectOne(statementId, reqUser);
        return user;
    }
}
