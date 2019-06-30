/**
 * Author:   LiXiaoPeng
 * Date:     2019/6/29 22:00
 * Description: 解析Mapper配置文件
 */
package com.mybatis.framework.config;

import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLMapperParser {
    private Configuration configuration;

    private Map<String, MapperStatement> mapperStatementMap;

    private MapperStatement mapperStatement;

    public XMLMapperParser(Configuration configuration) {
        this.configuration = configuration;
        mapperStatementMap = new HashMap<>();
        mapperStatement = new MapperStatement();
    }

    public Configuration parse(Element rootElement) {
        String namespace = rootElement.attributeValue("namespace");
        List<Element> elements = rootElement.elements("select");
        for (Element element: elements) {
            parseSqlTag(element, namespace);
        }
        configuration.setMapperStatementMap(mapperStatementMap);
        return configuration;
    }

    private void parseSqlTag(Element element, String namespace) {
        String id = element.attributeValue("id");
        mapperStatement.setId(id);
        mapperStatement.setParameterType(element.attributeValue("parameterType"));
        mapperStatement.setResultType(element.attributeValue("resultType"));
        mapperStatement.setStatementType(element.attributeValue("statementType"));
        String selectSql = (String)element.getData();
        selectSql.replaceAll("/t", "").replaceAll("/n", "");
        //mapperStatement.setSql();
        mapperStatementMap.put(namespace.concat(id), mapperStatement);
    }


}
