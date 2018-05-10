package com.xiaoM.Driver;

import com.xiaoM.ReportUtils.TestListener;
import io.appium.java_client.*;
import io.appium.java_client.android.*;
import io.appium.java_client.ios.LocksIOSDevice;
import io.appium.java_client.ios.PerformsTouchID;
import io.appium.java_client.ios.ShakesDevice;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;

import java.net.URL;


public class AppiumXMDriver<T extends WebElement> extends AppiumDriver<T>
            implements PressesKeyCode, HasNetworkConnection, PushesFiles, StartsActivity,
        FindsByAndroidUIAutomator<T>, LocksAndroidDevice, HasAndroidSettings, HasDeviceDetails,
        HasSupportedPerformanceDataType,HidesKeyboardWithKeyName, ShakesDevice, HasIOSSettings,
        FindsByIosUIAutomation<T>, LocksIOSDevice, PerformsTouchID, FindsByIosNSPredicate<T>,
        FindsByIosClassChain<T> {



    private static String PLATFORM ;

    /**
     * 后期可添加其他系统
     */
    static{
        if(TestListener.DeviceType.toLowerCase().equals("android")){
            PLATFORM = MobilePlatform.ANDROID;
        }else{
            PLATFORM = MobilePlatform.IOS;
        }
    }

    public AppiumXMDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, substituteMobilePlatform(desiredCapabilities, PLATFORM));
    }

    /**
     * @param service take a look
     *                at {@link io.appium.java_client.service.local.AppiumDriverLocalService}
     * @param desiredCapabilities take a look
     *                            at {@link org.openqa.selenium.Capabilities}
     */
    public AppiumXMDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, substituteMobilePlatform(desiredCapabilities, PLATFORM));
    }
}

