package com.xiaoM.Utils;

import java.util.List;

/**
 * 元素定位信息
 */
public class Location {

    private String IsRun;
    private String Step;
    private String Description;
    private String Action;
    private String Key;
    private String Value;
    private String TimeOut;
    private String Parameter;
    private String Expected;

    void setLocation(List<String> Parameteres){
        this.IsRun = Parameteres.get(1);
        this.Step = Parameteres.get(2);
        this.Description = Parameteres.get(3);
        this.Action = Parameteres.get(4);
        this.Key = Parameteres.get(5);
        this.Value = Parameteres.get(6);
        this.TimeOut = Parameteres.get(7);
        this.Parameter = Parameteres.get(8);
        this.Expected = Parameteres.get(9);
    }

    public String getIsRun() {
        return IsRun;
    }

    public String getStep() {
        return Step;
    }

    public String getDescription() {
        return Description;
    }

    public String getAction() {
        return Action;
    }

    public String getKey() {
        return Key;
    }

    public String getValue() {
        return Value;
    }

    public String getTimeOut() {
        return TimeOut;
    }

    public String getParameter() {
        return Parameter;
    }

    public String getExpected() {
        return Expected;
    }

    public void setParameter(String Parameter){
        this.Parameter = Parameter;
    }

    public void setValue(String value) {
        Value = value;
    }
}
