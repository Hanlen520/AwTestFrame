package com.xiaoM.Android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xiaoM.BeginScript.BeginAppScript;
import com.xiaoM.KeyWord.Appium.AdbMoudle;
import com.xiaoM.Utils.IOMananger;

public class MemThread extends Thread {
	
	Double memForThisTime;
	String appPackage;
	String device;
	String driverName;
	
	public MemThread(String appPackage,String device,String driverName) {
		this.appPackage = appPackage;
		this.device = device;
		this.driverName = driverName;
	}

	public void run() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String date;
		String workSpace = BeginAppScript.ProjectPath+"/test-result/MonitorResoure/Mem";
		while(true){
			try {
				Thread.sleep(50);
				memForThisTime = getMobileMem(appPackage,device);
				date = dateFormat.format(new Date());
				IOMananger.saveToFile(workSpace, driverName, String.valueOf(memForThisTime+" "+date));
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 测试内存数据
	 * @param appPackage 包名
	 * @return Double类型的内存数据?(MB)
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static Double getMobileMem(String appPackage,String device) throws IOException, InterruptedException {
		String line;
		String mem;
		ProcessBuilder pb = new ProcessBuilder(AdbMoudle.adb, "-s",device, "shell", "dumpsys meminfo | grep " + appPackage);
		Process process = pb.start();
		InputStream is = process.getInputStream();
		BufferedReader bf = new BufferedReader(new InputStreamReader(is));
		while ((line = bf.readLine()) != null) {
			if (line.contains("activities")) {
				// 截取前面数字部分
				mem = line.substring(0, 9);
				// 删除空格 并返回Double
				mem = mem.trim();
				return ((Double.valueOf(mem)) / 1024);
			}
		}
		return 0.0;
	}
}
