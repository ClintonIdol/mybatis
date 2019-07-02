import com.mybatis.framework.sqlsession.SqlSessionFactory;
import com.mybatis.framework.sqlsession.SqlSessionFactoryBuilder;
import com.test.mybatis.po.User;
import com.test.mybatis.dao.UserDao;
import com.test.mybatis.dao.UserDaoImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * Author:   LiXiaoPeng
 * Date:     2019/6/29 19:02
 * Description:
 */

public class UserDaoTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws Exception {

        String resource = "SqlMapConfig.xml";
        //通过类加载器加载全局配置文件
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testQueryUserById() {
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);
        User reqUser = new User(1);
        User user = userDao.queryUserById(reqUser);
        System.out.print(user);
    }
}
