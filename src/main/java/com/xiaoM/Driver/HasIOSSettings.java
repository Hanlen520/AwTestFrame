package com.xiaoM.Driver;

import io.appium.java_client.HasSettings;
import io.appium.java_client.Setting;

interface HasIOSSettings extends HasSettings {
    /**
     * Set the `nativeWebTap` setting. *iOS-only method*.
     * Sets whether Safari/webviews should convert element taps into x/y taps
     * @param enabled turns nativeWebTap on if true, off if false
     */
    default void nativeWebTap(Boolean enabled) {
        setSetting(Setting.NATIVE_WEB_TAP, enabled);
    }
}
