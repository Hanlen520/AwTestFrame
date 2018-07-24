package com.xiaoM.KeyWord.Selenium;

import com.xiaoM.Element.LocationWebElement;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.Picture;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.FindFailed;

public class ClickModule {
    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;

    public ClickModule(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean ClickElement(Location location){
        log.info(TestCategory + "：点击控件 [ " + location.getDescription() + " ]");
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        try {
            locationWebElement.waitForElement(location).click();
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean DobuleClickElement(Location location){
        log.info(TestCategory + "：双击控件 [ " + location.getDescription() + " ]");
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        try {
            WebElement element = locationWebElement.waitForElement(location);
            new Actions(driver).doubleClick(element).perform();
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean RightClickElement(Location location){
        log.info(TestCategory + "：右击控件 [ " + location.getDescription() + " ]");
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        try {
            WebElement element = locationWebElement.waitForElement(location);
            new Actions(driver).contextClick(element).perform();
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean LongPressElement(Location location) throws Exception {
        log.info(TestCategory + "：长按控件 [ " + location.getDescription() + " ]");
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        try {
            WebElement element = locationWebElement.waitForElement(location);
            new Actions(driver).clickAndHold(element).perform();
            Thread.sleep(2);
            new Actions(driver).clickAndHold(element).release();
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
            new Actions(driver).moveByOffset(x,y).clickAndHold().perform();
        } catch (NumberFormatException e) {
            throw e;
        }
        return true;
    }


    public boolean HoverElement(Location location){
        log.info(TestCategory + "：鼠标悬停 [ " + location.getDescription() + " ]");
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        try {
            WebElement element = locationWebElement.waitForElement(location);
            new Actions(driver).moveToElement(element).perform();
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean ClickCoordinate(Location location){
        log.info(TestCategory + "：点击坐标 [ " + location.getValue() + " ]");
        try {
            int x = Integer.valueOf(location.getValue().split(":")[0]);
            int y = Integer.valueOf(location.getValue().split(":")[1]);
            new Actions(driver).moveByOffset(x,y).click().release();
        } catch (NumberFormatException e) {
            throw e;
        }
        return true;
    }

    public boolean ClickPicture(Location location) throws Exception {
        log.info(TestCategory + "：点击图片 [ " + location.getValue() + " ]");
        Picture.webPictureClick(location);
        return true;
    }
}
