package com.xiaoM.Main;

import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 打包用
 */
public class MainTest {
    public static void main(String []args){
        TestNG TestStart = new TestNG();
        Parser parser = new Parser("./StartTest.xml");
        List<XmlSuite> suites = new ArrayList<>();
        try {
            suites = parser.parseToList();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestStart.setXmlSuites(suites);
        TestStart.setUseDefaultListeners(false);
        TestStart.run();
    }
}
