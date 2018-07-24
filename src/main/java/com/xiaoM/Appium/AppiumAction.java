package com.xiaoM.Appium;

import com.aventstack.extentreports.ExtentTest;
import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.KeyWord.Appium.*;
import com.xiaoM.KeyWord.DatabaseMoudle;
import com.xiaoM.KeyWord.JavaScriptModule;
import com.xiaoM.KeyWord.SetModule;
import com.xiaoM.Utils.Location;

import java.util.Map;

public class AppiumAction {
    private AppiumXMDriver driver;
    private String TestCategory;
    private Map<String, Object> returnMap;
    private ExtentTest extentTest;
    private String DeviceName;

    public AppiumAction(AppiumXMDriver driver, String TestCategory, Map<String, Object> returnMap, ExtentTest extentTest, String DeviceName) {
        this.driver = driver;
        this.TestCategory = TestCategory;
        this.returnMap = returnMap;
        this.extentTest = extentTest;
        this.DeviceName = DeviceName;
    }

    public Object action(Location location) throws Exception {
        SetModule setModule;
        GetModule getModule;
        ClickModule clickModule;
        SendKeysModule sendKeysModule;
        WaitModule waitModule;
        SelectModule selectModule;
        JavaScriptModule javaScriptModule;
        CheckMoudle checkMoudle;
        DatabaseMoudle databaseMoudle;
        ScriptModule scriptModule;
        SwitchMoudle switchMoudle;
        WebIframe webIframe;
        RunModule runModule;
        DeviceMoudle deviceMoudle;
        AdbMoudle adbMoudle;
        String method = location.getAction().toLowerCase();
        switch (method) {
            case "setparam":
                setModule = new SetModule();
                return setModule.setPsaram(TestCategory, returnMap, location);
            case "getelement":
                getModule = new GetModule(driver, TestCategory);
                return getModule.getElement(location);
            case "getelementtext":
                getModule = new GetModule(driver, TestCategory);
                return getModule.getElementText(location);
            case "getelementsize":
                getModule = new GetModule(driver, TestCategory);
                return getModule.getElementSize(location);
            case "getelementpicture":
                getModule = new GetModule(driver, TestCategory);
                return getModule.getElementiPcture(location);
            case "getelementipcturetext":
                getModule = new GetModule(driver, TestCategory);
                return getModule.getElementiPctureText(location);
            case "getelementiattribute":
                getModule = new GetModule(driver, TestCategory);
                return getModule.getElementiAttribute(location);
            case "clickelement":
                clickModule = new ClickModule(driver, TestCategory);
                return clickModule.ClickElement(location);
            case "clickcoordinate":
                clickModule = new ClickModule(driver, TestCategory);
                return clickModule.ClickCoordinate(location);
            case "clickpicture":
                clickModule = new ClickModule(driver, TestCategory);
                return clickModule.ClickPicture(location);
            case "elementsendkeys":
                sendKeysModule = new SendKeysModule(driver, TestCategory);
                return sendKeysModule.SendKeys(location);
            case "adbsendkeys":
                adbMoudle = new AdbMoudle(driver, TestCategory);
                return adbMoudle.adbInputText(DeviceName,location);
            case "waitbytime":
                waitModule = new WaitModule(driver, TestCategory);
                return waitModule.waitByTime(location);
            case "waitbypictureappear":
                waitModule = new WaitModule(driver, TestCategory);
                return waitModule.waitByPictureAppear(location);
            case "selectbyindex":
                selectModule = new SelectModule(driver, TestCategory);
                return selectModule.selectByIndex(location);
            case "selectbyvalue":
                selectModule = new SelectModule(driver, TestCategory);
                return selectModule.selectByValue(location);
            case "selectbytext":
                selectModule = new SelectModule(driver, TestCategory);
                return selectModule.selectByText(location);
            case "javascript":
                javaScriptModule = new JavaScriptModule(driver, TestCategory);
                return javaScriptModule.javaScriptMethod(location);
            case "checkelementnotnull":
                checkMoudle = new CheckMoudle(driver, TestCategory, returnMap);
                return checkMoudle.CheckElementNotNull(location);
            case "checkelementisnull":
                checkMoudle = new CheckMoudle(driver, TestCategory, returnMap);
                return checkMoudle.CheckElementIsNull(location);
            case "checktextbystring":
                checkMoudle = new CheckMoudle(driver, TestCategory, returnMap);
                return checkMoudle.CheckTextByString(location);
            case "checktextbyint":
                checkMoudle = new CheckMoudle(driver, TestCategory, returnMap);
                return checkMoudle.CheckTextByInt(location);
            case "checktextbydouble":
                checkMoudle = new CheckMoudle(driver, TestCategory, returnMap);
                return checkMoudle.CheckTextByDouble(location);
            case "checkpicturebyopencv":
                checkMoudle = new CheckMoudle(driver, TestCategory, returnMap);
                return checkMoudle.CheckPictureByOpenCV(location);
            case "checkpicturebyhash":
                checkMoudle = new CheckMoudle(driver, TestCategory, returnMap);
                return checkMoudle.CheckPictureByHash(location);
            case "databaseupdata":
                databaseMoudle = new DatabaseMoudle(TestCategory, returnMap);
                return databaseMoudle.dataBaseUpdata(location);
            case "databasequery":
                databaseMoudle = new DatabaseMoudle(TestCategory, returnMap);
                return databaseMoudle.dataBaseQuery(location);
            case "script":
                scriptModule = new ScriptModule(driver, TestCategory);
                return scriptModule.script(location);
            case "switchtowebview":
                switchMoudle = new SwitchMoudle(driver, TestCategory);
                return switchMoudle.switchToWebview();
            case "switchtonative":
                switchMoudle = new SwitchMoudle(driver, TestCategory);
                return switchMoudle.switchToNative();
            case "switchtoframe":
                webIframe = new WebIframe(driver, TestCategory);
                return webIframe.switchToIframe(location);
            case "switchtoparentframe":
                webIframe = new WebIframe(driver, TestCategory);
                return webIframe.switchToParentFrame();
            case "switchtodefaultcontent":
                webIframe = new WebIframe(driver, TestCategory);
                return webIframe.switchToDefaultContent();
            case "module":
                runModule = new RunModule(driver, TestCategory, returnMap, extentTest, DeviceName);
                return runModule.moduleMethod(location);
            case "swipeup":
                deviceMoudle = new DeviceMoudle(driver, TestCategory, DeviceName);
                return deviceMoudle.SwipeUp();
            case "swipedown":
                deviceMoudle = new DeviceMoudle(driver, TestCategory, DeviceName);
                return deviceMoudle.SwipeDown();
            case "swipeleft":
                deviceMoudle = new DeviceMoudle(driver, TestCategory, DeviceName);
                return deviceMoudle.SwipeLeft();
            case "swiperight":
                deviceMoudle = new DeviceMoudle(driver, TestCategory, DeviceName);
                return deviceMoudle.SwipeRight();
            case "restartapp":
                deviceMoudle = new DeviceMoudle(driver, TestCategory, DeviceName);
                return deviceMoudle.restartApp();
            case "removeapp":
                deviceMoudle = new DeviceMoudle(driver, TestCategory, DeviceName);
                return deviceMoudle.removeApp(location);
            case "resetapp":
                deviceMoudle = new DeviceMoudle(driver, TestCategory, DeviceName);
                return deviceMoudle.resetApp();
            case "installapp":
                deviceMoudle = new DeviceMoudle(driver, TestCategory, DeviceName);
                return deviceMoudle.installApp(location);
            case"pressandroidkeycode":
                adbMoudle = new AdbMoudle(driver, TestCategory);
                return adbMoudle.PressAndroidKeycode(location);
            default:
                throw new NullPointerException("不支持该关键字");
        }
    }
}
