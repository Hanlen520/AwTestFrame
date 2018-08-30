package com.xiaoM.Driver;

import java.net.URL;

import com.aventstack.extentreports.ExtentTest;
import com.xiaoM.BeginScript.BeginAppScript;
import com.xiaoM.Utils.IOMananger;
import com.xiaoM.Utils.PortProber;
import com.xiaoM.Appium.UseDevice;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppBaseDriver {
    AppiumServerUtils AppiumServer = null;
    AppiumXMDriver driver = null;

    public AppiumXMDriver setUpApp(String DeviceName, ExtentTest extentTest,String CaseName){
        URL url;
        String[][] DeviceBase = IOMananger.readExcelDataXlsx(BeginAppScript.DeviceConfig, DeviceName);
        int Port = PortProber.getFreePort();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {
            switch (BeginAppScript.DeviceType.toLowerCase()) {
                case "android":
                        if (DeviceBase == null) {
                            extentTest.fail("设备不存在，请确认 AndroidDevices.xlsx 中存在 " + DeviceName + " 的设备参数");
                            throw new NullPointerException();
                        }
                        AppiumServer = new AppiumServerUtils(DeviceBase[1][2], Port, String.valueOf(PortProber.getFreePort()));
                        url = AppiumServer.startServer(CaseName);
                        String appPath = BeginAppScript.ProjectPath + "/testCase/" + BeginAppScript.TestCase + "/apps/" + BeginAppScript.AppName;
                        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
                        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceBase[2][2]);
                        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceBase[3][2]);
                        capabilities.setCapability(MobileCapabilityType.APP, appPath);
                        if (BeginAppScript.PackageName != null && BeginAppScript.Activity != null) {
                            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, BeginAppScript.PackageName);
                            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, BeginAppScript.Activity);
                        }
                        capabilities.setCapability(MobileCapabilityType.NO_RESET, BeginAppScript.ResetApp);
                        capabilities.setCapability(MobileCapabilityType.UDID, DeviceBase[2][2]);
                        capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
                        capabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);
                        break;
                case "ios":
                        if (DeviceBase == null) {
                            extentTest.fail("设备不存在，请确认 iOSDevices.xlsx 中存在 " + DeviceName + " 的设备参数");
                            throw new Exception();
                        }
                        AppiumServer = new AppiumServerUtils();
                        url = AppiumServer.startServer(DeviceBase[1][2], Port,CaseName);
                        String bundleId = BeginAppScript.bundleId;
                        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
                        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceBase[3][2]);
                        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceBase[4][2]);
                        capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
                        capabilities.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, true);//iOS并发必须要重新装WDA
                        capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, PortProber.getWDAFreePort());//WDA端口
                        capabilities.setCapability(MobileCapabilityType.UDID, DeviceBase[2][2]);
                        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, bundleId);
                        break;
                default:
                    UseDevice.addDevice(DeviceName);
                    extentTest.fail("请在 config.xml 中配置正确的设备系统类型：Android/iOS");
                    throw new Exception();
            }
            driver = new AppiumXMDriver<MobileElement>(url, capabilities);
        } catch (Exception e) {
            extentTest.fail("设备 " + DeviceName + " 启动失败，请确实设备参数配置正确");
            e.printStackTrace();
        }
        return driver;
    }

    public AppiumXMDriver setUpWap(String DeviceName, ExtentTest extentTest,String CaseName) throws Exception {
        URL url;
        String[][] DeviceBase = IOMananger.readExcelDataXlsx(BeginAppScript.DeviceConfig, DeviceName);
        int Port = PortProber.getFreePort();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {
            switch (BeginAppScript.DeviceType.toLowerCase()) {
                case "android":
                        if (DeviceBase == null) {
                            extentTest.fail("设备不存在，请确认 AndroidDevices.xlsx 中存在 " + DeviceName + " 的设备参数");
                            throw new Exception();
                        }
                        AppiumServer = new AppiumServerUtils(DeviceBase[1][2], Port, String.valueOf(PortProber.getFreePort()));
                        url = AppiumServer.startServer(CaseName);
                        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
                        capabilities.setCapability(CapabilityType.BROWSER_NAME, DeviceBase[4][2]);// Browser
                        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceBase[2][2]);
                        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceBase[3][2]);
                        capabilities.setCapability(MobileCapabilityType.UDID, DeviceBase[2][2]);
                        capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
                        capabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);
                        break;
                case "ios":
                        if (DeviceBase == null) {
                            extentTest.fail("设备不存在，请确认 iOSDevices.xlsx 中存在 " + DeviceName + " 的设备参数");
                            throw new Exception();
                        }
                        AppiumServer = new AppiumServerUtils();
                        url = AppiumServer.startServer(DeviceBase[1][2], Port,CaseName);
                        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
                        capabilities.setCapability(CapabilityType.BROWSER_NAME, DeviceBase[5][2]);// Browser
                        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceBase[3][2]);
                        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceBase[4][2]);
                        capabilities.setCapability(IOSMobileCapabilityType.START_IWDP, true);
                        capabilities.setCapability(MobileCapabilityType.UDID, DeviceBase[2][2]);
                        break;
                default:
                    UseDevice.addDevice(DeviceName);
                    extentTest.fail("请在 config.xml 中配置正确的设备系统类型：Android/iOS");
                    throw new Exception();
            }
            driver = new AppiumXMDriver<MobileElement>(url, capabilities);
        } catch (Exception e) {
            extentTest.fail("设备 " + DeviceName + " 启动失败，请确实设备参数配置正确");
            e.printStackTrace();
        }
        return driver;
    }

    public AppiumServerUtils getAppiumServer() {
        return AppiumServer;
    }
}
