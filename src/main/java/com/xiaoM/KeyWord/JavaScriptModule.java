package com.xiaoM.KeyWord;

import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class JavaScriptModule {
    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;

    public JavaScriptModule(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public Object javaScriptMethod(Location location) {
        log.info(TestCategory + ": 执行js脚本 [ " + location.getValue() + " ]");
        Object result = null;
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            result = js.executeScript(location.getValue());
        } catch (Exception e) {
           e.printStackTrace();
        }
        if (result == null) {
            return true;
        }
        return result;
    }
}
