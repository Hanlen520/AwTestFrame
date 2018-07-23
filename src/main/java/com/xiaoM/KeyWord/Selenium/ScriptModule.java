package com.xiaoM.KeyWord.Selenium;

import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.SeleniumExecuteScript;
import org.openqa.selenium.WebDriver;

public class ScriptModule {
    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;

    public ScriptModule(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public Object script(Location location){
        log.info(TestCategory + ":调用外部脚本 [ " + location.getValue() + " ]");
        Object result = null;
        try {
            SeleniumExecuteScript executeScript = new SeleniumExecuteScript(driver);
            result = executeScript.runScript(location);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
