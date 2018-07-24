package com.xiaoM.KeyWord.Appium;

import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Element.LocationAppElement;
import com.xiaoM.Element.LocationWebElement;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.Picture;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ClickModule {
    private Log log = new Log(this.getClass());
    private AppiumXMDriver driver;
    private String TestCategory;

    public ClickModule(AppiumXMDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean ClickElement(Location location){
        log.info(TestCategory + "：点击控件 [ " + location.getDescription() + " ]");
        LocationAppElement locationAppElement = new LocationAppElement(driver, TestCategory);
        try {
            locationAppElement.waitForElement(location).click();
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean LongPressElement(Location location){
        log.info(TestCategory + "：长按控件 [ " + location.getDescription() + " ]");
        LocationAppElement locationAppElement = new LocationAppElement(driver, TestCategory);
        try {
            WebElement element = locationAppElement.waitForElement(location);
            new TouchAction(driver).longPress(element).perform();
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean LongPressCoordinate(Location location){
        log.info(TestCategory + "：长按坐标 [ " + location.getValue() + " ]");
        try {
            int x = Integer.valueOf(location.getValue().split(":")[0]);
            int y = Integer.valueOf(location.getValue().split(":")[1]);
            new TouchAction(driver).longPress(x,y).perform();
        } catch (NumberFormatException e) {
            throw e;
        }
        return true;
    }


    public boolean ClickCoordinate(Location location){
        log.info(TestCategory + "：点击坐标 [ " + location.getValue() + " ]");
        try {
            int x = Integer.valueOf(location.getValue().split(":")[0]);
            int y = Integer.valueOf(location.getValue().split(":")[1]);
            new TouchAction(driver).tap(x, y).perform();
        } catch (NumberFormatException e) {
            throw e;
        }
        return true;
    }

    public boolean ClickPicture(Location location){
        log.info(TestCategory + "：点击图片 [ " + location.getValue() + " ]");
        try {
            Picture.pictureClick(driver, location.getValue());
        } catch (Exception e) {
            throw e;
        }
        return true;
    }
}
