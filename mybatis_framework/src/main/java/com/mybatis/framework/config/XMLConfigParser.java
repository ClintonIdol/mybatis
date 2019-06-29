/**
 * Author:   LiXiaoPeng
 * Date:     2019/6/29 21:59
 * Description: 解析全局配置文件
 */
package com.mybatis.framework.config;

import com.mybatis.framework.sqlsession.DocumentReader;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigParser {

    private Configuration configuration;

    public XMLConfigParser(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration parseConfiguration(Element element) {
        parseEnvironments(element.element("environments"));

        parseMappers(element.element("mappers"));
        return configuration;
    }

    //<environments default="dev">
    private void parseEnvironments(Element element) {
        String resourceId = element.attributeValue("default");
        List<Element> elements = element.elements("environment");

        for (Element envElement: elements) {
            String envId = envElement.attributeValue("id");
            if(envId != null && envId.equals(resourceId)) {
                parseDataSource(envElement.element("dataSource"));
            }
        }
    }

    //<dataSource type="DBCP">
    private void parseDataSource(Element element) {
        String type = element.attributeValue("type");
        if (type == null || type.equals("")) {
            type = "DBCP";
        }

        List<Element> elements = element.elements("property");
        Properties properties = new Properties();
        for (Element propElement: elements) {
            properties.setProperty(propElement.attributeValue("name"), propElement.attributeValue("value"));
        }

        BasicDataSource dataSource = null;
        if("DBCP".equals(type)) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
        }
        configuration.setDataSource(dataSource);
    }

    //<mappers>
    private void parseMappers(Element element) {
        List<Element> elements = element.elements("mapper");
        for (Element mapperElement: elements) {
            parseMapper(mapperElement);
        }
    }

    //<mapper resource="mapper/UserMapper.xml"></mapper>
    private void parseMapper(Element mapperElement) {
        String resource = mapperElement.attributeValue("resource");
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        Document document = DocumentReader.createDocumentByInputStream(inputStream);
        XMLMapperParser xmlMapperParser = new XMLMapperParser(configuration);
        configuration = xmlMapperParser.parse(document.getRootElement());
    }
}
