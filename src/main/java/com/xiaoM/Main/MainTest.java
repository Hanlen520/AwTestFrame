package com.xiaoM.Main;

import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTest {
    public static String TestCase;
    public static String TestType;
    public static Map<String, String> screenMessageList = new HashMap<>();

    public static void main(String []args){
        TestCase = "163WebMail";
        TestType = "WEB";
        XmlSuite suite = new XmlSuite();
        suite.setName("AWTestFrame");
        suite.setDataProviderThreadCount(1);//设置线程数
        XmlTest test = new XmlTest(suite);
        test.setName(TestCase);
        List<XmlClass> classes = new ArrayList<>();
        switch (TestType.toLowerCase()){
            case "web":
                classes.add(new XmlClass("com.xiaoM.BeginScript.BeginWebScript"));
                break;
            case "app":
                classes.add(new XmlClass("com.xiaoM.BeginScript.BeginAppScript"));
                break;
            case "wap":
                classes.add(new XmlClass("com.xiaoM.BeginScript.BeginAppScript"));
                break;
        }
        test.setXmlClasses(classes);
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        TestNG TestStart = new TestNG();
        TestStart.setXmlSuites(suites);
        TestStart.setUseDefaultListeners(false);//不使用默认监听器
        TestStart.run();
    }
}
