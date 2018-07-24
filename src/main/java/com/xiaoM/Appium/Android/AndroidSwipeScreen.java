package com.xiaoM.Appium.Android;

import com.xiaoM.Driver.AppiumXMDriver;
import io.appium.java_client.TouchAction;

import java.time.Duration;

public class AndroidSwipeScreen {
    static Duration duration=Duration.ofSeconds(1);
    public static void swipeUp(AppiumXMDriver driver)
    {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        TouchAction action1 = new TouchAction(driver).press(width / 2,height * 4/ 5).waitAction(duration).moveTo(width /2, height /4).release();
        action1.perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void swipeDown(AppiumXMDriver driver)// scroll down to refresh
    {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        TouchAction action1 = new TouchAction(driver).press(width / 2,height/4).waitAction(duration).moveTo(width /2, height* 3/4).release();
        action1.perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void swipeLeft(AppiumXMDriver driver)
    {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        TouchAction action1 = new TouchAction(driver).press(width -10,height/2).waitAction(duration).moveTo(width /4, height /2).release();
        action1.perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void swipeRight(AppiumXMDriver driver)
    {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        TouchAction action1 = new TouchAction(driver).press(10,height/2).waitAction(duration).moveTo(width *3/4+10, height /2).release();
        action1.perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
