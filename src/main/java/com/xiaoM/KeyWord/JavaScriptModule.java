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
        Object result;
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            result = js.executeScript(location.getValue());
        } catch (Exception e) {
            log.error(TestCategory + ":执行js脚本失败 [ " + location.getValue() + " ]");
            throw e;
        }
        log.info(TestCategory + ":执行js脚本 [ " + location.getValue() + " ]");
        if (result == null) {
            return true;
        }
        return result;
    }
}
