package com.xiaoM.KeyWord.Selenium;

import com.xiaoM.Element.LocationWebElement;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import org.openqa.selenium.WebDriver;

public class SendKeysModule {
    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;

    public SendKeysModule(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean SendKeys(Location location){
        log.info(TestCategory + "：控件输入内容 [ 描述： " + location.getDescription() +" 输入内容： " + location.getParameter() + " ]");
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        try {
            locationWebElement.waitForElement(location).sendKeys(location.getParameter());
        } catch (Exception e) {
            throw e;
        }
        return true;
    }
}
