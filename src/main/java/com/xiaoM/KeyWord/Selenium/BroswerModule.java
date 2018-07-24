package com.xiaoM.KeyWord.Selenium;

import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class BroswerModule {
    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;

    public BroswerModule(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean OpenUrl(Location location) {
        log.info(TestCategory + "：打开网址 [ " + location.getValue() + " ]");
        driver.get(location.getValue());
        return true;
    }

    public boolean WindowMaxSize() {
        log.info(TestCategory + "：窗口最大化");
        driver.manage().window().maximize();
        return true;
    }

    public boolean WindowSetSize(Location location) {
        log.info(TestCategory + "： 窗口设置指定大小 [ " + location.getParameter() + " ]");
        int width = Integer.valueOf(location.getParameter().split(",")[0]);
        int height = Integer.valueOf(location.getParameter().split(",")[1]);
        driver.manage().window().setSize(new Dimension(width, height));
        return true;
    }

    public boolean SwitchToLastWindow() {
        Set<String> WindowsId = driver.getWindowHandles();
        log.info(TestCategory + "： [ 切换到最后一个窗口 ]");
        for (String windowId : WindowsId) {
            driver.switchTo().window(windowId);
        }
        return true;
    }

    public boolean SwitchToWindow(Location location) {
        log.info(TestCategory + "： [ 切换到第" + location.getValue() + "个窗口 ]");
        int num = Integer.valueOf(location.getValue());
        Set<String> WindowsId = driver.getWindowHandles();
        int start = 0;
        for (String windowId : WindowsId) {
            start++;
            if (start == num) {
                driver.switchTo().window(windowId);
            }
        }
        return true;
    }

    public boolean WindowRefresh(){
        log.info(TestCategory + "： [ 页面刷新 ]");
        driver.navigate().refresh();
        return true;
    }

    public boolean WindowBack(){
        log.info(TestCategory + "： [ 页面后退 ]");
        driver.navigate().back();
        return true;
    }

    public boolean WindowForward(){
        log.info(TestCategory + "： [ 页面前进 ]");
        driver.navigate().forward();
        return true;
    }
}
