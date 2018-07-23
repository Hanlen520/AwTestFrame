package com.xiaoM.KeyWord.Appium;

import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Utils.AppiumExecuteScript;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;

public class ScriptModule {
    private Log log = new Log(this.getClass());
    private AppiumXMDriver driver;
    private String TestCategory;

    public ScriptModule(AppiumXMDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public Object script(Location location) throws Exception {
        Object result;
        try {
            AppiumExecuteScript executeScript = new AppiumExecuteScript(driver);
            result = executeScript.runScript(location);
            log.info(TestCategory + ":调用外部脚本执行成功 [ " + location.getValue() + " ]");
        } catch (Exception e) {
            log.error(TestCategory + ":调用外部脚本执行失败 [ " + location.getValue() + " ]");
            throw e;
        }
        return result;
    }
}
