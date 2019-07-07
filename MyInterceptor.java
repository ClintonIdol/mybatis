/**
 * Author:   LiXiaoPeng
 * Date:     2019/7/7 14:36
 * Description: mybatis拦截器
 */
package com.test.mybatis.Interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;

//@Intercepts，其值是一个@Signature数组。@Intercepts用于表明当前的对象是一个Interceptor
//@Signature则表明要拦截的接口、方法以及对应的参数类型
//Mybatis拦截器只能拦截四种类型的接口：Executor、StatementHandler、ParameterHandler和ResultSetHandler。
// |--这是在Mybatis的Configuration中写死了的，如果要支持拦截其他接口就需要我们重写Mybatis的Configuration。Mybatis可以对这四个接口中所有的方法进行拦截
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
@SuppressWarnings({"unchecked", "rawtypes"})
public class MyInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement)invocation.getArgs()[0];
        Object parameter = null;
        if(invocation.getArgs().length > 0) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = ms.getBoundSql(parameter);
        Configuration configuration = ms.getConfiguration();
        String sqlId = ms.getId();
        String sql = getSql(configuration, boundSql, sqlId);
        System.out.println(sql);
        Object result = invocation.proceed();
        return result;
    }

    /**
     * 组装sql 使其返回路径+sql信息
     * @param configuration
     * @param boundSql
     * @param sqlId
     * @return
     */
    private static String getSql(Configuration configuration, BoundSql boundSql, String sqlId) {
        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        str.append(sqlId).append(": ").append(sql);
        return str.toString();
    }

    private static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " "); // sql语句中有多个空格是用一个空格替换
        if(!parameterMappings.isEmpty() && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if(typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) { //非parameterType 为Pojo、Collection、Map 即参数只有一个
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else { //parameterType 为Pojo、Collection、Map
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping: parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if(metaObject.hasGetter(propertyName)) {
                        Object value = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(value)));

                    } else if(boundSql.hasAdditionalParameter(propertyName)) {
                        Object additionalParameter = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(additionalParameter)));
                    } else {
                        sql = sql.replaceFirst("\\?", "缺失"); //打印出缺失，提醒该参数缺失并防止错位
                    }
                }
            }
        }
        return sql;
    }

    /**
     * 参数显示做调整
     * @param obj
     * @return
     */
    private static String getParameterValue(Object obj) {
        String value = "";
        if(obj instanceof String) {
            value = "'"+ obj + "'";
        } else if(obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'"+ formatter.format(obj) +"'";
        } else {
            if(obj != null) {
                value = obj.toString();
            }
        }
        return value;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        //do nothing
    }
}
