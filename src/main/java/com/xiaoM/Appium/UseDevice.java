package com.xiaoM.Appium;

import com.xiaoM.BeginScript.BeginAppScript;

public class UseDevice {
	
	public static synchronized String getDevice (){
		String device = BeginAppScript.deviceList.get(0);
		BeginAppScript.deviceList.remove(0);
		return device;	
	}

	public static synchronized void addDevice(String DeviceName){
		BeginAppScript.deviceList.add(DeviceName);
	}
}
