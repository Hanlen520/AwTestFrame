package com.xiaoM.Utils;

import com.xiaoM.ReportUtils.TestListener;

public class UseDevice {
	static String device;
	
	public static synchronized String getDevice (){
		device = TestListener.deviceList.get(0);
		TestListener.deviceList.remove(0);
		return device;	
	}

	public static synchronized void addDevice(String DeviceName){
		TestListener.deviceList.add(DeviceName);
	}
}
