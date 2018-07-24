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
    public static String TestCase ;
    public static String TestType ;
    private static int Thread = 1;
    public static Map<String, String> screenMessageList = new HashMap<>();

    public static void main(String[] args) {
        Map<String, String> startArgs = new HashMap<>();
        for (int i = 0; i <= args.length / 2; i++) {
            startArgs.put(args[i], args[i + 1]);
            i++;
        }
        for (String key : startArgs.keySet()) {
            switch (key.toLowerCase()) {
                case "-c":
                    TestCase = startArgs.get("-c");
                    break;
                case "-t":
                    TestType = startArgs.get("-t");
                    break;
                case "-r":
                    Thread = Integer.valueOf(startArgs.get("-r"));
                    break;
            }
        }
        XmlSuite suite = new XmlSuite();
        suite.setName("AWTestFrame");
        suite.setDataProviderThreadCount(Thread);//设置线程数
        XmlTest test = new XmlTest(suite);
        test.setName(TestCase);
        List<XmlClass> classes = new ArrayList<>();
        switch (TestType.toLowerCase()) {
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
        TestStart.setUseDefaultListeners(false);
        TestStart.run();
    }
}
