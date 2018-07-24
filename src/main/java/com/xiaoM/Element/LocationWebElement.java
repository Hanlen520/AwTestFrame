package com.xiaoM.Element;

import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

public class LocationWebElement {

    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;


    public LocationWebElement(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    private WebElement getElement(String fashion, String value) {
        WebElement element;
        switch (fashion.toLowerCase()) {
            case "id":
                element = driver.findElement(By.id(value));
                break;
            case "classname":
                element = driver.findElement(By.className(value));
                break;
            case "name":
                element = driver.findElement(By.name(value));
                break;
            case "xpath":
                element = driver.findElement(By.xpath(value));
                break;
            case "cssselector":
                element = driver.findElement(By.cssSelector(value));
                break;
            case"linktext":
                element = driver.findElement(By.linkText(value));
                break;
            case"partiallinktext":
                element = driver.findElement(By.partialLinkText(value));
                break;
            case"tagname":
                element = driver.findElement(By.tagName(value));
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
            throw e;
        }
        return webElement;
    }
}
