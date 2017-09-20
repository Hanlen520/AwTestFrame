package com.xiaoM.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.xiaoM.ReportUtils.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseDriver {

	public  AppiumDriver <MobileElement> setUpApp(String device) throws IOException {
		AppiumServerUtils  AppiumServer = null;
		URL url;
		int Port = PortProber.getFreePort();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		if(TestListener.DeviceType.equals("Android")){
			String devicesPath = TestListener.ProjectPath + "/devices/AndroidDevices.xlsx";
			String[][] DeviceBase = IOMananger.readExcelDataXlsx(device,devicesPath);
			AppiumServer = new AppiumServerUtils(DeviceBase[1][2],Port,String.valueOf(PortProber.getFreePort()));
			url = AppiumServer.startServer();
			File appDir=new File(TestListener.ProjectPath,"Apps");
			File app =new File(appDir,TestListener.AppName+".apk");
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceBase[2][2]);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceBase[3][2]);
			capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
//			capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.netease.mail");
//			capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.netease.mobimail.activity.LaunchActivity");
			capabilities.setCapability(MobileCapabilityType.NO_RESET, TestListener.ResetApp);
			capabilities.setCapability(MobileCapabilityType.UDID, DeviceBase[2][2]);
			capabilities.setCapability("unicodeKeyboard", "True");
			capabilities.setCapability("resetKeyboard", "True");
			capabilities.setCapability("noSign", "True");	
		}else{
			String devicesPath = TestListener.ProjectPath + "/devices/iOSDevices.xlsx";
			String[][] DeviceBase = IOMananger.readExcelDataXlsx(device,devicesPath);
			AppiumServer = new AppiumServerUtils();
			url = AppiumServer.startServer(DeviceBase[1][2],Port);
			String bundleId = TestListener.bundleId;
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceBase[3][2]);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceBase[4][2]);
			capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 500000);
			capabilities.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, true);//iOS并发必须要重新装WDA
			capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT,PortProber.getWDAFreePort());//WDA端口
			capabilities.setCapability(MobileCapabilityType.UDID, DeviceBase[2][2]);
			capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID,bundleId);
		}	
		return new AppiumDriver<MobileElement>(url, capabilities);
	}

	public AppiumDriver <MobileElement> setUpWap(String device) throws IOException {
		AppiumServerUtils  AppiumServer = null;
		URL url;
		int Port = PortProber.getFreePort();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		if(TestListener.DeviceType.equals("Android")){
			String devicesPath = TestListener.ProjectPath + "/devices/AndroidDevices.xlsx";
			String[][] DeviceBase = IOMananger.readExcelDataXlsx(device,devicesPath);
			AppiumServer = new AppiumServerUtils(DeviceBase[1][2],Port,String.valueOf(PortProber.getFreePort()));
			url = AppiumServer.startServer();
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
			capabilities.setCapability(CapabilityType.BROWSER_NAME, DeviceBase[4][2]);// Browser
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceBase[2][2]);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceBase[3][2]);
			capabilities.setCapability(MobileCapabilityType.UDID, DeviceBase[2][2]);
			capabilities.setCapability("unicodeKeyboard", "True");
			capabilities.setCapability("resetKeyboard", "True");
		}else{
			String devicesPath = TestListener.ProjectPath + "/devices/iOSDevices.xlsx";
			String[][] DeviceBase = IOMananger.readExcelDataXlsx(device,devicesPath);
			AppiumServer = new AppiumServerUtils();
			url = AppiumServer.startServer(DeviceBase[1][2],Port);
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
			capabilities.setCapability(CapabilityType.BROWSER_NAME, DeviceBase[5][2]);// Browser
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceBase[3][2]);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceBase[4][2]);
			capabilities.setCapability(IOSMobileCapabilityType.START_IWDP, true);
//			capabilities.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, true);//iOS并发必须要重新装WDA
//			capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT,PortProber.getWDAFreePort());//WDA端口
			capabilities.setCapability(MobileCapabilityType.UDID, DeviceBase[2][2]);	
		}
		return new AppiumDriver <MobileElement>(url, capabilities);
	}
}
