package com.xiaoM.BeginScript;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.xiaoM.Main.MainTest;
import com.xiaoM.Selenium.RunWebMode;
import com.xiaoM.Utils.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BeginWebScript {
    private Log log = new Log(this.getClass());
    public static ExtentReports extent = ExtentManager.createHtmlReportInstance();;
    public static XSSFWorkbook workbook;
    public static String[][] RunCase;//执行测试case
    public static Map<String, String[]> DataBaseConfig = new HashMap<>();
    public static String OS;
    public static String ProjectPath;//工程路径
    public static String TestCase;//测试用例所在的表
    public static String BrowserName;

    @BeforeSuite
    public void init() {
        //获取操作系统
        String os = System.getProperty("os.name");
        if (os.contains("Mac")) {
            OS = "MAC";
        } else if (os.contains("Windows")) {
            OS = "WINDOWS";
        } else if (os.contains("Linux")) {
            OS = "LINUX";
        }
        try {
            ProjectPath = new File(System.getProperty("user.dir")).getPath();// 工程根目录
            TestCase = MainTest.TestCase;
            BrowserName = MainTest.BrowserName;
            String CasePath = ProjectPath + "/testCase/" + TestCase + "/main.xlsx";
            workbook = IOMananger.getCaseExcel(CasePath);
            RunCase = IOMananger.runTime(workbook,"TestCases");//获取具体需要执行的测试用例
            DataBaseConfig = BaseConfig.getDataBaseConfigXlsx(workbook);//获取数据库配置
            MainTest.commonParam = IOMananger.getCommonParam(workbook);
        } catch (Exception e) {
            log.error("启动测试失败");
            ExtentTest extentTest = extent.createTest("启动测试失败");
            extentTest.fail(e);
            extent.flush();
            e.printStackTrace();
            System.exit(0);
        }
    }

    @DataProvider(parallel = true)
    public Object[][] TestCases() {
        return BeginWebScript.RunCase;
    }

    @Test(dataProvider = "TestCases")
    public void runCaserunCase(String ID, String Module, String CaseName,String Remark) throws Exception {
        String TestCategory = ID + "_" + Module + "_" + CaseName;
        log.info(TestCategory + " --- Start");
        ExtentTest extentTest = extent.createTest(TestCategory, Remark);
        extentTest.assignCategory(Module);
        try {
            new RunWebMode().runCase(ID, Module, CaseName,BrowserName, extentTest);
            log.info(TestCategory + " --- Pass");
        } catch (Exception e) {
            log.error(TestCategory + " --- Fail");
            e.printStackTrace();
            throw e;
        }
    }

    @AfterSuite
    public void afterSuite() {
        extent.flush();
        File file = new File("./Temp/");
        IOMananger.deleteDirectory(file);
    }
}
