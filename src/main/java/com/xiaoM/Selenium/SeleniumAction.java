package com.xiaoM.Selenium;

import com.aventstack.extentreports.ExtentTest;
import com.xiaoM.KeyWord.DatabaseMoudle;
import com.xiaoM.KeyWord.JavaScriptModule;
import com.xiaoM.KeyWord.Selenium.*;
import com.xiaoM.KeyWord.SetModule;
import com.xiaoM.Utils.Location;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class SeleniumAction {
    private WebDriver driver;
    private String TestCategory;
    private Map<String, Object> returnMap;
    private ExtentTest extentTest;
    private String DeviceName;

    public SeleniumAction(WebDriver driver, String TestCategory, Map<String, Object> returnMap, ExtentTest extentTest, String DeviceName) {
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
        WebIframe webIframe;
        RunModule runModule;
        BroswerModule broswerModule;
        AlertModule alertModule;
        String method = location.getAction().toLowerCase();
        switch (method) {
            case "open":
                broswerModule = new BroswerModule(driver, TestCategory);
                return broswerModule.OpenUrl(location);
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
            case "longpresselement":
                clickModule = new ClickModule(driver, TestCategory);
                return clickModule.LongPressElement(location);
            case "longpresscoordinate":
                clickModule = new ClickModule(driver, TestCategory);
                return clickModule.LongPressCoordinate(location);
            case "dobuleclickelement":
                clickModule = new ClickModule(driver, TestCategory);
                return clickModule.DobuleClickElement(location);
            case "rightclickelement":
                clickModule = new ClickModule(driver, TestCategory);
                return clickModule.RightClickElement(location);
            case "hoverelement":
                clickModule = new ClickModule(driver, TestCategory);
                return clickModule.HoverElement(location);
            case "clickcoordinate":
                clickModule = new ClickModule(driver, TestCategory);
                return clickModule.ClickCoordinate(location);
            case "clickpicture":
                clickModule = new ClickModule(driver, TestCategory);
                return clickModule.ClickPicture(location);
            case "elementsendkeys":
                sendKeysModule = new SendKeysModule(driver, TestCategory);
                return sendKeysModule.SendKeys(location);
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
            case "windowmaxsize":
                broswerModule = new BroswerModule(driver, TestCategory);
                return broswerModule.WindowMaxSize();
            case "windowsetsize":
                broswerModule = new BroswerModule(driver, TestCategory);
                return broswerModule.WindowSetSize(location);
            case "switchtolastwindow":
                broswerModule = new BroswerModule(driver, TestCategory);
                return broswerModule.SwitchToLastWindow();
            case "switchtowindow":
                broswerModule = new BroswerModule(driver, TestCategory);
                return broswerModule.SwitchToWindow(location);
            case"windowrefresh":
                broswerModule = new BroswerModule(driver, TestCategory);
                return broswerModule.WindowRefresh();
            case"windowback":
                broswerModule = new BroswerModule(driver, TestCategory);
                return broswerModule.WindowBack();
            case"windowforward":
                broswerModule = new BroswerModule(driver, TestCategory);
                return broswerModule.WindowForward();
            case "acceptalert":
                alertModule = new AlertModule(driver, TestCategory);
                return alertModule.AcceptAlert();
            case "dismissalert":
                alertModule = new AlertModule(driver, TestCategory);
                return alertModule.DismissAlert();
            case "alertgettext":
                alertModule = new AlertModule(driver, TestCategory);
                return alertModule.AlertGetText();
            case "alertinputtext":
                alertModule = new AlertModule(driver, TestCategory);
                return alertModule.AlertInputText(location);
            default:
                throw new NullPointerException("不支持该关键字");
        }
    }
}
