package com.xiaoM.Utils;


import com.xiaoM.ReportUtils.TestListener;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;


import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

class PictureCompare {
    final private static String path = TestListener.ProjectPath + "/locationPicture/";

    /**
     * 获取指定控件的图像
     */
    static String captureElement(WebDriver driver, WebElement element) throws Exception {
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

}
