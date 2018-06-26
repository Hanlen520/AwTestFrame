package com.xiaoM.KeyWord;

import com.xiaoM.BeginScript.BeginScript;
import com.xiaoM.Utils.IOMananger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdbMoudle {
    public static String adb = "adb";

    /**
     * 获得应用的UID
     * @param appPackage 应用包名
     * @return UID
     * @throws IOException
     */
    public static String getMobileAppUid(String appPackage,String device) {
        //CMD 上运行需要改为adb shell "dumpsys package com.netease.mail | grep userId="
        ProcessBuilder pb = new ProcessBuilder(adb, "-s",device ,"shell", "dumpsys package " + appPackage + " | grep userId=");
        try {
            Process p = pb.start();
            String line = null;
            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream(), "utf-8"));
            if ((line = bf.readLine()) != null) {
                return line.split("=")[1].split(" ")[0]; // 分割字符串
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取应用流量数据
     * @param appPackage 应用包名
     * @return 上下行流量数据
     */
    public static void getMobileAppNet(String appPackage,String device,String driverName) {
        String line;
        String[] splitArr;
        String uid = getMobileAppUid(appPackage,device);
        ProcessBuilder pb1 = new ProcessBuilder(adb, "-s",device,"shell", "cat /proc/net/xt_qtaguid/stats" + " | grep " + uid);
        try {
            Process p = pb1.start();
            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream(), "utf-8"));
            int rx = 0;
            int st = 0;
            while ((line = bf.readLine()) != null) {
                splitArr = line.split(" ");
                for (int x = 0; x < splitArr.length; x++) {
                    if (x == 5) {
                        rx += Integer.parseInt(splitArr[x]);//下行数据
                    }
                    if (x == 7) {
                        st += Integer.parseInt(splitArr[x]);
                    }
                }
            }
            String workSpace = BeginScript.ProjectPath+"/test-result/MonitorResoure/Net";
            IOMananger.saveToFile(workSpace, driverName, String.valueOf(rx));
            IOMananger.saveToFile(workSpace, driverName, String.valueOf(st));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 强制停止应用
     * @param appPackage 包名
     * @throws IOException
     */
    public static void forceStop(String appPackage,String device) {
        ProcessBuilder pb = new ProcessBuilder(adb,"-s",device, "shell", "am force-stop " + appPackage);
        try {
            pb.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 清除缓存
     *
     * @param appPackage
     * @return
     */
    public static void adbClearCache(String appPackage,String device) {
        ProcessBuilder pb1 = new ProcessBuilder(adb,"-s",device,"shell", "pm", "clear", appPackage);
        try {
            pb1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 启动app
     * @param appPackageActivity
     * @param device
     */
    public static void adbStartAPP(String appPackageActivity,String device) {
        ProcessBuilder pb1 = new ProcessBuilder(adb,"-s",device, "shell", "am", "start", "-n", appPackageActivity);
        try {
            pb1.start();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    /**
     * app启动时延
     * @return
     */
    public static String appLuanchTime(String appPackageActivity,String device){
        ProcessBuilder pb1 = new ProcessBuilder(adb,"-s",device, "shell", "am", "start", "-W","-n", appPackageActivity);
        List<String> results = new ArrayList<String>();
        String Time =null;
        try {
            Process process =pb1.start();
            Scanner scanner = new Scanner(process.getInputStream());
            while (scanner.hasNextLine()) {
                results.add(scanner.nextLine());
            }
            scanner.close();
            for(String result:results){
                if(result.contains("ThisTime:")){
                    Time = result.split(": ")[1];
                }
            }
            return Time;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public boolean adbInputText(String device,String text){
        try {
            new ProcessBuilder(adb,"-s",device, "shell", "input", "text", text).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;

    }

}
