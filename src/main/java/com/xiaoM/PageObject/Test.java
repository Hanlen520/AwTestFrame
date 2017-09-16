package com.xiaoM.PageObject;

import org.openqa.selenium.By;

import com.xiaoM.Utils.ElementAction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Test{
	
	public boolean testWap(AppiumDriver <MobileElement> driver,String http) throws InterruptedException{
//		driver.get(http);
		ElementAction.waitForElement(driver, By.id("index-kw"), 5).sendKeys("hello world");
		ElementAction.waitForElement(driver, By.id("index-bn"), 5).click();
		Thread.sleep(5000);
		return true;
	}
	
	public  String returnUrl(){
		return "http://www.baidu.com";
	}
	
	public boolean node2(String sdfg){
		System.out.println("==========================>"+sdfg);
		return true;
	}
	
	public boolean node(String sdfg,String sjssj){
		System.out.println("==========================>"+sdfg);
		System.out.println("==========================>"+sjssj);
		return true;
	}
	
	public boolean ClickTest(AppiumDriver <MobileElement> driver,String q,String w) throws InterruptedException{
		ElementAction.waitForElement(driver, By.id("com.testerhome.webview:id/next_page"), 5).click();
		ElementAction.waitForElement(driver, By.xpath("//*[@text='取消']"), 5).click();
		System.out.println("==========================>"+q);
		return true;
	}
	
	public boolean ClickTest2(AppiumDriver <MobileElement> driver,String q) throws InterruptedException{
		System.out.println("==========================>"+q);
		return true;
	}
	
}
