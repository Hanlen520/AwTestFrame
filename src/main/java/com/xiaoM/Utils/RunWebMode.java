package com.xiaoM.Utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.xiaoM.BeginScript.BeginWebScript;
import com.xiaoM.Driver.WebBaseDriver;
import com.xiaoM.Main.MainTest;
import org.openqa.selenium.WebDriver;

import java.util.*;

public class RunWebMode {
    private Log log = new Log(this.getClass());
    private StringBuilder sb;
    private String FailAction;

    public void runCase(String ID, String Module, String CaseName, String BrowserName,ExtentTest extentTest) throws Exception {
        extentTest.getModel().setStartTime(new Date());
        Location location;
        String TestCategory = ID + "_" + Module + "_" + CaseName;
        String[][] testStart = IOMananger.readExcelDataXlsx(BeginWebScript.workbook,CaseName);
        if (testStart != null) {
            WebBaseDriver base = new WebBaseDriver();
            WebDriver driver;
            try {
                driver = base.setUpWebDriver(BrowserName);
            } catch (Exception e) {
                log.error("driver 初始化失败");
                extentTest.fail(e);
                throw e;
            }
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
                        SeleniumAction seleniumAction = new SeleniumAction(driver, TestCategory, returnMap, extentTest, "");
                        Object result = seleniumAction.action(location);
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
            } catch (Exception e) {
                if (!FailAction.toLowerCase().equals("module")){
                    SeleniumScreenShot screenShot = new SeleniumScreenShot(driver);
                    screenShot.setScreenName(TestCategory);
                    screenShot.takeScreenshot();
                    sb.append("[异常截图如下]：");
                    extentTest.fail("<pre>" + sb.toString() + "</pre>", MediaEntityBuilder.createScreenCaptureFromPath(MainTest.screenMessageList.get(TestCategory)).build());
                    extentTest.error(e);
                }
                FailStep.dealWithFailStep(b, testStart, extentTest);
                throw e;
            } finally {
                extentTest.getModel().setEndTime(new Date());
                driver.quit();
            }
        } else {
            extentTest.fail("该测试用例:" + CaseName + "在 " + MainTest.TestCase + ".xlsx 中没有对应的命名的 sheet");
            throw new NullPointerException();
        }
    }
}
