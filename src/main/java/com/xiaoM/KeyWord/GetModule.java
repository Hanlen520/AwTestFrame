package com.xiaoM.KeyWord;

import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Element.LocationElement;
import com.xiaoM.Utils.BaiduOCR;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.Picture;
import org.openqa.selenium.WebElement;

public class GetModule {

    private Log log = new Log(this.getClass());
    private AppiumXMDriver driver;
    private String TestCategory;

    public GetModule(AppiumXMDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    /**
     * 获取控件
     * @param location
     * @return
     */
    public WebElement getElement(Location location) {
        LocationElement locationElement = new LocationElement(driver, TestCategory);
        WebElement element;
        try {
            element = locationElement.waitForElement(location);
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
        LocationElement locationElement = new LocationElement(driver, TestCategory);
        String result;
        try {
            result = locationElement.waitForElement(location).getText();
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
        LocationElement locationElement = new LocationElement(driver, TestCategory);
        String result;
        try {
            result = locationElement.waitForElement(location).getSize().toString();
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
        LocationElement locationElement = new LocationElement(driver, TestCategory);
        String result = null;
        try {
            WebElement element = locationElement.waitForElement(location);
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
        LocationElement locationElement = new LocationElement(driver, TestCategory);
        String result;
        try {
            WebElement element = locationElement.waitForElement(location);
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
        LocationElement locationElement = new LocationElement(driver, TestCategory);
        String result;
        try {
            result = locationElement.waitForElement(location).getAttribute(location.getParameter());
        } catch (Exception e) {
            log.error(TestCategory + "：获取控件属性失败 [ " + location.getDescription() + " ]");
            throw e;
        }
        log.info(TestCategory + "：获取控件属性成功 [ " + result + " ]");
        return result;
    }

}
