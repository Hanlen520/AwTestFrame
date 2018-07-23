package com.xiaoM.Driver;

import java.net.URL;

import com.xiaoM.BeginScript.BeginAppScript;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

/**
 * @author xiaoM
 * @since 2017-03-30
 */
public class AppiumServerUtils {
	String ipAddress;
	int port;
	String bp;
	public AppiumDriverLocalService service;
	
	public AppiumServerUtils(){
	}
	public AppiumServerUtils(String ipAddress, int port,String bp){
		this.ipAddress = ipAddress;
		this.port = port;
		this.bp = bp;
	}
	
	/**
	 * 使用默认（即127.0.0.1:4723）
	 * @return
	 */
	public URL startAppiumServerByDefault() {
		AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
		service.start();
		if (service == null || !service.isRunning()) {
			throw new RuntimeException("An appium server node is not started!");
		}
		return service.getUrl();
	}
	
	public URL startAppiumServerNoReset() {
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
		service.start();
		if (service == null || !service.isRunning()) {
			throw new RuntimeException("An appium server node is not started!");
		}
		return service.getUrl();
	}

	public URL startServer() {
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.withIPAddress(ipAddress);
		builder.usingPort(port);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, BeginAppScript.Log_Level.toLowerCase());
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, bp);
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		if (service == null || !service.isRunning()) {
			throw new RuntimeException("An appium server node is not started!");
		}
		return service.getUrl();
	}
	
	public URL startServer(String ipAddress,int port) {
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.withIPAddress(ipAddress);
		builder.usingPort(port);
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		if (service == null || !service.isRunning()) {
			throw new RuntimeException("An appium server node is not started!");
		}
		return service.getUrl();
	}


}
