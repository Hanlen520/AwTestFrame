package com.xiaoM.KeyWord.Selenium;

import com.xiaoM.Element.LocationWebElement;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.Picture;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class ClickModule {
    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;

    public ClickModule(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean ClickElement(Location location){
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        log.info(TestCategory + "：点击控件 [ " + location.getDescription() + " ]");
        try {
            locationWebElement.waitForElement(location).click();
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean ClickCoordinate(Location location){
        try {
            int x = Integer.valueOf(location.getValue().split(":")[0]);
            int y = Integer.valueOf(location.getValue().split(":")[1]);
            new Actions(driver).moveByOffset(x,y).click().release();
        } catch (NumberFormatException e) {
            log.error(TestCategory + "：点击坐标失败 [ " + location.getValue() + " ]");
            throw e;
        }
        log.info(TestCategory + "：点击坐标成功 [ " + location.getValue() + " ]");
        return true;
    }

    public boolean ClickPicture(Location location){
        Picture.webPictureClick(location);
        return true;
    }
}
