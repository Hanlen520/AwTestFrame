package com.xiaoM.BeginScript;

import com.aventstack.extentreports.ExtentTest;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.UseDevice;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xiaoM.ReportUtils.TestListener;
import com.xiaoM.Utils.Run;

public class BeginScript {
    private Log log = new Log(this.getClass());

    @DataProvider(parallel = true)
    public Object[][] TestCases() {
        return TestListener.RunCase;
    }

    @Test(dataProvider = "TestCases")
    public void runCase(String ID, String Type, String Description, String CaseName) throws Exception {
        String RunDevice = UseDevice.getDevice();//获取设备
        String TestCategory = ID + "_" + RunDevice + "_" + CaseName;
        log.info(TestCategory + " --- Start");
        ExtentTest extentTest = TestListener.extent.createTest(TestCategory, Description);//测试报告增加一个节点
        extentTest.assignCategory(RunDevice);//根据设备来分类
        try {
            new Run().runCase(RunDevice, Type, CaseName, TestCategory, extentTest);
        } catch (Exception e) {
            log.error(TestCategory + " --- Fail");
            extentTest.error(TestCategory + " --- Fail");
            throw e;
        }finally {
            UseDevice.addDevice(RunDevice);
        }
        log.info(TestCategory + " --- Pass");
        extentTest.pass(TestCategory + " --- Pass");

    }

    @AfterSuite
    public void afterSuite() {
        TestListener.extent.flush();
    }
}
