package com.xiaoM.KeyWord.Appium;

import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Utils.Log;

public class SwitchMoudle {
    private Log log = new Log(this.getClass());
    private AppiumXMDriver driver;
    private String TestCategory;

    public SwitchMoudle(AppiumXMDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean switchToWebview(){
        log.info(TestCategory + " :切换进入WEBVIEW");
        try {
            driver.getContextHandles().forEach((handle) -> {
                if (handle.toString().contains("WEBVIEW")) {
                    driver.context(handle.toString());
                }
            });
            return true;
        } catch (Exception e) {
           throw e;
        }
    }

    public boolean switchToNative(){
        log.info(TestCategory + ":切换进入Native");
        try {
            driver.getContextHandles().forEach((handle) -> {
                if (handle.toString().contains("Native")) {
                    driver.context(handle.toString());
                }
            });
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
}
