package com.xiaoM.KeyWord.Appium;

import com.google.common.io.Files;
import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Element.LocationAppElement;
import com.xiaoM.Main.MainTest;
import com.xiaoM.Utils.FingerPrint;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.Match;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.Map;

public class CheckMoudle {
    private Log log = new Log(this.getClass());
    private AppiumXMDriver driver;
    private String TestCategory;
    private Map<String, Object> returnMap;

    public CheckMoudle(AppiumXMDriver driver, String TestCategory, Map<String, Object> returnMap) {
        this.driver = driver;
        this.TestCategory = TestCategory;
        this.returnMap = returnMap;
    }

    public boolean CheckElementNotNull(Location location) {
        log.info(TestCategory + "：检查控件不为空 [ " + location.getDescription() + " ]");
        LocationAppElement locationAppElement = new LocationAppElement(driver, TestCategory);
        try {
            locationAppElement.waitForElement(location);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean CheckElementIsNull(Location location) {
        log.info(TestCategory + "：检查控件为空 [ " + location.getDescription() + " ]");
        LocationAppElement locationAppElement = new LocationAppElement(driver, TestCategory);
        try {
            locationAppElement.waitForElement(location);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public boolean CheckTextByString(Location location) {
        String value_0 = location.getValue();
        String value_1 = location.getExpected();
        if (value_0.contains("${")) {
            value_0 = Match.replaceKeys(returnMap, value_0);
        } else if (value_1.contains("${")) {
            value_1 = Match.replaceKeys(returnMap, value_1);
        }
        log.info(TestCategory + "：文本校验(String) 预期值 [ " + value_1 + "  ] 实际值 [ " + value_0 + " ]");
        if (value_1.equals(value_0)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean CheckTextByInt(Location location) {
        String value_0 = location.getValue();
        String value_1 = location.getExpected();
        if (value_0.contains("${")) {
            value_0 = Match.replaceKeys(returnMap, value_0);
        } else if (value_1.contains("${")) {
            value_1 = Match.replaceKeys(returnMap, value_1);
        }
        log.info(TestCategory + "：文本校验(int) 预期值 [ " + value_1 + "  ] 实际值 [ " + value_0 + " ]");
        int A = Integer.valueOf(value_0);
        int B = Integer.valueOf(value_1);
        if (A == B) {
            return true;
        } else {
            return false;
        }
    }

    public boolean CheckTextByDouble(Location location) {
        String value_0 = location.getValue();
        String value_1 = location.getExpected();
        if (value_0.contains("${")) {
            value_0 = Match.replaceKeys(returnMap, value_0);
        } else if (value_1.contains("${")) {
            value_1 = Match.replaceKeys(returnMap, value_1);
        }
        log.info(TestCategory + "：文本校验(Double) 预期值 [ " + value_1 + "  ] 实际值 [ " + value_0 + " ]");
        Double C = Double.valueOf(value_0);
        Double D = Double.valueOf(value_1);
        if (C.equals(D)) {
            return true;
        } else {
            return false;
        }
    }

    public Object CheckPictureByOpenCV(Location location) {
        try {
            String value_0 = location.getValue();
            String value_1 = "./testCase/" + MainTest.TestCase + "/picture/" + location.getExpected();
            File dir = new File("./Temp/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Files.copy(new File(value_1), new File(dir.getAbsolutePath()+"/"+ location.getExpected()));
            opencv_core.IplImage src = opencv_imgcodecs.cvLoadImage(value_0);
            opencv_core.IplImage tmp = opencv_imgcodecs.cvLoadImage(dir.getAbsolutePath()+"/"+ location.getExpected());
            opencv_core.IplImage result = opencv_core.cvCreateImage(opencv_core.cvSize(src.width() - tmp.width() + 1, src.height() - tmp.height() + 1), opencv_core.IPL_DEPTH_32F, 1);
            opencv_core.cvZero(result);
            opencv_imgproc.cvMatchTemplate(src, tmp, result, opencv_imgproc.CV_TM_CCORR_NORMED);
            double[] minVal = new double[2];
            double[] maxVal = new double[2];
            opencv_core.cvMinMaxLoc(result, minVal, maxVal);
            double compare_result = maxVal[0];
            opencv_core.cvReleaseImage(src);
            opencv_core.cvReleaseImage(tmp);
            opencv_core.cvReleaseImage(result);
            if (compare_result > 0.95f) {
                log.info(TestCategory + "：调用OpenCV图片校验通过 [ 相似度: " + compare_result + " ]");
                return compare_result;
            } else {
                log.error(TestCategory + "：调用OpenCV图片校验失败 [ 相似度: " + compare_result + " ]");
                return compare_result;
            }
        } catch (Exception e) {
            log.error(TestCategory + "：调用OpenCV图片校验异常");
            e.printStackTrace();
        }
        return false;
    }

    public Object CheckPictureByHash(Location location) {
        try {
            String value_0 = location.getValue();
            String value_1 = "./testCase/" + MainTest.TestCase + "/picture/" + location.getExpected();
            File dir = new File("./Temp/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
           /* String value_0 = location.getValue();
            String value_1 = location.getExpected();*/
            FingerPrint fp1 = new FingerPrint(ImageIO.read(new File(value_0)));
            FingerPrint fp2 = new FingerPrint(ImageIO.read(new File( "./Temp/" + value_1)));
            double compare_result = fp1.compare(fp2);
            if (compare_result >= 0.8f) {
                log.info(TestCategory + "：调用哈希算法图片校验通过 [ 相似度: " + compare_result + " ]");
                return compare_result;
            } else {
                log.error(TestCategory + "：调用哈希算法图片校验失败 [ 相似度: " + compare_result + " ]");
                return compare_result;
            }
        } catch (Exception e) {
            log.error(TestCategory + "：调用哈希算法图片校验异常");
            e.printStackTrace();
        }
        return false;
    }
}
