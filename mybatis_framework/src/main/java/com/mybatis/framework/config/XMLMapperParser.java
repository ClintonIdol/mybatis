/**
 * Author:   LiXiaoPeng
 * Date:     2019/6/29 22:00
 * Description: 解析Mapper配置文件
 */
package com.mybatis.framework.config;

import org.dom4j.Element;

import java.util.List;

public class XMLMapperParser {

    private Configuration configuration;

    private String namespace;

    public XMLMapperParser(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(Element rootElement) {
        namespace = rootElement.attributeValue("namespace");
        List<Element> elements = rootElement.elements("select");
        //解析所有的select标签
        parseStatements(elements);
    }


    /**
     * 多个标签循环解析
     * @param elements
     */
    private void parseStatements(List<Element> elements) {
        for (Element element: elements) {
            parseStatement(element);
        }
    }

    /**
     * <select id="findUserById" parameterType="com.test.mybatis.po.User"
     *             resultType="com.test.mybatis.po.User" statementType="prepared">
     * 		SELECT * FROM user WHERE id = #{id}
     * 	</select>
     */
    private void parseStatement(Element element) {
        MapperStatement mapperStatement = new MapperStatement();
        String id = element.attributeValue("id");
        id = namespace.concat(".").concat(id);
        mapperStatement.setId(id);

        mapperStatement.setStatementType(element.attributeValue("statementType"));

        String parameterType = element.attributeValue("parameterType");
        mapperStatement.setParameterTypeClass(classLoad(parameterType));

        String resultType = element.attributeValue("resultType");
        mapperStatement.setResultTypeClass(classLoad(resultType));

        SqlSource sqlSource = new SqlSource(element.getTextTrim());
        mapperStatement.setSqlSource(sqlSource);

        configuration.addMapperStatementMap(id, mapperStatement);
    }

    /**
     * 根据字符串反射出类
     * @param parameterType
     * @return
     */
    private Class<?> classLoad(String parameterType) {
        if(parameterType == null || parameterType == "") {
            return null;
        }
        try {
            return Class.forName(parameterType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
