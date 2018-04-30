package com.xiaoM.ReportUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.xiaoM.Utils.BaseConfig;
import com.xiaoM.Utils.ExtentManager;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.TestListenerAdapter;

import com.xiaoM.Utils.IOMananger;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class TestListener extends TestListenerAdapter {
    public static ExtentReports extent;
    public static XSSFWorkbook workbook;
    public static XSSFWorkbook DeviceConfig;
    public static Map<String, String[]> DataBaseConfig = new HashMap<>();
    /*public static Map<String, String[]> OcrConfig = new HashMap<>();*/
    public static String[][] RunCase;//执行测试case
    public static Map<String, String> screenMessageList = new HashMap<>();
    public static Map<String, String> logList = new HashMap<>();
    public static Map<String, String> RmPicture = new HashMap<>();
    public static List<String> deviceList = new ArrayList<>();
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
        try {
            //读取配置文件
            Properties pp = new Properties();
            InputStreamReader reader = new InputStreamReader(new FileInputStream("config.properties"), "UTF-8");
            pp.load(reader);
            //获取操作系统
            String os = System.getProperty("os.name");
            if (os.contains("Mac")) {
                String appiumPath = "/usr/local/lib/node_modules/appium/build/lib/main.js";
                System.setProperty(AppiumServiceBuilder.APPIUM_PATH, appiumPath);
            }
            //获取测试设备
            if(pp.getProperty("UING_DEVICES").contains(",")){
                String[] devices = pp.getProperty("UING_DEVICES").split(",");
                deviceList.addAll(Arrays.asList(devices));
            }else{
                deviceList.add(pp.getProperty("UING_DEVICES"));
            }
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

            extent = ExtentManager.createHtmlReportInstance();//初始化测试报告
            workbook = IOMananger.getCaseExcel(CasePath);//获取测试用例Excel内容
            DeviceConfig = IOMananger.getDeviceExcel();//获取测试设备Excel内容
            RunCase = IOMananger.runTime("TestCases");//获取具体需要执行的测试用例
            DataBaseConfig = BaseConfig.getDataBaseConfigXlsx();//获取数据库配置
            /*OcrConfig = BaseConfig.getOcrConfigXlsx();//获取百度在线文字识别配置*/

        } catch (Exception e) {
            ExtentTest extentTest = extent.createTest("启动测试失败");
            extentTest.fail(e.getMessage());
            extent.flush();
            System.exit(0);
        }
    }
}
