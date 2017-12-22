package com.xiaoM.Utils;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.xiaoM.ReportUtils.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseDriver {
	Log log= new Log(this.getClass());
	AppiumServerUtils  AppiumServer = null;
	public AppiumDriver <MobileElement> setUpApp(String device,String CaseName) throws Exception{
		URL url ;
		String devicesPath ;
		String[][] DeviceBase;
		int Port = PortProber.getFreePort();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		switch (TestListener.DeviceType.toLowerCase()){
			case "android":
				devicesPath = TestListener.ProjectPath + "/devices/AndroidDevices.xlsx";
				DeviceBase  = IOMananger.readExcelDataXlsx(device,devicesPath);
				if(DeviceBase==null){
					TestListener.notDescriptionFailCaseName.add(CaseName);
					UseDevice.addDevice(device);
					throw new Exception("设备不存在，请确认 AndroidDevices.xlsx 中存在 "+device +" 的设备参数");
				}
				AppiumServer = new AppiumServerUtils(DeviceBase[1][2],Port,String.valueOf(PortProber.getFreePort()));
				url = AppiumServer.startServer();
				File appDir=new File(TestListener.ProjectPath,"Apps");
				File app =new File(appDir,TestListener.AppName+".apk");
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceBase[2][2]);
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceBase[3][2]);
				capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
				if(TestListener.PackageName!=null && TestListener.Activity!=null){
					capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, TestListener.PackageName);
					capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, TestListener.Activity);
				}
				capabilities.setCapability(MobileCapabilityType.NO_RESET, TestListener.ResetApp);
				capabilities.setCapability(MobileCapabilityType.UDID, DeviceBase[2][2]);
				capabilities.setCapability("unicodeKeyboard", "True");
				capabilities.setCapability("resetKeyboard", "True");
				capabilities.setCapability("noSign", "True");
				break;
			case "ios":
				devicesPath = TestListener.ProjectPath + "/devices/iOSDevices.xlsx";
				DeviceBase  = IOMananger.readExcelDataXlsx(device,devicesPath);
				if(DeviceBase==null){
					TestListener.notDescriptionFailCaseName.add(CaseName);
					UseDevice.addDevice(device);
					throw new Exception("设备不存在，请确认 iOSDevices.xlsx 中存在 "+device +" 的设备参数");
				}
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
				break;
			default:
				TestListener.notDescriptionFailCaseName.add(CaseName);
				UseDevice.addDevice(device);
				throw new Exception("请在 config.properties 中配置 "+device+" 正确的设备系统类型：Android/iOS");
		}
		return new AppiumDriver<MobileElement>(url, capabilities);
	}

	public AppiumDriver <MobileElement> setUpWap(String device,String CaseName) throws Exception {
		URL url = null;
		String devicesPath ;
		String[][] DeviceBase;
		int Port = PortProber.getFreePort();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		switch (TestListener.DeviceType.toLowerCase()){
			case "android":
				devicesPath = TestListener.ProjectPath + "/devices/AndroidDevices.xlsx";
				DeviceBase = IOMananger.readExcelDataXlsx(device,devicesPath);
				if(DeviceBase==null){
					TestListener.notDescriptionFailCaseName.add(CaseName);
					UseDevice.addDevice(device);
					throw new Exception("设备不存在，请确认 AndroidDevices.xlsx 中存在 "+device +" 的设备参数");
				}
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
				break;
			case "ios":
				devicesPath = TestListener.ProjectPath + "/devices/iOSDevices.xlsx";
				DeviceBase = IOMananger.readExcelDataXlsx(device,devicesPath);
				if(DeviceBase==null){
					TestListener.notDescriptionFailCaseName.add(CaseName);
					UseDevice.addDevice(device);
					throw new Exception("设备不存在，请确认 iOSDevices.xlsx 中存在 "+device +" 的设备参数");
				}
				AppiumServer = new AppiumServerUtils();
				url = AppiumServer.startServer(DeviceBase[1][2],Port);
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
				capabilities.setCapability(CapabilityType.BROWSER_NAME, DeviceBase[5][2]);// Browser
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceBase[3][2]);
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceBase[4][2]);
				capabilities.setCapability(IOSMobileCapabilityType.START_IWDP, true);
				capabilities.setCapability(MobileCapabilityType.UDID, DeviceBase[2][2]);
				break;
			default:
				TestListener.notDescriptionFailCaseName.add(CaseName);
				UseDevice.addDevice(device);
				throw new Exception("请在 config.properties 中配置 "+device+" 正确的设备系统类型：Android/iOS");
		}
		return new AppiumDriver <MobileElement>(url, capabilities);
	}

	public AppiumServerUtils getAppiumServer(){
		return AppiumServer;
	}
}
