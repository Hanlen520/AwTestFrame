package com.xiaoM.KeyWord.Selenium;

import com.xiaoM.Element.LocationWebElement;
import com.xiaoM.Utils.BaiduOCR;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.Picture;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GetModule {

    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;

    public GetModule(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    /**
     * 获取控件
     * @param location
     * @return
     */
    public WebElement getElement(Location location) {
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        WebElement element;
        try {
            element = locationWebElement.waitForElement(location);
        } catch (Exception e) {
            log.error(TestCategory + "：获取控件失败 [ " + location.getDescription() + " ]");
            throw e;
        }
        log.info(TestCategory + "：获取控件成功 [ " + element.toString() + " ]");
        return element;
    }

    /**
     * 获取控件文本
     * @param location
     * @return
     */
    public String getElementText(Location location) {
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        String result;
        try {
            result = locationWebElement.waitForElement(location).getText();
        } catch (Exception e) {
            log.error(TestCategory + "：获取控件文本失败 [ " + location.getDescription() + " ]");
            throw e;
        }
        log.info(TestCategory + "：获取控件文本成功 [ " + result + " ]");
        return result;
    }

    /**
     * 获取控件尺寸
     * @param location
     * @return
     */
    public String getElementSize(Location location) {
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        String result;
        try {
            result = locationWebElement.waitForElement(location).getSize().toString();
        } catch (Exception e) {
            log.error(TestCategory + "：获取控件尺寸失败 [ " + location.getDescription() + " ]");
            throw e;
        }
        log.info(TestCategory + "：获取控件尺寸成功 [ " + result + " ]");
        return result;
    }

    /**
     * 获取控件图片
     * @param location
     * @return
     */
    public String getElementiPcture(Location location) throws Exception {
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        String result = null;
        try {
            WebElement element = locationWebElement.waitForElement(location);
            result = Picture.captureElement(driver, element);
        } catch (Exception e) {
            log.error(TestCategory + "：获取控件图片失败 [ " + location.getDescription() + " ]");
            e.printStackTrace();
        }
        log.info(TestCategory + "：获取控件图片成功 [ " + result + " ]");
        return result;
    }

    /**
     * 获取控件图片文本
     * @param location
     * @return
     */
    public String getElementiPctureText(Location location) throws Exception {
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        String result;
        try {
            WebElement element = locationWebElement.waitForElement(location);
            result = BaiduOCR.getPictureText(driver, element);
        } catch (Exception e) {
            log.error(TestCategory + "：获取控件图片文本失败 [ " + location.getDescription() + " ]");
            throw e;
        }
        log.info(TestCategory + "：获取控件图片文本成功 [ " + result + " ]");
        return result;
    }

    /**
     * 获取控件属性
     * @param location
     * @return
     */
    public String getElementiAttribute(Location location){
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        String result;
        try {
            result = locationWebElement.waitForElement(location).getAttribute(location.getParameter());
        } catch (Exception e) {
            log.error(TestCategory + "：获取控件属性失败 [ " + location.getDescription() + " ]");
            throw e;
        }
        log.info(TestCategory + "：获取控件属性成功 [ " + result + " ]");
        return result;
    }

}
