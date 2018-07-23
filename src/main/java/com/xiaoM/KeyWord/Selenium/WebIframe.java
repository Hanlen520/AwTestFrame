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
        LocationWebElement locationAppElement = new LocationWebElement(driver, TestCategory);
        try {
            WebElement element = locationAppElement.waitForElement(location);
            driver.switchTo().frame(element);
            log.info(TestCategory + "：切换iframe [ " + location.getValue() + " ]");
        } catch (Exception e) {
            log.error(TestCategory + "：切换iframe失败 [ " + location.getValue() + " ]");
            throw e;
        }
        return true;
    }

    public boolean switchToParentFrame() {
        try {
            driver.switchTo().parentFrame();
            log.info(TestCategory + "：切换父级iframe成功");
        } catch (Exception e) {
            log.info(TestCategory + "：切换父级iframe失败");
            throw e;
        }
        return true;
    }

    public boolean switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
            log.info(TestCategory + "：切换默认Frame成功");
        } catch (Exception e) {
            log.info(TestCategory + "：切换默认Frame失败");
            throw e;
        }
        return true;
    }

}
