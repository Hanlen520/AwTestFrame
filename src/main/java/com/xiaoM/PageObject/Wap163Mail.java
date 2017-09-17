package com.xiaoM.PageObject;

import org.openqa.selenium.By;

import com.xiaoM.Utils.ElementAction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Wap163Mail {
	
	public static boolean WapLogin163(AppiumDriver <MobileElement> driver,String userName,String passWord) throws InterruptedException{
		driver.get("http://smart.mail.163.com/");
		ElementAction.waitForElement(driver, By.id("pop_mailEntry"), 15).click();
		ElementAction.waitForElement(driver, By.name("account"), 15).sendKeys(userName);
		ElementAction.waitForElement(driver, By.xpath("/html/body/div[1]/div/div[2]/form/div[3]/div/div/input"), 15).sendKeys(passWord);
		ElementAction.waitForElement(driver, By.xpath("/html/body/div[1]/div/div[2]/form/div[6]/div/button"), 15).click();
		Thread.sleep(5000);
		return true;
	}
}
