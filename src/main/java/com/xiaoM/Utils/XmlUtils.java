package com.xiaoM.Utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

public class XmlUtils {
    public static Element readConfigXml() throws Exception {
        String testCase = getTestCase();
        //创建SAXReader对象
        SAXReader reader = new SAXReader();
        //读取文件 转换成Document
        Document document = reader.read(new File("./testCase/" + testCase + "/config.xml"));
        //获取根节点元素对象
        return document.getRootElement();
    }

    public static String getTestCase() throws Exception {
        SAXReader reader = new SAXReader();
        //解决dom4j解析xml提示Connection timed out错误
        reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        reader.setValidation(false);
        Document document = reader.read(new File("StartTest.xml"));
        Element StartTest = document.getRootElement();
        String TestCase = StartTest.element("test").attributeValue("name");
        return TestCase;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getTestCase());
    }
}
