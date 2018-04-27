package com.xiaoM.Utils;

import com.xiaoM.ReportUtils.TestListener;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.Map;
import java.util.Set;


class ElementAction {
    private Log log = new Log(this.getClass());
    private AppiumDriver driver;
    private String TestCategory;
    private Map<String, Object> returnMap;

    ElementAction(AppiumDriver driver, String TestCategory, Map<String, Object> returnMap) {
        this.driver = driver;
        this.TestCategory = TestCategory;
        this.returnMap = returnMap;
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
                    new ExpectedCondition<WebElement>() {
                        @Override
                        public WebElement apply(WebDriver dr) {
                            return getLocationElement(location);
                        }
                    });
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
                new ExpectedCondition<WebElement>() {
                    @Override
                    public WebElement apply(WebDriver dr) {
                        return getLocationElement(location_Str);
                    }
                });
        return webElement;
    }

    private boolean sendKeysMethod(Location location) throws Exception {
        WebElement element = waitForElement(location);
        element.clear();
        element.sendKeys(location.getParameter());
        return true;
    }

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
                value = PictureCompare.captureElement(driver, element);
                break;
            case "getpicturetext":
                element = waitForElement(location);
                /*value = BaiduOCR.getPictureText(driver, element);*/
                break;
            default:
                throw new Exception(" 该控件获取值方式不存在：" + method);
        }
        return value;
    }

    private boolean selectMethod(Location location) throws Exception {
        WebElement element = waitForElement(location);
        String method = location.getParameter().toLowerCase();
        Select SelectMethod = new Select(element);
        if (method.contains("=")) {
            String method_0 = method.split("=")[0];
            String method_1 = method.split("=")[1];
            switch (method_0) {
                case "index":
                    int i = Integer.valueOf(method_1);
                    SelectMethod.selectByIndex(i);
                    break;
                case "value":
                    SelectMethod.selectByValue(method_1);
                    break;
            }
        } else {
            SelectMethod.selectByVisibleText(method);
        }
        return true;
    }

    private String alertMethod(Location location) {
        Alert alert = driver.switchTo().alert();
        String action = location.getValue().toLowerCase();
        String alertText = alert.getText();
        switch (action) {
            case "accept":
                alert.accept();
                break;
            case "dismiss":
                alert.dismiss();
                break;
        }
        return alertText;
    }

    private boolean clickMethod(Location location) throws Exception {
        waitForElement(location).click();
        return true;
    }

    private boolean waitMethod(Location location) throws Exception {
        String method = location.getKey().toLowerCase();
        switch (method) {
            case "time":
                int millis = Integer.valueOf(location.getValue()) * 1000;
                Thread.sleep(millis);
                break;
            default:
                throw new Exception("[异常]: 不支持该[ " + location.getKey() + " ]操作");
        }
        return true;
    }


    private boolean setMethod(Location location) throws Exception {
        String key = location.getValue();
        String value = location.getParameter();
        returnMap.put(key, value);
        return true;
    }

    private boolean mouseMethod(Location location) throws Exception {
        WebElement element;
        String method = location.getParameter().toLowerCase();
        switch (method) {
            case "hover":
                element = waitForElement(location);
                new Actions(driver).moveToElement(element).perform();
                break;
            case "draganddrop":
                if (location.getValue().contains(";")) {
                    String location_Str_0 = location.getValue().split(";")[0];
                    String location_Str_1 = location.getValue().split(";")[1];
                    WebElement element_Begin = waitForElement(location_Str_0, location);
                    WebElement element_End = waitForElement(location_Str_1, location);
                    new Actions(driver).dragAndDrop(element_Begin, element_End).perform();
                } else {
                    throw new Exception("[异常]: 拖拽实现的必要条件没达到[ " + location.getValue() + " ]");
                }
                break;
            case "click":
                element = waitForElement(location);
                new Actions(driver).click(element).perform();
                break;
            case "doubleclick":
                element = waitForElement(location);
                new Actions(driver).doubleClick(element).perform();
                break;
            case "rightclick":
                element = waitForElement(location);
                new Actions(driver).contextClick(element).perform();
                break;
            default:
                throw new Exception("[异常]: 无法识别该鼠标操作类型[ " + location.getParameter() + " ]");
        }
        return true;
    }

    private boolean browserMethod(Location location) throws Exception {
        String method = location.getValue().toLowerCase();
        Thread.sleep(3);
        switch (method) {
            case "refresh":
                driver.navigate().refresh();
                break;
            case "back":
                driver.navigate().back();
                break;
            case "forward":
                driver.navigate().forward();
                break;
            case "switchtowindow":
                Set<String> allWindowsId = driver.getWindowHandles();
                for (String windowId : allWindowsId) {
                    if (driver.switchTo().window(windowId).getTitle().contains(location.getParameter())) {
                        /*driver = driver.switchTo().window(windowId);*/
                        break;
                    }
                }
                break;
            case "maximize":
                driver.manage().window().maximize();
                break;
            case "setsize":
                int width = Integer.valueOf(location.getParameter().split(",")[0]);
                int height = Integer.valueOf(location.getParameter().split(",")[1]);
                driver.manage().window().setSize(new Dimension(width, height));
                break;
            default:
                throw new Exception("[异常]: 不支持该操作类型[ " + location.getValue() + " ]");
        }
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

    private Object javaScriptMethod(Location location) throws Exception {
        String method = location.getParameter().toLowerCase();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object value;
        switch (method) {
            case "click":
                WebElement element = waitForElement(location);
                value = js.executeScript("arguments[0].click();", element);
                break;
            default:
                value = js.executeScript(location.getValue());
        }
        return value;
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
                        opencv_core.IplImage tmp = opencv_imgcodecs.cvLoadImage(TestListener.ProjectPath + "/locationPicture/" + value_1);
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
                        FingerPrint fp2 = new FingerPrint(ImageIO.read(new File(TestListener.ProjectPath + "/locationPicture/" + value_1)));
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
       /* Map<String, String[]> dataBaseConfig = TestListener.DataBaseConfig;
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
            case "oracle":
                dataBaseDriver = "oracle.jdbc.driver.OracleDriver";
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
        }*/
        return null;
    }

    private Object scriptMethod(Location location) throws Exception {
        ExecuteScript executeScript = new ExecuteScript(driver);
        return executeScript.runScript(location);
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
            case "select":
                return selectMethod(location);

            case "mouse":
                return mouseMethod(location);
            case "browser":
                return browserMethod(location);
            case "iframe":
                return iframeMethod(location);
            case "javascript":
                return javaScriptMethod(location);
            case "compare":
                return compareMethod(location);
            case "database":
                return databaseMethod(location);
            case "script":
                return scriptMethod(location);
        }
        return true;
    }
}
