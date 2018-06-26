package com.xiaoM.KeyWord;

import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Element.LocationElement;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.Picture;
import io.appium.java_client.TouchAction;

public class ClickModule {
    private Log log = new Log(this.getClass());
    private AppiumXMDriver driver;
    private String TestCategory;

    public ClickModule(AppiumXMDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean ClickElement(Location location){
        LocationElement locationElement = new LocationElement(driver, TestCategory);
        try {
            locationElement.waitForElement(location).click();
        } catch (Exception e) {
            log.error(TestCategory + "：点击控件失败 [ " + location.getDescription() + " ]");
            throw e;
        }
        log.info(TestCategory + "：点击控件成功 [ " + location.getDescription() + " ]");
        return true;
    }

    public boolean ClickCoordinate(Location location){
        try {
            int x = Integer.valueOf(location.getValue().split(":")[0]);
            int y = Integer.valueOf(location.getValue().split(":")[1]);
            new TouchAction(driver).tap(x, y).perform();
        } catch (NumberFormatException e) {
            log.error(TestCategory + "：点击坐标失败 [ " + location.getValue() + " ]");
            throw e;
        }
        log.info(TestCategory + "：点击坐标成功 [ " + location.getValue() + " ]");
        return true;
    }

    public boolean ClickPicture(Location location){
        try {
            Picture.pictureClick(driver, location.getValue());
        } catch (Exception e) {
            log.error(TestCategory + "：点击图片失败 [ " + location.getValue() + " ]");
            throw e;
        }
        log.info(TestCategory + "：点击图片成功 [ " + location.getValue() + " ]");
        return true;
    }
}
