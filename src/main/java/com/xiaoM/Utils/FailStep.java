package com.xiaoM.Utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FailStep {
    public static void dealWithFailStep(int a,String[][] testStart ,ExtentTest test){
       for(int i=a+1;i<testStart.length;i++){
           List<String> parameteres = new ArrayList<>(Arrays.asList(testStart[i]));
           Location location = new Location();
           location.setLocation(parameteres);
           if (location.getIsRun().toLowerCase().equals("y")) {
               StringBuilder sb = new StringBuilder();
               String Step = location.getStep();
               String Description = location.getDescription();
               String Action = location.getAction();
               String Value = location.getValue();
               String Parameter = location.getParameter();
               sb.append("[步骤]: " + Step + "\r\n");
               sb.append("[步骤描述]: " + Description + "\r\n");
               sb.append("[操作方式]:" + Action + "\r\n");
               sb.append("[属性值]:" + Value + "\r\n");
               sb.append("[参数]：" + Parameter + "\r\n");
               sb.append("[返回值]：null");
               test.log(Status.SKIP, "<pre>" + sb.toString() + "</pre>");
           }
       }
   }

    public static void dealWithMoubleFailStep(int a, String[][] testStart, ExtentTest test, Location location2){
        for(int i=a+1;i<testStart.length;i++){
            List<String> parameteres = new ArrayList<>(Arrays.asList(testStart[i]));
            Location location = new Location();
            location.setLocation(parameteres);
            if (location.getIsRun().toLowerCase().equals("y")) {
                StringBuilder sb = new StringBuilder();
                String Step = location2.getValue() + "." + location.getStep();
                String Description = location.getDescription();
                String Action = location.getAction();
                String Value = location.getValue();
                String Parameter = location.getParameter();
                sb.append("[模块]: " + location2.getDescription() + "\r\n");
                sb.append("[步骤]: " + Step + "\r\n");
                sb.append("[步骤描述]: " + Description + "\r\n");
                sb.append("[操作方式]:" + Action + "\r\n");
                sb.append("[属性值]:" + Value + "\r\n");
                sb.append("[参数]：" + Parameter + "\r\n");
                sb.append("[返回值]：null");
                test.log(Status.SKIP, "<pre>" + sb.toString() + "</pre>");
            }
        }
    }
}
