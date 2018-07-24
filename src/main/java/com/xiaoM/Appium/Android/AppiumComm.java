package com.xiaoM.Appium.Android;

import java.util.List;

public class AppiumComm {

	
	public static int cpuMaxComp(List<Integer> cpuList)  {
		int cpuMax = 0;// 计算最大值
		int cpu;
		for (int i = 0; i < cpuList.size(); i++) {
			cpu = cpuList.get(i);
			if (cpu > cpuMax) {
				cpuMax = cpu;
			}
		}
		return cpuMax;
	}
	public static int cpuAvg(List<Integer> cpuList)  {
		int cpuAvg = 0;// 计算均值
		int cpuSum = 0;
		for(int cpu:cpuList){
			cpuSum = cpuSum + cpu;
		}
		cpuAvg = Integer.valueOf(cpuSum/cpuList.size());		
		return cpuAvg;
	}
	public static Double memMaxComp(List<Double> menList)  {
		double memMax = 0;
		double men;
		for (int i = 0; i < menList.size(); i++) {
			men = menList.get(i);
			if (men > memMax) {
				memMax = men;
			}
		}
		return memMax;
	}
	public static Double menAvg(List<Double> menList)  {
		Double menAvg;// 计算均值
		Double menSum = 0.0;
		for(Double men:menList){
			menSum = menSum + men;
		}
		menAvg = menSum/menList.size();		
		return menAvg;
	}
}
