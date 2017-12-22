package com.xiaoM.BeginScript;

import java.io.IOException;

import com.xiaoM.Utils.UseDevice;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xiaoM.ReportUtils.TestListener;
import com.xiaoM.Utils.Run;

public class BeginScript{
	
	@DataProvider(parallel=true)
	public Object[][]TestCases() throws IOException{
		return TestListener.RunCase;
	}

	@Test(dataProvider = "TestCases")
	public void runCase3(String Type,String CaseName) throws Exception{
		String RunDevice = UseDevice.getDevice();
		Run run = new Run();
		run.runCase(RunDevice,Type, CaseName);
	}
}
