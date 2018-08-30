package com.xiaoM.KeyWord.Selenium;

import com.xiaoM.Element.LocationWebElement;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class KeyboardMode {
    Log log = new Log(this.getClass());
    private String TestCategory;
    private WebDriver driver;

    public KeyboardMode(WebDriver driver, String TestCategory) {
        this.TestCategory = TestCategory;
        this.driver = driver;
    }

    private Keys getKeys(String value) {
        switch (value) {
            case "add":
                return Keys.ADD;
            case "tab":
                return Keys.TAB;
            case "enter":
                return Keys.ENTER;
            case "backspace":
                return Keys.BACK_SPACE;
            case "esc":
                return Keys.ESCAPE;
            case "alt":
                return Keys.ALT;
            case "ctrl":
                return Keys.CONTROL;
            case "shift":
                return Keys.SHIFT;
            case "space":
                return Keys.SPACE;
            case "help":
                return Keys.HELP;
            case "home":
                return Keys.HOME;
            case "insert":
                return Keys.INSERT;
            case "pagedown":
                return Keys.PAGE_DOWN;
            case "pageup":
                return Keys.PAGE_UP;
            case "pause":
                return Keys.PAUSE;
            case "arrowdown":
                return Keys.ARROW_DOWN;
            case "arrowup":
                return Keys.ARROW_UP;
            case "arrowleft":
                return Keys.ARROW_LEFT;
            case "arrowright":
                return Keys.ARROW_RIGHT;
            case "down":
                return Keys.DOWN;
            case "up":
                return Keys.UP;
            case "left":
                return Keys.LEFT;
            case "right":
                return Keys.RIGHT;
            case "delete":
                return Keys.DELETE;
            case "end":
                return Keys.END;
            case "multiply":
                return Keys.MULTIPLY;
            case "meta":
                return Keys.META;
            case "f1":
                return Keys.F1;
            case "f2":
                return Keys.F2;
            case "f3":
                return Keys.F3;
            case "f4":
                return Keys.F4;
            case "f5":
                return Keys.F5;
            case "f6":
                return Keys.F6;
            case "f7":
                return Keys.F7;
            case "f8":
                return Keys.F8;
            case "f9":
                return Keys.F9;
            case "f10":
                return Keys.F10;
            case "f11":
                return Keys.F11;
            case "f12":
                return Keys.F12;
            case "numpad0":
                return Keys.NUMPAD0;
            case "numpad1":
                return Keys.NUMPAD1;
            case "numpad2":
                return Keys.NUMPAD2;
            case "numpad3":
                return Keys.NUMPAD3;
            case "numpad4":
                return Keys.NUMPAD4;
            case "numpad5":
                return Keys.NUMPAD5;
            case "numpad6":
                return Keys.NUMPAD6;
            case "numpad7":
                return Keys.NUMPAD7;
            case "numpad8":
                return Keys.NUMPAD8;
            case "numpad9":
                return Keys.NUMPAD9;
        }
        return null;
    }

    public boolean SendKeys(Location location) {
        log.info(TestCategory + "：模拟键盘操作 [ " + location.getParameter().toLowerCase() + " ]");
        String value = location.getParameter().contains("\\+") ? location.getParameter().split("\\+")[1].trim() : location.getParameter();
        if (location.getParameter().toLowerCase().contains("shift")) {
            if (location.getValue().isEmpty() || location.getValue() == "") {
                new Actions(driver).keyDown(Keys.SHIFT).sendKeys(value).keyUp(Keys.SHIFT).perform();
            } else {
                WebElement element = new LocationWebElement(driver, TestCategory).waitForElement(location);
                new Actions(driver).keyDown(Keys.SHIFT).sendKeys(element, value).keyUp(Keys.SHIFT).perform();
            }
        } else if (location.getParameter().toLowerCase().contains("ctrl")) {
            if (location.getValue().isEmpty() || location.getValue() == "") {
                new Actions(driver).keyDown(Keys.CONTROL).sendKeys(value).keyUp(Keys.CONTROL).perform();
            } else {
                WebElement element = new LocationWebElement(driver, TestCategory).waitForElement(location);
                new Actions(driver).keyDown(Keys.CONTROL).sendKeys(element, value).keyUp(Keys.CONTROL).perform();
            }
        } else if (location.getParameter().toLowerCase().contains("alt")) {
            if (location.getValue().isEmpty() || location.getValue() == "") {
                new Actions(driver).keyDown(Keys.ALT).sendKeys(value).keyUp(Keys.ALT).perform();
            } else {
                WebElement element = new LocationWebElement(driver, TestCategory).waitForElement(location);
                new Actions(driver).keyDown(Keys.ALT).sendKeys(element, value).keyUp(Keys.ALT).perform();
            }
        } else {
            if (location.getValue().isEmpty() || location.getValue() == "") {
                new Actions(driver).sendKeys(getKeys(location.getParameter().toLowerCase())).perform();
            } else {
                WebElement element = new LocationWebElement(driver, TestCategory).waitForElement(location);
                new Actions(driver).sendKeys(element, getKeys(location.getParameter().toLowerCase())).perform();
            }
        }
        return true;
    }
}

