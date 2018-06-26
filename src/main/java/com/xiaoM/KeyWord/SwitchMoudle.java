package com.xiaoM.KeyWord;

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
        try {
            driver.getContextHandles().forEach((handle) -> {
                if (handle.toString().contains("WEBVIEW")) {
                    driver.context(handle.toString());
                    log.info(TestCategory + ":切换进入WEBVIEW成功");
                }else{
                    log.error(TestCategory + ":当前页面不存在WEBVIEW页");
                }
            });
            return true;
        } catch (Exception e) {
            log.error(TestCategory + ":切换进入WEBVIEW发生异常");
           return false;
        }
    }

    public boolean switchToNative(){
        try {
            driver.getContextHandles().forEach((handle) -> {
                if (handle.toString().contains("Native")) {
                    driver.context(handle.toString());
                    log.info(TestCategory + ":切换进入Native成功");
                }
            });
            return true;
        } catch (Exception e) {
            log.error(TestCategory + ":切换进入Native发生异常");
            return false;
        }
    }
}
