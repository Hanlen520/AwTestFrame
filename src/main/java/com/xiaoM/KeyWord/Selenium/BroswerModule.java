package com.xiaoM.KeyWord.Selenium;

import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import org.openqa.selenium.WebDriver;

public class BroswerModule {
    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;

    public BroswerModule(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean OpenUrl(Location location){
        log.info(TestCategory + "：打开网址 [ " + location.getValue() + " ]");
        driver.get(location.getValue());
        return true;
    }
}
