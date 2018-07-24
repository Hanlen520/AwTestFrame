package com.xiaoM.KeyWord.Selenium;

import com.xiaoM.Element.LocationWebElement;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebIframe {
    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;

    public WebIframe(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean switchToIframe(Location location) {
        log.info(TestCategory + "：切换Frame标签 [ " + location.getDescription() + " ]");
        LocationWebElement locationAppElement = new LocationWebElement(driver, TestCategory);
        try {
            WebElement element = locationAppElement.waitForElement(location);
            driver.switchTo().frame(element);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean switchToParentFrame() {
        log.info(TestCategory + "：切换父级Frame标签");
        try {
            driver.switchTo().parentFrame();
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean switchToDefaultContent() {
        log.info(TestCategory + "：切换默认Frame");
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

}
