package com.xiaoM.Utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

public class XmlUtils {
    public static Element readXml() throws DocumentException {
        //创建SAXReader对象
        SAXReader reader = new SAXReader();
        //读取文件 转换成Document
        Document  document = reader.read(new File("config.xml"));
        //获取根节点元素对象
        return document.getRootElement();
    }
    public static void main(String []args) throws DocumentException {
        Element config = readXml();
        System.out.println(config.elementText("Devices"));
    }
}
