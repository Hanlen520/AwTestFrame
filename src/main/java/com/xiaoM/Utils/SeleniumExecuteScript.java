package com.xiaoM.Utils;

import com.xiaoM.Main.MainTest;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeleniumExecuteScript {
    private WebDriver driver;

    public SeleniumExecuteScript(WebDriver driver) {
        this.driver = driver;
    }

    public Object runScript(Location location) throws Exception{
        String jarName = location.getParameter();
        String className = location.getValue().split("::")[0];
        String methodName = location.getValue().split("::")[1];
        Object result ;
            File file = new File("./testCase/" + MainTest.TestCase +"/Script/" + jarName);//类路径(包文件上一层)
            URLClassLoader loader = new URLClassLoader(new URL[]{file.toURI().toURL()});//创建类加载器
            Class<?> cls = loader.loadClass(className);//加载指定类，注意一定要带上类的包名
            Object obj = cls.newInstance();//初始化一个实例
            Pattern p = Pattern.compile("(?<=\\()(.+?)(?=\\))");
            Matcher m = p.matcher(methodName);
            String data = null;
            Object[] args;
            while (m.find()) {
                data = m.group().replace("\"","");
            }
            if (data != null) {
                if (data.contains("driver")) {
                    if (data.contains(",")) {
                        String[] datas = data.split(",");
                        args = new Object[datas.length];
                        args[0] = driver;
                        for (int i = 1; i < datas.length; i++) {
                            args[i] = datas[i];
                        }
                        @SuppressWarnings("rawtypes")
                        Class[] argsClass = new Class[args.length];
                        argsClass[0] = WebDriver.class;
                        for (int i = 1, j = args.length; i < j; i++) {
                            argsClass[i] = args[i].getClass();
                        }
                        result = cls.getMethod(methodName.split("\\(")[0], argsClass).invoke(obj, args);
                    } else {
                        methodName = methodName.replace("(\"driver\")", "");
                        result = cls.getMethod(methodName, WebDriver.class).invoke(obj, driver);
                    }
                } else {
                    if (data.contains(",")) {
                        args = data.split(",");
                        @SuppressWarnings("rawtypes")
                        Class[] argsClass = new Class[args.length];
                        for (int i = 0, j = args.length; i < j; i++) {
                            argsClass[i] = args[i].getClass();
                        }
                        result = cls.getMethod(methodName.split("\\(")[0], argsClass).invoke(obj, args);
                    } else {
                        result = cls.getMethod(methodName.split("\\(")[0], String.class).invoke(obj, data);
                    }
                }
            } else {
                methodName = methodName.replace("()", "");
                result = cls.getMethod(methodName).invoke(obj);
            }
        return result;
    }
}
