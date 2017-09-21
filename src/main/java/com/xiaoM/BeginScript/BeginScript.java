package com.xiaoM.BeginScript;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xiaoM.ReportUtils.TestListener;
import com.xiaoM.Utils.Run;

public class BeginScript{
	
	@DataProvider
	public String[][]TestCases() throws IOException{
		return TestListener.RunCase;
	}

//	@Test(dataProvider = "TestCases")
//	public void runCase(String Type,String CaseName) throws Exception{
//		String RunDevice = "iphone 6SP";
//		TestListener.RunDevices.add(RunDevice);
//		Run run = new Run();
//		run.runCase(RusnDevice,Type, CaseName);
//	}
//	
//	@Test(dataProvider = "TestCases")
//	public void runCase2(String Type,String CaseName) throws Exception{
//		String RunDevice = "iphone 5S";
//		TestListener.RunDevices.add(RunDevice);
//		Run run = new Run();
//		run.runCase(RunDevice,Type, CaseName);
//	}
//	
	@Test(dataProvider = "TestCases")
	public void runCase3(String Type,String CaseName) throws Exception{
		String RunDevice = "三星i9192";
		TestListener.RunDevices.add(RunDevice);
		Run run = new Run();
		run.runCase(RunDevice,Type, CaseName);
	}
	
}
