package com.xiaoM.Driver;

import com.xiaoM.BeginScript.BeginScript;
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
        HasSupportedPerformanceDataType, HidesKeyboardWithKeyName, ShakesDevice, HasIOSSettings,
        FindsByIosUIAutomation<T>, LocksIOSDevice, PerformsTouchID, FindsByIosNSPredicate<T>,
        FindsByIosClassChain<T> {

    private static String PLATFORM;

    /**
     * 后期可添加其他系统
     */
    static {
        switch (BeginScript.DeviceType.toLowerCase()) {
            case "android":
                PLATFORM = MobilePlatform.ANDROID;
                break;
            case "ios":
                PLATFORM = MobilePlatform.IOS;
                break;
        }
    }

    public AppiumXMDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, substituteMobilePlatform(desiredCapabilities, PLATFORM));
    }

    public AppiumXMDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, substituteMobilePlatform(desiredCapabilities, PLATFORM));
    }
}


