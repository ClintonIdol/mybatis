package com.mybatis.framework.sqlsession;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.io.Reader;

public class DocumentReader {

	/**
	 * 利用字节流创建Document对象
	 * 
	 * @param inputStream
	 * @return
	 */
	public static Document createDocumentByInputStream(InputStream inputStream) {
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(inputStream);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * 利用字符流创建Document对象
	 * @param reader
	 * @return
	 */
	public static Document createDocumentByReader(Reader reader) {
		Document document = null;
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(reader);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return  document;
	}

}
