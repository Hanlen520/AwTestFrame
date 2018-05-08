package com.xiaoM.Utils;

import com.xiaoM.ReportUtils.TestListener;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;


import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

class Picture {
  private static String picturePath = TestListener.ProjectPath + "/testCase/" + TestListener.TestCase + "/picture/";
    /**
     * 获取指定控件的图像
     */
    static String captureElement(AppiumDriver<MobileElement> driver, WebElement element) throws Exception {
        // 截图整个页面
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        if (element != null) {
            BufferedImage img = ImageIO.read(screen);
            // 获得元素的高度和宽度
            int width = element.getSize().getWidth();
            int height = element.getSize().getHeight();
            // 创建一个矩形使用上面的高度，和宽度
            Rectangle rect = new Rectangle(width, height);
            // 得到元素的坐标
            Point p = element.getLocation();
            BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
            // 存为png格式
            ImageIO.write(dest, "png", screen);
        }
        return screen.getAbsolutePath();
    }


    /**
     * 获取目标图片在当前页面的坐标
     * 目标图片的分辨率不能和当前页面分辨率相差太多，否则获取的坐标误差会偏大
     *
     * @param driver
     * @param targetName(不能为中文名称)
     * @return
     */
    public static int[] getImageRecognitionLoc(AppiumDriver<MobileElement> driver, String targetName) {
        File scrFile = null;
        try {
            driver.context("NATIVE_APP");//切换到NATIVE_APP进行app截图
            scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        opencv_core.IplImage src = opencv_imgcodecs.cvLoadImage(scrFile.getAbsolutePath());
        opencv_core.IplImage tmp = opencv_imgcodecs.cvLoadImage(picturePath + targetName);
        opencv_core.IplImage result = opencv_core.cvCreateImage(opencv_core.cvSize(src.width() - tmp.width() + 1, src.height() - tmp.height() + 1), opencv_core.IPL_DEPTH_32F, 1);
        opencv_core.cvZero(result);
        opencv_imgproc.cvMatchTemplate(src, tmp, result, opencv_imgproc.CV_TM_CCORR_NORMED);
        double[] min_val = new double[2];
        double[] max_val = new double[2];
        int[] minLoc = new int[2];
        int[] startLoc = new int[2];
        opencv_core.cvMinMaxLoc(result, min_val, max_val, minLoc, startLoc, null);
        int[] endLoc = new int[2];
        endLoc[0] = startLoc[0] + tmp.width() / 2;
        endLoc[1] = startLoc[1] + tmp.height() / 2;
        opencv_core.cvReleaseImage(src);
        opencv_core.cvReleaseImage(tmp);
        opencv_core.cvReleaseImage(result);
        return endLoc;
    }

    /**
     * 模板匹配
     *
     * @param driver
     * @param targetName(不能为中文名称)
     * @return
     */
    public static boolean matchTemplate(AppiumDriver<MobileElement> driver, String targetName) {
        boolean matchRes;
        File scrFile = null;
        try {
            driver.context("NATIVE_APP");//切换到NATIVE_APP进行app截图
            scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        opencv_core.IplImage src = opencv_imgcodecs.cvLoadImage(scrFile.getAbsolutePath());
        opencv_core.IplImage tmp = opencv_imgcodecs.cvLoadImage(picturePath + targetName + ".png");
        opencv_core.IplImage result = opencv_core.cvCreateImage(opencv_core.cvSize(src.width() - tmp.width() + 1, src.height() - tmp.height() + 1), opencv_core.IPL_DEPTH_32F, 1);
        double[] minVal = new double[2];
        double[] maxVal = new double[2];
        opencv_core.cvMinMaxLoc(result, minVal, maxVal);
        matchRes = maxVal[0] > 0.99f ? true : false;
        opencv_core.cvReleaseImage(result);//释放图像
        return matchRes;
    }

    /**
     * 点击目标图片
     *
     * @param driver
     * @param targetName(不能为中文名称)
     */
    public static void pictureClick(AppiumDriver<MobileElement> driver, String targetName) {
        int[] targetLoc = getImageRecognitionLoc(driver, targetName);
        TouchAction action = new TouchAction(driver);
        action.tap(targetLoc[0], targetLoc[1]).perform();
    }

}
