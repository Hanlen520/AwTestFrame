package com.xiaoM.PageObject;

import org.openqa.selenium.By;

import com.xiaoM.Utils.ElementAction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class IOS163Mail {
	
	public static boolean IosLogin163(AppiumDriver <MobileElement> driver,String userName,String passWord) throws InterruptedException{
		ElementAction.waitForElement(driver, By.name("163/QQ/Gmail/企业邮箱等"), 15).sendKeys(userName);
		ElementAction.waitForElement(driver, By.name("密码"), 15).sendKeys(passWord);
		ElementAction.waitForElement(driver, By.xpath("//*[@name='登录']"), 15).click();
		Thread.sleep(5000);
		return true;
	}
}
