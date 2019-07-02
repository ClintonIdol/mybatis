/**
 * Author:   LiXiaoPeng
 * Date:     2019/7/2 15:37
 * Description:
 */
package com.mybatis.framework.sqlsession;

import com.mybatis.framework.config.BoundSql;
import com.mybatis.framework.config.Configuration;
import com.mybatis.framework.config.MapperStatement;
import com.mybatis.framework.config.ParameterMapping;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

public class SimpleExecutor implements Executor {

    @Override
    public <T> T query(Configuration configuration, MapperStatement mapperStatement, Object object) {
        Connection connection = null;
        Object returnObject = null;

        try {
            //获取连接
            connection = configuration.getDataSource().getConnection();

            //获取sql语句
            BoundSql boundSql = mapperStatement.getSqlSource().getBoundSql();
            String sql = boundSql.getSql();

            //获取statementType
            String statementType = mapperStatement.getStatementType();
            if("prepared".equals(statementType)) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                //设置参数
                List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
                //获取入参类型
                Class<?> parameterTypeClass = mapperStatement.getParameterTypeClass();
                if(parameterTypeClass == Integer.class || parameterTypeClass == Float.class) {
                    // 8中基本类型 不需要参数映射 直接使用
                    preparedStatement.setObject(1, object);
                } else {
                    //Map POJO 类型
                    for (int i=0,size=parameterMappingList.size(); i<size; i++) {
                        ParameterMapping parameterMapping = parameterMappingList.get(i);
                        //得到属性名称
                        String name = parameterMapping.getName();
                        //反射获取属性值
                        Field field = parameterTypeClass.getDeclaredField(name);
                        //设置属性暴力访问
                        field.setAccessible(true);

                        Object o = field.get(object);
                        preparedStatement.setObject(i+1, o);
                    }

                    ResultSet resultSet = preparedStatement.executeQuery();
                    //获取返回参数类型
                    Class<?> resultTypeClass = mapperStatement.getResultTypeClass();

                    while(resultSet.next()) {
                        //构建返回对象
                        Object resultObj = resultTypeClass.newInstance();
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        int count = metaData.getColumnCount();

                        for(int i=1; i<=count; i++) {
                            String columnName = metaData.getColumnName(i);
                            Field declaredField = resultTypeClass.getDeclaredField(columnName);
                            declaredField.setAccessible(true);
                            declaredField.set(resultObj, resultSet.getObject(columnName));
                        }
                        returnObject = resultObj;
                    }
                }

            } else {
                // TODO 处理其他方式statement
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T)returnObject;
    }

    /**
     * connection conn = this.getconnection();
     *     string strsql = "select emp_id from employee where emp_id = ?";
     *     preparedstatement pstmt = conn.preparestatement(strsql);
     *     pstmt.setstring(1,"pma42628m");
     *     resultset rs = pstmt.executequery();
     *
     *
     *     while(rs.next()){
     *        string fname = rs.getstring("emp_id");
     *        system.out.println("the fname is " + fname);
     */
}
