package com.xiaoM.KeyWord.Appium;

import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.Picture;

public class WaitModule {
    private Log log = new Log(this.getClass());
    private AppiumXMDriver driver;
    private String TestCategory;

    public WaitModule(AppiumXMDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean waitByTime(Location location) {
        log.info(TestCategory + "：等待 [ " + location.getValue() + "秒 ]");
        int millis = Integer.valueOf(location.getValue()) * 1000;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean waitByPictureAppear(Location location) throws Exception {
        log.error(TestCategory + "：等待目标图片出现 [  "+location.getValue()+"  ]");
        long time = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() > time + 6000) {
                throw new NoSuchFieldException();
            }
            if (Picture.matchTemplate(driver, location.getValue(),TestCategory)) {
                return true;
            }
        }
    }
}
