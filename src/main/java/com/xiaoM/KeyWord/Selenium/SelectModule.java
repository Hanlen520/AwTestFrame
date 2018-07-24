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
        log.info(TestCategory + "：下拉控件选择内容 [ 描述： " + location.getDescription() +" 选择内容： index=" + location.getParameter() + " ]");
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        try {
            WebElement element = locationWebElement.waitForElement(location);
            String index = location.getParameter();
            Select SelectMethod = new Select(element);
            int i = Integer.valueOf(index);
            SelectMethod.selectByIndex(i);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean selectByValue(Location location) {
        log.info(TestCategory + "：下拉控件选择内容 [ 描述： " + location.getDescription() +" 选择内容： value=" + location.getParameter() + " ]");
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        try {
            WebElement element = locationWebElement.waitForElement(location);
            String value = location.getParameter();
            Select SelectMethod = new Select(element);
            SelectMethod.selectByValue(value);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean selectByText(Location location) {
        log.info(TestCategory + "：下拉控件选择内容 [ 描述： " + location.getDescription() +" 选择内容： text=" + location.getParameter() + " ]");
        LocationWebElement locationWebElement = new LocationWebElement(driver, TestCategory);
        try {
            WebElement element = locationWebElement.waitForElement(location);
            String Text = location.getParameter();
            Select SelectMethod = new Select(element);
            SelectMethod.selectByVisibleText(Text);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }
}
