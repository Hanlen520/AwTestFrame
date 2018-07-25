package com.xiaoM.Main;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTest {
    public static String TestCase;
    public static String TestType;
    private static int Thread = 1;
    public static String OS;
    public static String BrowserName;
    public static String Devcie;
    public static String bundleId;
    public static String NoRestApp;
    public static String PackageName;
    public static String Activity;
    public static String AppName;
    public static String AppiumServer = "INFO";
    public static Map<String, String> screenMessageList = new HashMap<>();

    public static void main(String[] args) {
        //args = new String[] {"-testcase", "163WebMail","-type", "web", "-browser", "chrome"};
        for (int i = 0; i < args.length; i++) {
            switch (args[i].toLowerCase()) {
                case "-testcase":
                    TestCase = args[i + 1];
                    break;
                case "-type":
                    TestType = args[i + 1];
                    break;
                case "-thread":
                    Thread = Integer.valueOf(args[i + 1]);
                    break;
                case "-os":
                    OS = args[i + 1];
                    break;
                case "-browser":
                    BrowserName = args[i + 1];
                    break;
                case "-device":
                    Devcie = args[i + 1];
                    break;
                case "-bundleid":
                    bundleId = args[i + 1];
                    break;
                case "-norest":
                    NoRestApp = args[i + 1];
                    break;
                case "-packagename":
                    PackageName = args[i + 1];
                    break;
                case "-activity":
                    Activity = args[i + 1];
                case "-appname":
                    AppName = args[i + 1];
                    break;
                case "-debug":
                    AppiumServer = args[i + 1].toLowerCase().equals("true")?"DEBUG":"INFO";
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
