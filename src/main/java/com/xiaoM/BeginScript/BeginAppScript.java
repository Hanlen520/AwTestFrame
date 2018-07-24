package com.xiaoM.BeginScript;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.xiaoM.Appium.RunAppMode;
import com.xiaoM.Appium.UseDevice;
import com.xiaoM.Main.MainTest;
import com.xiaoM.Utils.*;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Element;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.*;

public class BeginAppScript {
    private Log log = new Log(this.getClass());
    public static ExtentReports extent = ExtentManager.createHtmlReportInstance();//初始化测试报告
    public static XSSFWorkbook workbook;
    public static XSSFWorkbook DeviceConfig;
    public static Map<String, String[]> DataBaseConfig = new HashMap<>();
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
    public static String TestType;

    @BeforeSuite
    public void init(){
        try {
            Element config = XmlUtils.readConfigXml();
            String os = System.getProperty("os.name");
            if (os.contains("Mac")) {
                String appiumPath = "/usr/local/lib/node_modules/appium/build/lib/main.js";
                System.setProperty(AppiumServiceBuilder.APPIUM_PATH, appiumPath);
            }
            if(config.element("MobileTest").elementText("Device").contains(",")){
                String[] devices = config.element("MobileTest").elementText("Device").split(",");
                deviceList.addAll(Arrays.asList(devices));
            }else{
                deviceList.add(config.element("MobileTest").elementText("Device"));
            }
            Log_Level = config.element("MobileTest").elementText("Appium-Server");
            ProjectPath = new File(System.getProperty("user.dir")).getPath();// 工程根目录
            TestCase = MainTest.TestCase;
            CasePath = ProjectPath + "/testCase/" + TestCase + "/main.xlsx";
            TestType = MainTest.TestType;
            DeviceType = config.element("MobileTest").elementText("DeviceType");
            ResetApp = config.element("MobileTest").element("AndroidConfig").elementText("NoRestApp");
            AppName = config.element("MobileTest").element("AndroidConfig").elementText("AppName");
            Resource_Monitoring = config.element("MobileTest").element("AndroidConfig").elementText("ResourceMonitoring");
            PackageName = config.element("MobileTest").element("AndroidConfig").elementText("PackageName");
            Activity = config.element("MobileTest").element("AndroidConfig").elementText("Activity");
            bundleId = config.element("MobileTest").element("IOSConfig").elementText("bundleId");
            workbook = IOMananger.getCaseExcel(CasePath);//获取测试用例Excel内容
            DeviceConfig = IOMananger.getDeviceExcel();//获取测试设备Excel内容
            RunCase = IOMananger.runTime(workbook,"TestCases");//获取具体需要执行的测试用例
            DataBaseConfig = BaseConfig.getDataBaseConfigXlsx(workbook);//获取数据库配置
        } catch (Exception e) {
            ExtentTest extentTest = extent.createTest("初始化测试失败");
            extentTest.fail(e);
        }
    }

    @DataProvider(parallel = true)
    public Object[][] TestCases() {
        return RunCase;
    }

    @Test(dataProvider = "TestCases")
    public void runCase(String ID, String Module, String CaseName,String Remark){
        String RunDevice = UseDevice.getDevice();//获取设备
        String TestCategory = ID + "_" + Module + "_" + CaseName;
        log.info(TestCategory + " --- Start");
        ExtentTest extentTest = extent.createTest(TestCategory, Remark);//测试报告增加一个节点
        extentTest.assignCategory(Module);//根据模块来分类
        try {
            new RunAppMode().runCase(RunDevice, TestType, CaseName, TestCategory, extentTest);
            log.info(TestCategory + " --- Pass");
        } catch (Exception e) {
            log.error(TestCategory + " --- Fail");
        }finally {
            UseDevice.addDevice(RunDevice);
        }
    }

    @AfterSuite
    public void afterSuite() {
        File file = new File("./Temp/");
        IOMananger.deleteDirectory(file);
        extent.flush();
    }
}
