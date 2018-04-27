package com.xiaoM.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.google.common.io.Files;
import com.xiaoM.ReportUtils.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

/**
 * 截图工具类
 * @author XiaoM
 *
 */
public class ScreenShot{
	private AppiumDriver <MobileElement> driver;
	private String TestCategory;
	Log log =new Log(this.getClass());
	
	public void setScreenName(String TestCategory){
		this.TestCategory = TestCategory;
	}
	public ScreenShot(AppiumDriver driver){
		this.driver = driver;
	}
	private void takeScreenshot(String screenPath) {
            //appium在Chrome没法截图，需用原生app下进行截图(这里纯属为了WAP端可以进行截图)
            driver.context("NATIVE_APP");//切换到NATIVE_APP进行app截图
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, new File(screenPath));
			log.error(TestCategory +" 错误截图："+screenPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void takeScreenshot() {
		String screenName = TestCategory;
		File dir = new File("test-result/snapshot");
		if (!dir.exists()){
			dir.mkdirs();
			}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd(HH.mm.ss)");
		String date = dateFormat.format(new Date());
		String path = "../test-result/snapshot/"+ screenName +"_"+ date +".jpg";
		TestListener.screenMessageList.put(TestCategory, path);
		String screenPath = dir.getAbsolutePath() + "\\" + screenName  +"_"+  date + ".jpg";
		takeScreenshot(screenPath);
	}

}
