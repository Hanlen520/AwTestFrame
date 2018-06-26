package com.xiaoM.Utils;

import com.xiaoM.BeginScript.BeginScript;

public class UseDevice {
	
	public static synchronized String getDevice (){
		String device = BeginScript.deviceList.get(0);
		BeginScript.deviceList.remove(0);
		return device;	
	}

	public static synchronized void addDevice(String DeviceName){
		BeginScript.deviceList.add(DeviceName);
	}
}
