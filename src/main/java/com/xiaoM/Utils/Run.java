package com.xiaoM.Utils;

import java.util.*;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.xiaoM.Android.ResourceMonitoring;
import com.xiaoM.ReportUtils.TestListener;

import io.appium.java_client.AppiumDriver;

public class Run {
    private StringBuilder sb;
    private BaseDriver base;

    public void runCase(String DeviceName, String Type, String CaseName, String TestCategory, ExtentTest extentTest) throws Exception {
        AppiumDriver driver = null;
        Location location;
        switch (Type.toLowerCase()) {
            case "app":
                base = new BaseDriver();
                driver = base.setUpApp(DeviceName, extentTest);
                break;
            case "wap":
                base = new BaseDriver();
                driver = base.setUpWap(DeviceName, extentTest);
                break;
            default:
                extentTest.fail("请在 " + TestListener.TestCase + ".xlsx 中选择正确的测试类型：APP/WAP");
                throw new Exception();
        }
        extentTest.getModel().setStartTime(new Date());
        String[][] testStart = IOMananger.readExcelDataXlsx(TestListener.workbook,CaseName);
        if (testStart != null) {
            ResourceMonitoring RM = null;
            boolean StartRM = false;
            if (TestListener.Resource_Monitoring.toLowerCase().equals("true") && Type.toLowerCase().equals("app") && TestListener.DeviceType.toLowerCase().equals("android")) {
                RM = new ResourceMonitoring();
                RM.startMonitoring(DeviceName, TestCategory);
                StartRM = true;
            } else if (TestListener.Resource_Monitoring.toLowerCase().equals("true") && !Type.toLowerCase().equals("app")) {
                extentTest.fail("资源监控只适用于 Android 平台的APP");
                throw new Exception();
            } else if (TestListener.Resource_Monitoring.toLowerCase().equals("true") && !TestListener.DeviceType.toLowerCase().equals("android")) {
                extentTest.fail("资源监控只适用于 Android 平台的APP");
                throw new Exception();
            }
            Map<String, Object> returnMap = new HashMap<>();
            int b = 0;
            try {
                for (int a = 1; a < testStart.length; a++) {
                    b = a;
                    List<String> parameteres = new ArrayList<>(Arrays.asList(testStart[a]));
                    location = new Location();
                    location.setLocation(parameteres);
                    if (location.getIsRun().equals("YES")) {
                        sb = new StringBuilder();
                        String Step = location.getStep();
                        String Description = location.getDescription();
                        String Action = location.getAction();
                        String Key = location.getKey();
                        String Value = location.getValue();
                        String Parameter = location.getParameter();
                        if (Parameter.contains("${")) {
                            Parameter = Match.replaceKeys(returnMap, Parameter);
                            location.setParameter(Parameter);
                        }
                        if (Value.contains("${")) {
                            Value = Match.replaceKeys(returnMap, Value);
                            location.setValue(Value);
                        }
                        sb.append("[步骤]: " + Step + "\r\n");
                        sb.append("[步骤描述]: " + Description + "\r\n");
                        sb.append("[操作方式]:" + Action + "\r\n");
                        sb.append("[关键字]:" + Key + "\r\n");
                        sb.append("[属性值]:" + Value + "\r\n");
                        sb.append("[参数]：" + Parameter + "\r\n");
                        ElementAction elementAction = new ElementAction(driver, TestCategory, returnMap);
                        Object result = elementAction.action(location);
                        if (result != null) {
                            if (result.toString().toLowerCase().equals("false")) {
                                sb.append("[返回值]：False\r\n");
                                throw new Exception("返回值为 False");
                            }
                        }
                        sb.append("[返回值]：" + result);
                        returnMap.put(Step, result);
                        extentTest.log(Status.INFO, "<pre>" + sb.toString() + "</pre>");
                    }
                }
                if (StartRM) {
                    RM.stopMonitoring(DeviceName, TestCategory);
                }
            } catch (Exception e) {
                ScreenShot screenShot = new ScreenShot(driver);
                screenShot.setScreenName(TestCategory);
                screenShot.takeScreenshot();
                sb.append("[异常截图如下]：");
                extentTest.fail("<pre>" + sb.toString() + "</pre>", MediaEntityBuilder.createScreenCaptureFromPath(TestListener.screenMessageList.get(TestCategory)).build());
                extentTest.error(e);
                FailStep.dealWithFailStep(b, testStart, extentTest);
                throw e;
            } finally {
                extentTest.getModel().setEndTime(new Date());
                driver.quit();
                if (base.getAppiumServer().service != null || base.getAppiumServer().service.isRunning()) {
                    base.getAppiumServer().service.stop();
                }
            }
        } else {
            extentTest.fail("该测试用例:" + CaseName + "在 " + TestListener.TestCase + ".xlsx 中没有对应的命名的 sheet");
            throw new Exception();
        }
    }
}
