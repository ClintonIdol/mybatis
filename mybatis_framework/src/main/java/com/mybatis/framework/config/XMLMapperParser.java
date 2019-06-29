/**
 * Author:   LiXiaoPeng
 * Date:     2019/6/29 22:00
 * Description: 解析Mapper配置文件
 */
package com.mybatis.framework.config;

import org.dom4j.Element;

public class XMLMapperParser {
    private Configuration configuration;

    public XMLMapperParser(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration parse(Element rootElement) {
        //TODO 解析Mapper配置文件
        return configuration;
    }
}
