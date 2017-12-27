package com.xiaoM.ReportUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import org.testng.TestListenerAdapter;

import com.xiaoM.Utils.IOMananger;
import com.xiaoM.Utils.Log;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class TestListener extends TestListenerAdapter {
    public static String[][] RunCase;//执行测试case
    public static Map<String, String> screenMessageList = new HashMap<String, String>();
    public static Map<String, String> failMessageList = new HashMap<String, String>();
    public static Map<String, String> Category = new HashMap<String, String>();
    public static Map<String, String> logList = new HashMap<String, String>();
    public static Map<String, Long> RuntimeStart = new HashMap<String, Long>();
    public static Map<String, Long> RuntimeEnd = new HashMap<String, Long>();
    public static Map<String, String> RmPicture = new HashMap<String, String>();
    public static List<String> deviceList = new ArrayList<String>();
    public static String Log_Level;
    public static String DeviceType;//设备类型
    public static String ResetApp;//是否重置应用
    public static String AppName;//Android APP的文件名
    public static String Resource_Monitoring;
    public static String PackageName;//Android APP的包名
    public static String Activity;//Android APP的Activity
    public static String bundleId;//IOS应用的标识名
    public static String ProjectPath;//工程路径
    public static String TestCase;//测试用例所在的表
    public static String CasePath;

    //配置初始化
    static {
        //读取配置文件
        Properties pp = new Properties();
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream("config.properties"), "UTF-8");
            pp.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取操作系统
        String os = System.getProperty("os.name");
        if (os.contains("Mac")) {
            String appiumPath = "/usr/local/lib/node_modules/appium/build/lib/main.js";
            System.setProperty(AppiumServiceBuilder.APPIUM_PATH, appiumPath);
        }
        if(pp.getProperty("UING_DEVICES").contains(",")){
            String[] devices = pp.getProperty("UING_DEVICES").split(",");
            deviceList.addAll(Arrays.asList(devices));
        }else{
            deviceList.add(pp.getProperty("UING_DEVICES"));
        }
//        RunDevices = deviceList;
        Log_Level = pp.getProperty("LOG_LEVEL");
        ProjectPath = new File(System.getProperty("user.dir")).getPath();// 工程根目录
        TestCase = pp.getProperty("TESTCASE");
        CasePath = ProjectPath + "/testCase/" + TestCase + ".xlsx";
        DeviceType = pp.getProperty("DEVICE_TYPE");
        ResetApp = pp.getProperty("NORESET_APP");
        AppName = pp.getProperty("APP_NAME");
        Resource_Monitoring = pp.getProperty("RESOURCE_MONITORING");
        PackageName = pp.getProperty("APP_PACKAGENAME");
        Activity = pp.getProperty("APP_ACTIVITY");
        bundleId = pp.getProperty("BUNDIEID");
        //获取测试执行用例
        try {
            RunCase = IOMananger.runTime("TestCases", CasePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String logPath = ProjectPath + "/test-output/log/runLog.log";
        File path = new File(logPath);
        if (path.exists()) {
            path.delete();//删除日志文件
        }
    }
}
