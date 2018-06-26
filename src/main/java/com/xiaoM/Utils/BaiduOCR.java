package com.xiaoM.Utils;


import com.baidu.aip.ocr.AipOcr;
import com.xiaoM.Driver.AppiumXMDriver;
import io.appium.java_client.MobileElement;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;

import java.util.HashMap;

public class BaiduOCR {
    /*private static String APP_ID = TestListener.OcrConfig.get("百度文字识别")[2];
    private static String API_KEY = TestListener.OcrConfig.get("百度文字识别")[3];
    private static String SECRET_KEY = TestListener.OcrConfig.get("百度文字识别")[4];*/
    private static String APP_ID = "10742805";
    private static String API_KEY = "IYfajNLAmiaKQXqMUqdAigtA";
    private static String SECRET_KEY = "5pAoDz0BImDOqqVwDfSr8WAxQ4ORTZat";

    public synchronized static String getPictureText(AppiumXMDriver<MobileElement> driver , WebElement element) throws Exception {
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        String imagePath = Picture.captureElement(driver,element);
        JSONObject response = client.webImage(imagePath, new HashMap<>());
        return response.get("words_result").toString().split("\"")[3];
    }
}
