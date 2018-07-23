package com.xiaoM.KeyWord.Selenium;

import com.xiaoM.Element.LocationWebElement;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectModule {
    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;

    public SelectModule(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean selectByIndex(Location location){
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        try {
            WebElement element = locationWebElement.waitForElement(location);
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
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        try {
            WebElement element = locationWebElement.waitForElement(location);
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
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        try {
            WebElement element = locationWebElement.waitForElement(location);
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
