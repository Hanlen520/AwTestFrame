package com.xiaoM.KeyWord;

import com.xiaoM.Android.AndroidSwipeScreen;
import com.xiaoM.Android.AppiumComm;
import com.xiaoM.BeginScript.BeginScript;
import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.IOS.IosSwipeScreen;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;

public class DeviceMoudle {
    private Log log = new Log(this.getClass());
    private AppiumXMDriver driver;
    private String TestCategory;
    private String DeviceName;

    public DeviceMoudle(AppiumXMDriver driver, String TestCategory,String DeviceName) {
        this.driver = driver;
        this.TestCategory = TestCategory;
        this.DeviceName = DeviceName;
    }

    public boolean SwipeDown(){
        try {
            if(BeginScript.DeviceType.toLowerCase().equals("android")){
                AndroidSwipeScreen.swipeDown(driver);
            }else{
                IosSwipeScreen.swipeDown(driver);
            }
            log.info(TestCategory + "：向下滑动屏幕成功");
        } catch (Exception e) {
            log.error(TestCategory + "：向下滑动屏幕失败");
            throw e;
        }
        return true;
    }

    public boolean SwipeLeft(){
        try {
            if(BeginScript.DeviceType.toLowerCase().equals("android")){
                AndroidSwipeScreen.swipeLeft(driver);
            }else{
                IosSwipeScreen.swipeLeft(driver);
            }
            log.info(TestCategory + "：向左滑动屏幕成功");
        } catch (Exception e) {
            log.error(TestCategory + "：向左滑动屏幕失败");
            throw e;
        }
        return true;
    }

    public boolean SwipeRight(){
        try {
            if(BeginScript.DeviceType.toLowerCase().equals("android")){
                AndroidSwipeScreen.swipeRight(driver);
            }else{
                IosSwipeScreen.swipeRight(driver);
            }
            log.info(TestCategory + "：向右滑动屏幕成功");
        } catch (Exception e) {
            log.error(TestCategory + "：向右滑动屏幕失败");
            throw e;
        }
        return true;
    }

    public boolean SwipeUp(){
        try {
            if(BeginScript.DeviceType.toLowerCase().equals("android")){
                AndroidSwipeScreen.swipeUp(driver);
            }else{
                IosSwipeScreen.swipeUp(driver);
            }
            log.info(TestCategory + "：向上滑动屏幕成功");
        } catch (Exception e) {
            log.error(TestCategory + "：向上滑动屏幕失败");
            throw e;
        }
        return true;
    }

    public boolean restartApp(){
        try {
            if(BeginScript.DeviceType.toLowerCase().equals("android")){
                AdbMoudle.forceStop(BeginScript.PackageName,DeviceName);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AdbMoudle.adbStartAPP(BeginScript.AppName+"/"+BeginScript.Activity,DeviceName);
            }else{
                log.warn("IOS设备不支持 restartApp 关键字");
            }
            log.info(TestCategory + "：重启应用成功");
        } catch (Exception e) {
            log.error(TestCategory + "：重启应用失败");
            throw e;
        }
        return true;
    }

    public boolean installApp(Location location){
        try {
            if(BeginScript.DeviceType.toLowerCase().equals("android")){
                driver.installApp(location.getValue());
            }else{
                log.warn("IOS设备不支持 installApp 关键字");
            }
            log.info(TestCategory + "：安装应用成功");
        } catch (Exception e) {
            log.error(TestCategory + "：安装应用失败");
            throw e;
        }
        return true;
    }

    public boolean resetApp(){
        try {
            driver.resetApp();
            log.info(TestCategory + "：重置应用成功");
        } catch (Exception e) {
            log.error(TestCategory + "：重置应用失败");
            throw e;
        }
        return true;
    }

    public boolean removeApp(Location location){
        try {
            driver.removeApp(location.getValue());
            log.info(TestCategory + "：移除应用成功");
        } catch (Exception e) {
            log.error(TestCategory + "：移除应用失败");
            throw e;
        }
        return true;
    }

}
