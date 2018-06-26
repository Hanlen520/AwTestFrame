package com.xiaoM.KeyWord;

import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Element.LocationElement;
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
        LocationElement locationElement = new LocationElement(driver, TestCategory);
        try {
            WebElement element = locationElement.waitForElement(location);
            driver.switchTo().frame(element);
            log.info(TestCategory + "：切换iframe成功 [ " + location.getValue() + " ]");
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
