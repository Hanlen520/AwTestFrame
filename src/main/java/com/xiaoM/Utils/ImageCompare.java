package com.xiaoM.Utils;

import java.io.File;

import io.appium.java_client.android.AndroidDriver;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.google.common.io.Files;
import com.xiaoM.ReportUtils.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class ImageCompare {

	private static String picturePath = TestListener.ProjectPath+"/picture/";
	
	/**
	 * 获取目标图片在当前页面的坐标</p>
	 * 目标图片的分辨率不能和当前页面分辨率相差多，否则获取的坐标误差会偏大
	 * @param driver
	 * @param targetName(不能为中文名称)
	 * @return
	 */
	public static int[] getImageRecognitionLoc(AppiumDriver <MobileElement> driver,String targetName){
		String screenPath = picturePath + targetName+"main.png";
		try {
			driver.context("NATIVE_APP");//切换到NATIVE_APP进行app截图
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, new File(screenPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		IplImage src = opencv_imgcodecs.cvLoadImage(picturePath + targetName+"main.png");
		IplImage tmp = opencv_imgcodecs.cvLoadImage(picturePath + targetName+".png");
		IplImage result = opencv_core.cvCreateImage(opencv_core.cvSize(src.width()-tmp.width()+1, src.height()-    tmp.height()+1), opencv_core.IPL_DEPTH_32F, 1);
		opencv_core.cvZero(result);
		opencv_imgproc.cvMatchTemplate(src, tmp, result, opencv_imgproc.CV_TM_CCORR_NORMED);
		double[] min_val = new double[2];
		double[] max_val = new double[2];
		int[] minLoc = new int[2];
		int[] startLoc = new int[2];  
		opencv_core.cvMinMaxLoc(result, min_val, max_val, minLoc, startLoc, null);	
		int[] endLoc = new int[2];
		endLoc[0] = startLoc[0]+tmp.width()/2;
		endLoc[1] = startLoc[1]+tmp.height()/2;
		opencv_core.cvReleaseImage(src);
		opencv_core.cvReleaseImage(tmp);
		opencv_core.cvReleaseImage(result);
		new File(screenPath).delete();
		return endLoc;
	}
	
	/**
	 * 模板匹配
	 * @param driver
	 * @param targetName(不能为中文名称)
	 * @return
	 */
	public boolean matchTemplate(AppiumDriver <MobileElement> driver,String targetName) {
		boolean matchRes;
		String screenPath = picturePath + targetName+"main.png";
		try {
			driver.context("NATIVE_APP");//切换到NATIVE_APP进行app截图
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, new File(screenPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		IplImage src = opencv_imgcodecs.cvLoadImage(picturePath + targetName+"main.png");
		IplImage tmp = opencv_imgcodecs.cvLoadImage(picturePath + targetName+".png");
		IplImage result = opencv_core.cvCreateImage(opencv_core.cvSize(src.width()-tmp.width()+1, src.height()-    tmp.height()+1), opencv_core.IPL_DEPTH_32F, 1);
		double[] minVal = new double[2];
		double[] maxVal = new double[2];
		opencv_core.cvMinMaxLoc(result, minVal, maxVal);
		matchRes = maxVal[0] > 0.99f ? true : false;
		opencv_core.cvReleaseImage(result);//释放图像
		new File(screenPath).delete();
		return matchRes;
	}
	
	/**
	 * 点击目标图片
	 * @param driver
	 * @param targetName(不能为中文名称)
	 */
	public static void imageClick(AppiumDriver <MobileElement> driver,String targetName){
		int[] targetLoc = ImageCompare.getImageRecognitionLoc(driver, targetName);
		TouchAction action = new TouchAction (driver);
		action.tap(targetLoc[0], targetLoc[1]).perform();
	}
}
