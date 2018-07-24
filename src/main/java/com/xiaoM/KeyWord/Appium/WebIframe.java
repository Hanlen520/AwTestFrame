package com.xiaoM.KeyWord.Appium;

import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Element.LocationAppElement;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import org.openqa.selenium.WebElement;

public class WebIframe {
    private Log log = new Log(this.getClass());
    private AppiumXMDriver driver;
    private String TestCategory;

    public WebIframe(AppiumXMDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean switchToIframe(Location location) {
        log.info(TestCategory + "：切换Frame标签 [ " + location.getDescription() + " ]");
        LocationAppElement locationAppElement = new LocationAppElement(driver, TestCategory);
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
