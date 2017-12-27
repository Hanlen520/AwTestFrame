package com.xiaoM.BeginScript;

import java.io.IOException;

import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.UseDevice;
import org.hamcrest.StringDescription;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xiaoM.ReportUtils.TestListener;
import com.xiaoM.Utils.Run;

public class BeginScript{
	private Log log= new Log(this.getClass());
	@DataProvider(parallel=true)
	public Object[][]TestCases() throws IOException{
		return TestListener.RunCase;
	}

	@Test(dataProvider = "TestCases")
	public void runCase(String ID, String Type, String Description, String CaseName) throws Exception{
		String RunDevice = UseDevice.getDevice();
		String TestCategory = ID+"_"+RunDevice+"_"+CaseName;
		TestListener.Category.put(ID,TestCategory);
		log.info(TestCategory + " "+ Description);
		Run run = new Run();
		run.runCase(ID,RunDevice,Type, CaseName);
	}
}
