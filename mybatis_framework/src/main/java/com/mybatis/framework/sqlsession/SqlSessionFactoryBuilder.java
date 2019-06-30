package com.mybatis.framework.sqlsession;


import com.mybatis.framework.config.Configuration;
import com.mybatis.framework.config.XMLConfigParser;
import org.dom4j.Document;

import java.io.InputStream;
import java.io.Reader;

/**
 * 构建SqlSession工厂
 */
public class SqlSessionFactoryBuilder {

    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        this.configuration = new Configuration();
    }

    //方式一 字节流法
    public SqlSessionFactory build(InputStream inputStream) {
        /**
         * 解析全局配置文件，封装为Configuration
         * 通过InputStream流对象，去创建Document对象（dom4j）
         */
        Document document = DocumentReader.createDocumentByInputStream(inputStream);
        /**对全局配置文件进行解析*/
        XMLConfigParser xmlConfigParser = new XMLConfigParser(configuration);
        xmlConfigParser.parseConfiguration(document.getRootElement());
        return this.build();
    }

    //方式二 字符流法
    public SqlSessionFactory build(Reader reader) {
        Document document = DocumentReader.createDocumentByReader(reader);
        /**对全局配置文件进行解析*/
        XMLConfigParser xmlConfigParser = new XMLConfigParser(configuration);
        xmlConfigParser.parseConfiguration(document.getRootElement());
        return this.build();
    }


    private SqlSessionFactory build() {
        return new DefaultSqlSessionFactory(configuration);
    }
}
