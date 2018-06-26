package com.xiaoM.IOS;

import com.xiaoM.Driver.AppiumXMDriver;
import org.openqa.selenium.JavascriptExecutor;

import java.util.HashMap;

public class IosSwipeScreen {
    final static String Scroll_UP = "Up";
    final static String SCroll_Down = "Down";
    final static String SCroll_Left = "Left";
    final static String SCroll_Right = "Right";

    public static void swipeUp(AppiumXMDriver driver)
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("direction", Scroll_UP);
        js.executeScript("mobile: scroll", scrollObject);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void swipeDown(AppiumXMDriver driver)// scroll down to refresh
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("direction", SCroll_Down);
        js.executeScript("mobile: scroll", scrollObject);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void swipeLeft(AppiumXMDriver driver)
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("direction", SCroll_Left);
        js.executeScript("mobile: scroll", scrollObject);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void swipeRight(AppiumXMDriver driver)
    {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("direction", SCroll_Right);
        js.executeScript("mobile: scroll", scrollObject);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
