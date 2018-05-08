package com.xiaoM.Utils;

import java.io.File;
import java.net.URL;

import com.aventstack.extentreports.ExtentTest;
import org.aspectj.weaver.ast.Test;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.xiaoM.ReportUtils.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

 class BaseDriver {
    AppiumServerUtils AppiumServer = null;

     AppiumDriver setUpApp(String DeviceName,ExtentTest extentTest) throws Exception {
        URL url;
        String[][] DeviceBase = IOMananger.readExcelDataXlsx(TestListener.DeviceConfig, DeviceName);
        int Port = PortProber.getFreePort();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        switch (TestListener.DeviceType.toLowerCase()) {
            case "android":
                if (DeviceBase == null) {
                    UseDevice.addDevice(DeviceName);
                    extentTest.fail("设备不存在，请确认 AndroidDevices.xlsx 中存在 " + DeviceName + " 的设备参数");
                    throw new Exception();
                }
                AppiumServer = new AppiumServerUtils(DeviceBase[1][2], Port, String.valueOf(PortProber.getFreePort()));
                url = AppiumServer.startServer();
                String appPath = TestListener.ProjectPath+"/testCase/"+TestListener.TestCase+"/apps/"+TestListener.AppName + ".apk";
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceBase[2][2]);
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceBase[3][2]);
                capabilities.setCapability(MobileCapabilityType.APP, appPath);
                if (TestListener.PackageName != null && TestListener.Activity != null) {
                    capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, TestListener.PackageName);
                    capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, TestListener.Activity);
                }
                capabilities.setCapability(MobileCapabilityType.NO_RESET, TestListener.ResetApp);
                capabilities.setCapability(MobileCapabilityType.UDID, DeviceBase[2][2]);
                capabilities.setCapability("unicodeKeyboard", true);
                capabilities.setCapability("resetKeyboard", true);
                capabilities.setCapability("noSign", true);
                break;
            case "ios":
                if (DeviceBase == null) {
                    UseDevice.addDevice(DeviceName);
                    extentTest.fail("设备不存在，请确认 iOSDevices.xlsx 中存在 " + DeviceName + " 的设备参数");
                    throw new Exception();
                }
                AppiumServer = new AppiumServerUtils();
                url = AppiumServer.startServer(DeviceBase[1][2], Port);
                String bundleId = TestListener.bundleId;
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
                extentTest.fail("请在 config.xml 中配置 " + DeviceName + " 正确的设备系统类型：Android/iOS");
                throw new Exception();
        }
        return new AppiumDriver<MobileElement>(url, capabilities);
    }

     AppiumDriver setUpWap(String DeviceName,ExtentTest extentTest) throws Exception {
        URL url;
        String[][] DeviceBase = IOMananger.readExcelDataXlsx(TestListener.DeviceConfig, DeviceName);
        int Port = PortProber.getFreePort();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        switch (TestListener.DeviceType.toLowerCase()) {
            case "android":
                if (DeviceBase == null) {
                    UseDevice.addDevice(DeviceName);
                    extentTest.fail("设备不存在，请确认 AndroidDevices.xlsx 中存在 " + DeviceName + " 的设备参数");
                    throw new Exception();
                }
                AppiumServer = new AppiumServerUtils(DeviceBase[1][2], Port, String.valueOf(PortProber.getFreePort()));
                url = AppiumServer.startServer();
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
                capabilities.setCapability(CapabilityType.BROWSER_NAME, DeviceBase[4][2]);// Browser
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceBase[2][2]);
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceBase[3][2]);
                capabilities.setCapability(MobileCapabilityType.UDID, DeviceBase[2][2]);
                capabilities.setCapability("unicodeKeyboard", true);
                capabilities.setCapability("resetKeyboard", true);
                break;
            case "ios":
                if (DeviceBase == null) {
                    UseDevice.addDevice(DeviceName);
                    extentTest.fail("设备不存在，请确认 iOSDevices.xlsx 中存在 " + DeviceName + " 的设备参数");
                    throw new Exception();
                }
                AppiumServer = new AppiumServerUtils();
                url = AppiumServer.startServer(DeviceBase[1][2], Port);
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
                extentTest.fail("请在 config.xml 中配置 " + DeviceName + " 正确的设备系统类型：Android/iOS");
                throw new Exception();
        }
        return new AppiumDriver<MobileElement>(url, capabilities);
    }

     AppiumServerUtils getAppiumServer() {
        return AppiumServer;
    }
}
