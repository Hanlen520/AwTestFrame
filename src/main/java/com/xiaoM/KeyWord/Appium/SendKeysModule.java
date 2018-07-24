package com.xiaoM.KeyWord.Appium;

import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Element.LocationAppElement;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;

public class SendKeysModule {
    private Log log = new Log(this.getClass());
    private AppiumXMDriver driver;
    private String TestCategory;

    public SendKeysModule(AppiumXMDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean SendKeys(Location location){
        log.info(TestCategory + "：控件输入内容 [ 描述： " + location.getDescription() +" 输入内容： " + location.getParameter() + " ]");
        LocationAppElement locationAppElement = new LocationAppElement(driver, TestCategory);
        try {
            locationAppElement.waitForElement(location).sendKeys(location.getParameter());
        } catch (Exception e) {
            throw e;
        }
        return true;
    }
}
