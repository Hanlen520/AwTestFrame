package com.xiaoM.KeyWord;

import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Element.LocationElement;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectModule {
    private Log log = new Log(this.getClass());
    private AppiumXMDriver driver;
    private String TestCategory;

    public SelectModule(AppiumXMDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean selectByIndex(Location location){
        LocationElement locationElement = new LocationElement(driver, TestCategory);
        try {
            WebElement element = locationElement.waitForElement(location);
            String index = location.getParameter();
            Select SelectMethod = new Select(element);
            int i = Integer.valueOf(index);
            SelectMethod.selectByIndex(i);
        } catch (Exception e) {
            log.error(TestCategory + "：控件选择失败 [ " + location.getDescription() + " ]");
            throw e;
        }
        log.info(TestCategory + "：控件选择成功 [ " + location.getDescription() + " ]");
        return true;
    }

    public boolean selectByValue(Location location) {
        LocationElement locationElement = new LocationElement(driver, TestCategory);
        try {
            WebElement element = locationElement.waitForElement(location);
            String value = location.getParameter();
            Select SelectMethod = new Select(element);
            SelectMethod.selectByValue(value);
        } catch (Exception e) {
            log.error(TestCategory + "：控件选择失败 [ " + location.getDescription() + " ]");
            throw e;
        }
        log.info(TestCategory + "：控件选择成功 [ " + location.getDescription() + " ]");
        return true;
    }

    public boolean selectByText(Location location) {
        LocationElement locationElement = new LocationElement(driver, TestCategory);
        try {
            WebElement element = locationElement.waitForElement(location);
            String Text = location.getParameter();
            Select SelectMethod = new Select(element);
            SelectMethod.selectByVisibleText(Text);
        } catch (Exception e) {
            log.error(TestCategory + "：控件选择失败 [ " + location.getDescription() + " ]");
            throw e;
        }
        log.info(TestCategory + "：控件选择成功 [ " + location.getDescription() + " ]");
        return true;
    }
}
