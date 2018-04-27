package com.xiaoM.Android;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.xiaoM.ReportUtils.TestListener;
import com.xiaoM.Utils.IOMananger;
import com.xiaoM.Utils.Log;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ResourceMonitoring {
    private Log log = new Log(this.getClass());
    private DecimalFormat df = new DecimalFormat("0.00");//格式化数值，保留两位小数

    public void startMonitoring(String DeviceName, String TestCategory) throws Exception {
        try {
            String[][] DeviceBase = IOMananger.readExcelDataXlsx(TestListener.DeviceConfig,DeviceName);
            log.info(TestCategory + " 启动资源监控器");
            String CpuPath = TestListener.ProjectPath + "/test-result/MonitorResoure/Cpu/" + DeviceName + ".txt";
            String MenPath = TestListener.ProjectPath + "/test-result/MonitorResoure/Mem/" + DeviceName + ".txt";
            String NetPath = TestListener.ProjectPath + "/test-result/MonitorResoure/Net/" + DeviceName + ".txt";
            String[] Paths = {CpuPath, MenPath, NetPath};
            IOMananger.deleteFile(Paths);//删除监控日志文件
            AppiumComm.getMobileAppNet(TestListener.PackageName, DeviceBase[2][2], DeviceName);
            CpuThread cpuThread = new CpuThread(TestListener.PackageName, DeviceBase[2][2], DeviceName); // CPU监控线程1
            MemThread memThread = new MemThread(TestListener.PackageName, DeviceBase[2][2], DeviceName);//内存监控线程2
            cpuThread.start();// CPU监控线程启动
            memThread.start();// 内存监控线程启动
        } catch (Exception e) {
            log.error(TestCategory + " 启动资源监控器失败");
            throw e;
        }
    }

    public void stopMonitoring(String DeviceName, String TestCategory) throws Exception {
        String[][] DeviceBase = IOMananger.readExcelDataXlsx(TestListener.DeviceConfig,DeviceName);
        AppiumComm.getMobileAppNet(TestListener.PackageName, DeviceBase[2][2], DeviceName);
        String CpuPath = TestListener.ProjectPath + "/test-result/MonitorResoure/Cpu/" + DeviceName + ".txt";
        String MenPath = TestListener.ProjectPath + "/test-result/MonitorResoure/Mem/" + DeviceName + ".txt";
        String NetPath = TestListener.ProjectPath + "/test-result/MonitorResoure/Net/" + DeviceName + ".txt";
        List<Integer> cpuList = new ArrayList<Integer>();
        List<Double> menList = new ArrayList<Double>();
        List<Integer> NetList = new ArrayList<Integer>();
        String[] Men;
        String[] Cpu;
        AppiumComm.adbClearCache(TestListener.PackageName, DeviceName);
        AppiumComm.forceStop(TestListener.PackageName, DeviceName);
        String appPackageActivity = TestListener.PackageName + "/" + TestListener.Activity;
        String luanchTime = AppiumComm.appLuanchTime(appPackageActivity, DeviceBase[2][2]);
        try {
            int cpuMax;
            Double memMax;
            List<String> Cpus = IOMananger.readTxtFile(CpuPath);
            int a;
            double k;
            for (int i = 0; i < Cpus.size(); i++) {
                Cpu = Cpus.get(i).split(" ");
                a = Integer.parseInt(Cpu[0]);
                cpuList.add(a);
            }
            List<String> Mens = IOMananger.readTxtFile(MenPath);
            for (int i = 0; i < Mens.size(); i++) {
                Men = Mens.get(i).split(" ");
                k = Double.valueOf(Men[0]);
                menList.add(k);
            }
            List<String> Nets = IOMananger.readTxtFile(NetPath);
            while (Nets.size() != 4) {
                Nets = IOMananger.readTxtFile(NetPath);
            }
            for (String net : Nets) {
                int i = Integer.parseInt(net);
                NetList.add(i);
            }
            PieChartPicture picture = new PieChartPicture(DeviceName, CpuPath, MenPath, TestCategory);
            picture.createScreen();
            cpuMax = AppiumComm.cpuMaxComp(cpuList);
            memMax = AppiumComm.memMaxComp(menList);
            int cpuAvg = AppiumComm.cpuAvg(cpuList);
            double menAvg = AppiumComm.menAvg(menList);
            String netshangxing = df.format((double) (NetList.get(2) - NetList.get(0)) / 1024.0);
            String netxiaxing = df.format((double) (NetList.get(3) - NetList.get(1)) / 1024.0);
            log.info(TestCategory + " " + DeviceName + " 首次启动时延为：" + luanchTime + "ms");
            log.info(TestCategory + " " + DeviceName + " 执行业务时CPU峰值为：" + cpuMax + "%");
            log.info(TestCategory + " " + DeviceName + " 执行业务时CPU均值为：" + cpuAvg + "%");
            log.info(TestCategory + " " + DeviceName + " 执行业务时内存峰值为：" + df.format(memMax) + "MB");
            log.info(TestCategory + " " + DeviceName + " 执行业务时内存均值为：" + df.format(menAvg) + "MB");
            log.info(TestCategory + " " + DeviceName + " 上行流量：" + netshangxing + "KB");
            log.info(TestCategory + " " + DeviceName + " 下行流量：" + netxiaxing + "KB");
            log.info(TestCategory + " " + DeviceName + " 关闭资源监控器");
        } catch (Exception e) {
            log.error(TestCategory + " " + DeviceName + " 读取资源监控信息失败");
            throw e;
        }
    }

    public static void main(String[] args) {
        String appPackageActivity = TestListener.PackageName + "/" + TestListener.Activity;
        String luanchTime = AppiumComm.appLuanchTime(appPackageActivity, "209e61b5");
        System.out.println(luanchTime);
    }
}
