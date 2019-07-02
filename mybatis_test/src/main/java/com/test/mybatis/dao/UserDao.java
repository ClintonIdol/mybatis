/**
 * Author:   LiXiaoPeng
 * Date:     2019/6/29 18:59
 * Description:
 */
package com.test.mybatis.dao;

import com.test.mybatis.po.User;

public interface UserDao {
    User queryUserById(User user);
}
