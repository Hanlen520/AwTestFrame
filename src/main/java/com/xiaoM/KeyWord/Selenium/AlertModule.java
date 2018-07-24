package com.xiaoM.KeyWord.Selenium;

import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import org.openqa.selenium.WebDriver;

public class AlertModule {
    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;

    public AlertModule(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean AcceptAlert() {
        log.info(TestCategory + "：Alert弹窗 [ 确定 ]");
        driver.switchTo().alert().accept();
        return true;
    }

    public boolean DismissAlert() {
        log.info(TestCategory + "：Alert弹窗 [  取消 ]");
        driver.switchTo().alert().dismiss();
        return true;
    }

    public String AlertGetText() {
        log.info(TestCategory + "：Alert弹窗 [ 取值 ]");
        String text = driver.switchTo().alert().getText();
        return text;
    }

    public boolean AlertInputText(Location location) {
        log.info(TestCategory + "：Alert弹窗 [ 输入内容：" + location.getParameter() + " ]");
        driver.switchTo().alert().sendKeys(location.getParameter());
        return true;
    }

}
