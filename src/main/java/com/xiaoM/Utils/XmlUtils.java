package com.xiaoM.Utils;

import com.xiaoM.Main.MainTest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

public class XmlUtils {
    public static Element readConfigXml() throws Exception {
        String testCase = MainTest.TestCase;
        //创建SAXReader对象
        SAXReader reader = new SAXReader();
        //读取文件 转换成Document
        Document document = reader.read(new File("./testCase/" + testCase + "/config.xml"));
        //获取根节点元素对象
        return document.getRootElement();
    }
}
