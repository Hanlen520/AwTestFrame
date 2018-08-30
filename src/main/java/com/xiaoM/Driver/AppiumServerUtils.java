package com.xiaoM.Driver;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;

import com.tigervnc.rdr.Exception;
import com.xiaoM.BeginScript.BeginAppScript;
import com.xiaoM.Main.MainTest;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

/**
 * @author xiaoM
 * @since 2017-03-30
 */
public class AppiumServerUtils {
    String ipAddress;
    int port;
    String bp;
    String src = "testCase/" + MainTest.TestCase + "/test-result/log";
    public AppiumDriverLocalService service;

    public AppiumServerUtils() {
    }

    public AppiumServerUtils(String ipAddress, int port, String bp) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.bp = bp;
    }

    /**
     * 使用默认（即127.0.0.1:4723）
     *
     * @return
     */
    public URL startAppiumServerByDefault() {
        AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
        service.start();
        if (service == null || !service.isRunning()) {
            throw new RuntimeException("An appium server node is not started!");
        }
        return service.getUrl();
    }

    public URL startAppiumServerNoReset() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
        service.start();
        if (service == null || !service.isRunning()) {
            throw new RuntimeException("An appium server node is not started!");
        }
        return service.getUrl();
    }

    public URL startServer(String appiumLogFileName) throws Exception {
        Field streamField = null;
        Field streamsField = null;
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress(ipAddress);
        builder.usingPort(port);
        builder.withArgument(GeneralServerFlag.LOG_LEVEL, BeginAppScript.Log_Level.toLowerCase());
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        builder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, bp);
        builder.withLogFile(new File(src + "/" + appiumLogFileName + ".log"));
        service = AppiumDriverLocalService.buildService(builder);
        try {
            streamField = AppiumDriverLocalService.class.getDeclaredField("stream");
            streamField.setAccessible(true);
            streamsField = Class.forName("io.appium.java_client.service.local.ListOutputStream")
                    .getDeclaredField("streams");
            streamsField.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            ((ArrayList<OutputStream>) streamsField.get(streamField.get(service))).clear(); // remove System.out logging
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        service.start();
        if (service == null || !service.isRunning()) {
           throw new RuntimeException("An appium server node is not started!");
        }
        return service.getUrl();
    }

    public URL startServer(String ipAddress, int port, String appiumLogFileName)throws Exception {
        Field streamField = null;
        Field streamsField = null;
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress(ipAddress);
        builder.usingPort(port).withLogFile(new File(src + "/" + appiumLogFileName + ".log"));
        service = AppiumDriverLocalService.buildService(builder);
        try {
            streamField = AppiumDriverLocalService.class.getDeclaredField("stream");
            streamField.setAccessible(true);
            streamsField = Class.forName("io.appium.java_client.service.local.ListOutputStream")
                    .getDeclaredField("streams");
            streamsField.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            ((ArrayList<OutputStream>) streamsField.get(streamField.get(service))).clear(); // remove System.out logging
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        service.start();
        if (service == null || !service.isRunning()) {
           throw new RuntimeException("An appium server node is not started!");
        }
        return service.getUrl();
    }


}
