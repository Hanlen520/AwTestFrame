package com.xiaoM.KeyWord.Selenium;

import com.google.common.io.Files;
import com.xiaoM.Main.MainTest;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.Picture;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import java.io.File;
import java.io.IOException;

public class WaitModule {
    private Log log = new Log(this.getClass());
    private WebDriver driver;
    private String TestCategory;

    public WaitModule(WebDriver driver, String TestCategory) {
        this.driver = driver;
        this.TestCategory = TestCategory;
    }

    public boolean waitByTime(Location location) {
        int millis = Integer.valueOf(location.getValue()) * 1000;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(TestCategory + "：等待 [ " + location.getValue() + "秒 ]");
        return true;
    }

    public boolean waitByPictureAppear(Location location) {
        String picturePath = "./testCase/" + MainTest.TestCase + "/picture/";
        File dir = new File("./Temp/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String value_1 = picturePath + location.getValue();
        try {
            Files.copy(new File(value_1), new File(dir.getAbsolutePath()+"/"+ location.getValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = "./Temp/";
        String screenshot = path + location.getValue();
        Screen screen = new Screen();
        Pattern from = new Pattern(screenshot);
        try {
            if (screen.wait(from, 10) != null) {
                screen.click(from);
            }
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
        }
        return true;
    }
}
