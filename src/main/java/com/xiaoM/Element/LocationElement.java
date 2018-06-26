package com.xiaoM.Element;

import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import io.appium.java_client.MobileSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

public class LocationElement {

    private Log log = new Log(this.getClass());
    private AppiumXMDriver driver;
    private String TestCategory;


    public LocationElement(AppiumXMDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    private WebElement getElement(String fashion, String value) {
        WebElement element;
        switch (fashion.toLowerCase()) {
            case "id":
                element = driver.findElement(By.id(value));
                break;
            case "accessibilityid":
                element = driver.findElementByAccessibilityId(value);
                break;
            case "classname":
                element = driver.findElement(By.className(value));
                break;
            case "xpath":
                element = driver.findElement(By.xpath(value));
                break;
            case "androiduiautomator":
                element = driver.findElement(MobileSelector.ANDROID_UI_AUTOMATOR.toString(), value);
                break;
            case "iosclasschain":
                element = driver.findElement(MobileSelector.IOS_CLASS_CHAIN.toString(), value);
                break;
            case "iospredicatestring":
                element = driver.findElement(MobileSelector.IOS_PREDICATE_STRING.toString(), value);
                break;
            case "iosuiautomator":
                element = driver.findElement(MobileSelector.IOS_UI_AUTOMATION.toString(), value);
                break;
            default:
                throw new NoSuchElementException(TestCategory + "：该元素定位方式不存在： " + fashion);
        }
        return element;
    }

    public WebElement waitForElement(Location location)  {
        String fashion = location.getValue().substring(0, location.getValue().indexOf("="));
        String value = location.getValue().substring(location.getValue().indexOf("=")+1, location.getValue().length());
        WebElement webElement;
        int timeout;
        if (location.getTimeOut().isEmpty() || location.getTimeOut() == null) {
            timeout = 1;//默认超时时间为1秒
            log.info(TestCategory + "：查找控件 [ 方式：" + fashion + " 属性值：" + value + "]");
        } else {
            timeout = Integer.valueOf(location.getTimeOut());
            log.info(TestCategory + "：查找控件 [ 方式：" + fashion + " 属性值：" + value + " 等待超时：" + timeout + "秒 ]");
        }
        try {
            webElement = (new WebDriverWait(driver, timeout)).until(
                    (ExpectedCondition<WebElement>) dr -> getElement(fashion, value));
        } catch (Exception e) {
            log.error(TestCategory + "：查找控件失败 [ 方式：" + fashion + " 属性值：" + value + "]");
            throw e;
        }
        return webElement;
    }
}
