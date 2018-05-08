package com.xiaoM.Utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.xiaoM.ReportUtils.TestListener;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class ElementAction {
    private Log log = new Log(this.getClass());
    private AppiumDriver driver;
    private String TestCategory;
    private Map<String, Object> returnMap;
    private ExtentTest extentTest;

    ElementAction(AppiumDriver driver, String TestCategory, Map<String, Object> returnMap, ExtentTest extentTest) {
        this.driver = driver;
        this.TestCategory = TestCategory;
        this.returnMap = returnMap;
        this.extentTest = extentTest;
    }

    private WebElement getLocationElement(Location location) {
        String method = location.getKey().toLowerCase();
        String value = location.getValue();
        WebElement element = null;
        switch (method) {
            case "byid":
                element = driver.findElement(By.id(value));
                break;
            case "byname":
                element = driver.findElement(By.name(value));
                break;
            case "byclassname":
                element = driver.findElement(By.className(value));
                break;
            case "byTagName":
                element = driver.findElement(By.tagName(value));
                break;
            case "byxpath":
                element = driver.findElement(By.xpath(value));
                break;
            case "bycssselector":
                element = driver.findElement(By.cssSelector(value));
                break;
            case "bylinktext":
                element = driver.findElement(By.linkText(value));
                break;
            case "bypartiallinktext":
                element = driver.findElement(By.partialLinkText(value));
                break;
            default:
                log.error(TestCategory + "[异常]: 该元素定位方式不存在：" + method);
        }
        return element;
    }

    private WebElement getLocationElement(String location_Str) {
        String method = location_Str.split("::")[0].toLowerCase();
        String value = location_Str.split("::")[1];
        WebElement element = null;
        switch (method) {
            case "byid":
                element = driver.findElement(By.id(value));
                break;
            case "byname":
                element = driver.findElement(By.name(value));
                break;
            case "byclassname":
                element = driver.findElement(By.className(value));
                break;
            case "bytagname":
                element = driver.findElement(By.tagName(value));
                break;
            case "byxpath":
                element = driver.findElement(By.xpath(value));
                break;
            case "bycssselector":
                element = driver.findElement(By.cssSelector(value));
                break;
            case "bylinktext":
                element = driver.findElement(By.linkText(value));
                break;
            case "bypartiallinktext":
                element = driver.findElement(By.partialLinkText(value));
                break;
        }
        return element;
    }

    /**
     * 等待元素出现
     */
    private WebElement waitForElement(Location location) throws Exception {
        WebElement webElement;
        int timeout;
        Thread.sleep(500);
        if (location.getTimeOut().isEmpty() || location.getTimeOut() == null) {
            timeout = 1;//默认超时时间为1秒
        } else {
            timeout = Integer.valueOf(location.getTimeOut());
        }
        webElement = (new WebDriverWait(driver, timeout)).until(
                (ExpectedCondition<WebElement>) dr -> getLocationElement(location));
        return webElement;
    }

    private Object waitGetElement(Location location) {
        WebElement webElement;
        int timeout;
        if (location.getTimeOut().isEmpty() || location.getTimeOut() == null) {
            timeout = 1;//默认超时时间为1秒
        } else {
            timeout = Integer.valueOf(location.getTimeOut());
        }
        try {
            webElement = (new WebDriverWait(driver, timeout)).until(
                    (ExpectedCondition<WebElement>) dr -> getLocationElement(location));
        } catch (Exception e) {
            return "null";
        }
        return webElement;
    }

    private WebElement waitForElement(String location_Str, Location location) throws Exception {
        int timeout;
        if (location.getTimeOut().isEmpty() || location.getTimeOut() == null) {
            timeout = 1;//默认超时时间为1秒
        } else {
            timeout = Integer.valueOf(location.getTimeOut());
        }
        WebElement webElement = (new WebDriverWait(driver, timeout)).until(
                (ExpectedCondition<WebElement>) dr -> getLocationElement(location_Str));
        return webElement;
    }

    /**
     * *********************************************************************************************************
     * 输值模式
     * *********************************************************************************************************
     */

    private boolean sendKeysMethod(Location location) throws Exception {
        WebElement element = waitForElement(location);
        element.clear();
        element.sendKeys(location.getParameter());
        return true;
    }

    /**
     * *********************************************************************************************************
     * 取值模式
     * *********************************************************************************************************
     */

    private Object getMethod(Location location) throws Exception {
        String value = null;
        WebElement element;
        String method = location.getParameter().toLowerCase();
        switch (method) {
            case "getelement":
                return waitGetElement(location);
            case "gettext":
                value = waitForElement(location).getText();
                break;
            case "size":
                value = waitForElement(location).getSize().toString();
                break;
            case "getpicture":
                if (location.getValue().isEmpty()) {
                    element = null;
                } else {
                    element = waitForElement(location);
                }
                value = Picture.captureElement(driver, element);
                break;
            case "getpicturetext":
                element = waitForElement(location);
                value = BaiduOCR.getPictureText(driver, element);
                break;
            default:
                throw new Exception(" 该控件获取值方式不存在：" + method);
        }
        return value;
    }

    /**
     * *********************************************************************************************************
     * 三种选择控件操作模式
     * *********************************************************************************************************
     */
    private boolean selectByIndexMethod(Location location) throws Exception {
        WebElement element = waitForElement(location);
        String index = location.getParameter();
        Select SelectMethod = new Select(element);
        int i = Integer.valueOf(index);
        SelectMethod.selectByIndex(i);
        return true;
    }

    private boolean selectByValueMethod(Location location) throws Exception {
        WebElement element = waitForElement(location);
        String value = location.getParameter();
        Select SelectMethod = new Select(element);
        SelectMethod.selectByValue(value);
        return true;
    }

    private boolean selectByTextMethod(Location location) throws Exception {
        WebElement element = waitForElement(location);
        String Text = location.getParameter();
        Select SelectMethod = new Select(element);
        SelectMethod.selectByVisibleText(Text);
        return true;
    }

    /**
     * *********************************************************************************************************
     * 点击操作(控件、坐标、图片)
     * *********************************************************************************************************
     */

    private boolean clickMethod(Location location) throws Exception {
        String method = location.getKey().toLowerCase();
        switch (method) {
            case "coordinate":
                int x = Integer.valueOf(location.getValue().split(":")[0]);
                int y = Integer.valueOf(location.getValue().split(":")[1]);
                new TouchAction(driver).tap(x, y).perform();
                break;
            case "picture":
                Picture.pictureClick(driver, location.getParameter());
                break;
            default:
                waitForElement(location).click();
        }
        return true;
    }

    /**
     * *********************************************************************************************************
     * 等待模式
     * *********************************************************************************************************
     */

    private boolean waitMethod(Location location) throws Exception {
        String method = location.getKey().toLowerCase();
        switch (method) {
            case "time":
                int millis = Integer.valueOf(location.getValue()) * 1000;
                Thread.sleep(millis);
                break;
            case "picture":
                long time = System.currentTimeMillis();
                while (true) {
                    if (System.currentTimeMillis() > time + 6000) {
                        throw new Exception("60S内目标图片没有匹配成功");
                    }
                    if (Picture.matchTemplate(driver, location.getParameter())) {
                        return true;
                    }
                }
            default:
                throw new Exception("[异常]: 不支持该[ " + location.getKey() + " ]操作");
        }
        return true;
    }

    /**
     * *********************************************************************************************************
     * 设置参数模式
     * *********************************************************************************************************
     */

    private boolean setMethod(Location location) {
        String key = location.getValue();
        String value = location.getParameter();
        returnMap.put(key, value);
        return true;
    }

    private boolean iframeMethod(Location location) throws Exception {
        String method = location.getParameter().toLowerCase();
        switch (method) {
            case "switchtoiframe":
                if (!location.getKey().isEmpty()) {
                    WebElement element = waitForElement(location);
                    driver.switchTo().frame(element);
                } else {
                    if (Match.isNumeric(location.getValue())) {
                        int i = Integer.valueOf(location.getValue());
                        driver.switchTo().frame(i);
                    } else {
                        driver.switchTo().frame(location.getValue());
                    }
                }
                break;
            case "switchtoparentframe":
                driver.switchTo().parentFrame();
                break;
            case "switchtodefaultcontent":
                driver.switchTo().defaultContent();
                break;
            default:
                throw new Exception("[异常]: 不支持该操作类型[ " + location.getParameter() + " ]");
        }
        return true;
    }

    /**
     * *********************************************************************************************************
     * 执行js脚本
     * *********************************************************************************************************
     */

    private Object javaScriptMethod(Location location) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(location.getValue());
    }

    private Object compareMethod(Location location) throws Exception {
        String method = location.getKey().toLowerCase();
        Object value_0 = location.getParameter();
        Object value_1 = location.getExpected();
        if (value_0.toString().contains("${")) {
            value_0 = Match.replaceKeys(returnMap, value_0.toString());
        } else if (value_1.toString().contains("${")) {
            value_1 = Match.replaceKeys(returnMap, value_1.toString());
        }
        switch (method) {
            case "text":
                switch (location.getValue().toLowerCase()) {
                    case "string":
                        if (value_1.equals(value_0)) {
                            return true;
                        } else {
                            throw new Exception("[校验失败]: 预期值 [ " + value_1 + "  ] 实际值 [ " + value_0 + " ]");
                        }
                    case "int":
                        int A = Integer.valueOf(value_0.toString());
                        int B = Integer.valueOf(value_1.toString());
                        if (A == B) {
                            return true;
                        } else {
                            throw new Exception("[校验失败]: 预期值 [ " + value_1 + "  ] 实际值 [ " + value_0 + " ]");
                        }
                    case "double":
                        Double C = Double.valueOf(value_0.toString());
                        Double D = Double.valueOf(value_1.toString());
                        if (C.equals(D)) {
                            return true;
                        } else {
                            throw new Exception("[校验失败]: 预期值 [ " + value_1 + "  ] 实际值 [ " + value_0 + " ]");
                        }
                    default:
                        throw new Exception("[异常]: 不支持该操作类型 [ " + location.getValue() + " ]");
                }

            case "picture":
                double compare_result;
                switch (location.getValue().toLowerCase()) {
                    case "opencv":
                        opencv_core.IplImage src = opencv_imgcodecs.cvLoadImage(value_0.toString());
                        opencv_core.IplImage tmp = opencv_imgcodecs.cvLoadImage(TestListener.ProjectPath + "/picture/" + value_1);
                        opencv_core.IplImage result = opencv_core.cvCreateImage(opencv_core.cvSize(src.width() - tmp.width() + 1, src.height() - tmp.height() + 1), opencv_core.IPL_DEPTH_32F, 1);
                        opencv_core.cvZero(result);
                        opencv_imgproc.cvMatchTemplate(src, tmp, result, opencv_imgproc.CV_TM_CCORR_NORMED);
                        double[] minVal = new double[2];
                        double[] maxVal = new double[2];
                        opencv_core.cvMinMaxLoc(result, minVal, maxVal);
                        compare_result = maxVal[0];
                        if (compare_result > 0.95f) {
                            return "相似度 [ " + compare_result + " ]";
                        }
                        opencv_core.cvReleaseImage(src);
                        opencv_core.cvReleaseImage(tmp);
                        opencv_core.cvReleaseImage(result);
                        throw new Exception("图像相识度为：" + compare_result + " OpenCV相似度小于0.95，判断为不通过");
                    case "hash":
                        FingerPrint fp1 = new FingerPrint(ImageIO.read(new File(value_0.toString())));
                        FingerPrint fp2 = new FingerPrint(ImageIO.read(new File(TestListener.ProjectPath + "/picture/" + value_1)));
                        compare_result = fp1.compare(fp2);
                        if (compare_result >= 0.8f) {
                            return "相似度 [ " + compare_result + " ]";
                        }
                        throw new Exception("图像相识度为：" + compare_result + " Hash算法相似度小于0.8，判断为不通过");
                    default:
                        throw new Exception("[异常]: 不支持该操作类型 [ " + location.getValue() + " ]");
                }
            case "element":
                switch (value_1.toString().toLowerCase()) {
                    case "notnull":
                        if (value_0.equals("null")) {
                            return false;
                        }
                        return "true";
                    case "isnull":
                        if (value_0.equals("null")) {
                            return true;
                        }
                        return "false";
                    default:
                        throw new Exception("[异常]: 不支持该操作类型 [ " + value_1.toString() + " ]");
                }

            default:
                throw new Exception("[异常]: 不支持该操作类型 [ " + location.getValue() + " ]");
        }
    }

    private Object databaseMethod(Location location) throws Exception {
        Map<String, String[]> dataBaseConfig = TestListener.DataBaseConfig;
        String[] dataBase = dataBaseConfig.get(location.getParameter());
        String dataBaseType = dataBase[2].toLowerCase();
        String dataBaseDriver;
        String url = dataBase[3];
        String userName = dataBase[4];
        String passWord = dataBase[5];
        switch (dataBaseType) {
            case "mysql":
                url = url + "?useSSL=false&serverTimezone=UTC";
                dataBaseDriver = "com.mysql.cj.jdbc.Driver";
                break;
            default:
                throw new Exception("[异常]: 不支持该数据库操作[ " + dataBaseType + " ]");
        }
        SqlHelper sqlHelper = new SqlHelper(dataBaseDriver, url, userName, passWord);
        String method = location.getKey().toLowerCase();
        String sql = location.getValue();
        switch (method) {
            case "update":
                sqlHelper.executeUpdate(sql);
                return true;
            case "query":
                ResultSet result = sqlHelper.executeQuery(sql);
                if (result.first()) {
                    return result.getString(1);
                }
                break;
            default:
                throw new Exception("[异常]: 不支持该操作类型[ " + method + " ]");
        }
        return null;
    }

    private Object scriptMethod(Location location) throws Exception {
        ExecuteScript executeScript = new ExecuteScript(driver);
        return executeScript.runScript(location);
    }

    private Object switchMethod(Location location) throws Exception {
        String value = location.getValue().toLowerCase();
        switch (value) {
            case "webview":
                driver.getContextHandles().forEach((handle) -> {
                    if (handle.toString().contains("WEBVIEW")) {
                        driver.context(handle.toString());
                    }
                });
            case "native":
                driver.getContextHandles().forEach((handle) -> {
                    if (handle.toString().contains("Native")) {
                        driver.context(handle.toString());
                    }
                });
            default:
                throw new Exception("无法识别该操作类型[ " + value + " ]");
        }
    }

    private Object moduleMethod(Location location2) throws Exception {
        String moudlePath = TestListener.ProjectPath + "/testCase/" + TestListener.TestCase + "/module.xlsx";
        String sheetName = location2.getValue();
        InputStream is = new FileInputStream(moudlePath);
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        workbook.close();
        StringBuilder sb = null;
        String[][] moduleStep = IOMananger.readExcelDataXlsx(workbook, sheetName);
        int b = 0;
        try {
            for (int a = 1; a < moduleStep.length; a++) {
                b=a;
                List<String> parameteres = new ArrayList<>(Arrays.asList(moduleStep[a]));
                Location location = new Location();
                location.setLocation(parameteres);
                sb = new StringBuilder();
                if (location.getIsRun().equals("YES")) {
                    String Step = sheetName + "." + location.getStep();
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
                    sb.append("[模块]: " + location2.getDescription() + "\r\n");
                    sb.append("[步骤]: " + Step + "\r\n");
                    sb.append("[步骤描述]: " + Description + "\r\n");
                    sb.append("[操作方式]:" + Action + "\r\n");
                    sb.append("[关键字]:" + Key + "\r\n");
                    sb.append("[属性值]:" + Value + "\r\n");
                    sb.append("[参数]：" + Parameter + "\r\n");
                    ElementAction elementAction = new ElementAction(driver, TestCategory, returnMap, extentTest);
                    Object result = elementAction.action(location);
                    if (result != null) {
                        if (result.toString().toLowerCase().equals("false")) {
                            throw new Exception("返回值为 False");
                        }
                    }
                    sb.append("[返回值]：" + result);
                    returnMap.put(Step, result);
                    extentTest.log(Status.INFO, "<pre>" + sb.toString() + "</pre>");
                }
            }
        } catch (Exception e) {
            ScreenShot screenShot = new ScreenShot(driver);
            screenShot.setScreenName(TestCategory);
            screenShot.takeScreenshot();
            sb.append("[异常截图如下]：");
            extentTest.fail("<pre>" + sb.toString() + "</pre>", MediaEntityBuilder.createScreenCaptureFromPath(TestListener.screenMessageList.get(TestCategory)).build());
            extentTest.error(e);
            FailStep.dealWithMoubleFailStep(b, moduleStep, extentTest,location2);
            throw e;
        }
        return true;
    }

    Object action(Location location) throws Exception {
        String method = location.getAction().toLowerCase();
        switch (method) {
            case "click":
                return clickMethod(location);
            case "sendkeys":
                return sendKeysMethod(location);
            case "wait":
                return waitMethod(location);
            case "set":
                return setMethod(location);
            case "get":
                return getMethod(location);
            case "selectbyindex":
                return selectByIndexMethod(location);
            case "selectbyvalue":
                return selectByValueMethod(location);
            case "selectbytext":
                return selectByTextMethod(location);
            case "javascript":
                return javaScriptMethod(location);
            case "compare":
                return compareMethod(location);
            case "database":
                return databaseMethod(location);
            case "script":
                return scriptMethod(location);
            case "switch":
                return switchMethod(location);
            case "iframe":
                return iframeMethod(location);
            case "module":
                return moduleMethod(location);
        }
        return true;
    }
}
