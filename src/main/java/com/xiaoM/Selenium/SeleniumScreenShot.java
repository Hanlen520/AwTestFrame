package com.xiaoM.Selenium;

import com.google.common.io.Files;
import com.xiaoM.Main.MainTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SeleniumScreenShot {
   private WebDriver driver;
   private String TestCategory;
    public void setScreenName(String TestCategory){
       this.TestCategory = TestCategory;
   }
   public SeleniumScreenShot(WebDriver driver){
       this.driver = driver;
   }
   private void takeScreenshot(String screenPath) {
       try {
           File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
           Files.copy(scrFile, new File(screenPath));
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   public void takeScreenshot() {
       String screenName =TestCategory;
       File dir = new File("./testCase/" + MainTest.TestCase + "/test-result/snapshot");
       if (!dir.exists()){
           dir.mkdirs();
       }
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd(HH.mm.ss)");
       String date = dateFormat.format(new Date());
       String path = "../test-result/snapshot/"+ screenName+ "_"+date+".png";
       MainTest.screenMessageList.put(TestCategory,path);
       String screenPath = dir.getAbsolutePath().concat("/")+ screenName+ "_"+date+".png";
       takeScreenshot(screenPath);
   }

}
