package com.xiaoM.Appium;

import java.util.*;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.xiaoM.BeginScript.BeginAppScript;
import com.xiaoM.Driver.AppBaseDriver;
import com.xiaoM.Appium.Android.ResourceMonitoring;
import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Main.MainTest;
import com.xiaoM.Utils.FailStep;
import com.xiaoM.Utils.IOMananger;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Match;

public class RunAppMode {
    private StringBuilder sb;
    private AppBaseDriver base;
    private String FailAction;

    public void runCase(String DeviceName, String Type, String CaseName, String TestCategory, ExtentTest extentTest) throws Exception {
        AppiumXMDriver driver;
        Location location;
        switch (Type.toLowerCase()) {
            case "app":
                base = new AppBaseDriver();
                driver = base.setUpApp(DeviceName, extentTest);
                break;
            case "wap":
                base = new AppBaseDriver();
                driver = base.setUpWap(DeviceName, extentTest);
                break;
            default:
                extentTest.fail("请在 " + BeginAppScript.TestCase + ".xlsx 中选择正确的测试类型：APP/WAP");
                throw new Exception();
        }
        extentTest.getModel().setStartTime(new Date());
        String[][] testStart = IOMananger.readExcelDataXlsx(BeginAppScript.workbook, CaseName);
        if (testStart != null) {
            /*ResourceMonitoring RM = null;
            boolean StartRM = false;
            if (BeginAppScript.Resource_Monitoring.toLowerCase().equals("true") && Type.toLowerCase().equals("app") && BeginAppScript.DeviceType.toLowerCase().equals("android")) {
                RM = new ResourceMonitoring();
                RM.startMonitoring(DeviceName, TestCategory);
                StartRM = true;
            } else if (BeginAppScript.Resource_Monitoring.toLowerCase().equals("true") && !Type.toLowerCase().equals("app")) {
                extentTest.fail("资源监控只适用于 Android 平台的APP");
                throw new Exception();
            } else if (BeginAppScript.Resource_Monitoring.toLowerCase().equals("true") && !BeginAppScript.DeviceType.toLowerCase().equals("android")) {
                extentTest.fail("资源监控只适用于 Android 平台的APP");
                throw new Exception();
            }*/
            Map<String, Object> returnMap = new HashMap<>();
            int b = 0;
            try {
                for (int a = 1; a < testStart.length; a++) {
                    b = a;
                    List<String> parameteres = new ArrayList<>(Arrays.asList(testStart[a]));
                    location = new Location();
                    location.setLocation(parameteres);
                    if (location.getIsRun().toLowerCase().equals("y")) {
                        sb = new StringBuilder();
                        String Step = location.getStep();
                        String Description = location.getDescription();
                        String Action = location.getAction();
                        String Value = location.getValue();
                        String Parameter = location.getParameter();
                        FailAction = Action;
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
                        sb.append("[关键字]:" + Action + "\r\n");
                        sb.append("[属性值]:" + Value + "\r\n");
                        sb.append("[参数]：" + Parameter + "\r\n");
                        AppiumAction appiumAction = new AppiumAction(driver, TestCategory, returnMap, extentTest, DeviceName);
                        Object result = appiumAction.action(location);
                        sb.append("[返回值]：" + result);
                        returnMap.put(Step, result);
                        if (result.toString().toLowerCase().equals("false")) {
                            extentTest.log(Status.FAIL, "<pre>" + sb.toString() + "</pre>");
                        } else if(Action.toLowerCase().contains("check")) {
                            extentTest.log(Status.PASS, "<pre>" + sb.toString() + "</pre>");
                        }else if(Action.toLowerCase().equals("module")){
                        }else{
                            extentTest.log(Status.INFO, "<pre>" + sb.toString() + "</pre>");
                        }
                    }
                }
                /*if (StartRM) {
                    RM.stopMonitoring(DeviceName, TestCategory);
                }*/
            } catch (Exception e) {
                if (!FailAction.toLowerCase().equals("module")){
                    AppiumScreenShot screenShot = new AppiumScreenShot(driver);
                    screenShot.setScreenName(TestCategory);
                    screenShot.takeScreenshot();
                    sb.append("[异常截图如下]：");
                    extentTest.fail("<pre>" + sb.toString() + "</pre>", MediaEntityBuilder.createScreenCaptureFromPath(BeginAppScript.screenMessageList.get(TestCategory)).build());
                    extentTest.error(e);
                }
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
            extentTest.fail("该测试用例:" + CaseName + "在 " + MainTest.TestCase + ".xlsx 中没有对应的命名的 sheet");
            throw new NullPointerException();
        }
    }
}
