package com.xiaoM.PageObject;

import org.openqa.selenium.By;

import com.xiaoM.Utils.ElementAction;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Android163Mail {
	
	public static boolean Login163(AppiumDriver <MobileElement> driver,String userName,String passWord){
		ElementAction.waitForElement(driver, By.id("com.netease.mail:id/editor_email"), 15).sendKeys(userName);
		ElementAction.waitForElement(driver, By.id("com.netease.mail:id/editor_password"), 15).sendKeys(passWord);
		ElementAction.waitForElement(driver, By.id("com.netease.mail:id/button_login"), 15).click();
		ElementAction.waitForElement(driver, By.id("com.netease.mail:id/btn_enter_mail"), 15).click();
		return true;
	}
	
	public static boolean SendMail(AppiumDriver <MobileElement> driver,String user,String title,String content){
		ElementAction.waitForElement(driver, By.id("com.netease.mail:id/iv_mail_list_plus"), 15).click();
		ElementAction.waitForElement(driver, By.xpath("//*[@resource-id='com.netease.mail:id/write_layout']"), 5).click();
		ElementAction.waitForElement(driver, By.id("com.netease.mail:id/mailcompose_address_input"), 5).sendKeys(user);
		driver.findElement(By.id("com.netease.mail:id/mailcompose_subject_textedit")).sendKeys(title);
		driver.findElement(By.id("com.netease.mail:id/mailcompose_content")).sendKeys(content);
		ElementAction.waitForElement(driver, By.id("com.netease.mail:id/tv_done"), 5).click();
		return true;
	}
}
