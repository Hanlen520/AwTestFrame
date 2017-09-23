package com.xiaoM.Utils;

import java.util.HashMap;
import java.util.Map;

import com.xiaoM.Android.ResourceMonitoring;
import com.xiaoM.ReportUtils.TestListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Run {
	Log log= new Log(this.getClass());
	public void  runCase(String DeviceName,String Type ,String CaseName) throws Exception{
		AppiumDriver <MobileElement> driver = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if(Type.equals("APP")){
			BaseDriver base = new BaseDriver();
			driver = base.setUpApp(DeviceName);
		}else{
			BaseDriver base = new BaseDriver();
			driver = base.setUpWap(DeviceName);
		}
		String[][] testStart = null;
		try {
			testStart = IOMananger.readExcelDataXlsx(CaseName,TestListener.CasePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResourceMonitoring RM = new ResourceMonitoring();
		boolean StartRM = false;
		if(TestListener.Resource_Monitoring.equals("True") && Type.equals("APP")&&TestListener.DeviceType.equals("Android")){
			RM.startMonitoring(driver, DeviceName);
			StartRM = true;
		}
		long StartTime = System.currentTimeMillis();
		log.info(DeviceName+"  测试用例:"+ CaseName +"---Start");
		try {
			for(int a=1;a<testStart.length;a++){
				if(testStart[a][0].equals("YES")){
					log.info(DeviceName+" --------------------------------------");
					String ClassName = testStart[a][4].split("::")[0];
					String MethodName = testStart[a][4].split("::")[1];
					log.info(DeviceName+"  MethodName:"+MethodName.split("\\(")[0]);
					log.info(DeviceName+"  PackageName:com.xiaoM.PageObject");
					log.info(DeviceName+"  ClassName:"+ClassName);
					log.info(DeviceName+"  执行步骤：" + testStart[a][1]);
					log.info(DeviceName+"  步骤名称：" + testStart[a][2]);
					String Steps = testStart[a][1]+":"+testStart[a][2];
					ExecuteScript Method = new ExecuteScript(driver);
					Object result = Method.runScript(ClassName,MethodName,map,Steps,DeviceName+"-"+CaseName);
					map.put(testStart[a][1], result);
					if(result.equals(false)){
						throw new Exception("返回值为：false");
					}
					log.info(DeviceName+"  返回值:" + result);	
				}	
			}
			TestListener.runSuccessMessageList.add(DeviceName+"-"+CaseName);
			log.info(DeviceName+" --------------------------------------");
			log.info(DeviceName+"  测试用例:"+ CaseName +"---End");
			long EndTime = System.currentTimeMillis();
			TestListener.RuntimeStart.put(DeviceName+"-"+CaseName,StartTime);
			TestListener.RuntimeEnd.put(DeviceName+"-"+CaseName,EndTime);
			if(StartRM){
				RM.stopMonitoring(DeviceName);
			}
			driver.quit();
		} catch (Exception e) {
			TestListener.runFailMessageList.add(DeviceName+"-"+CaseName);
			AppiumScreenShot screenShot = new AppiumScreenShot(driver);
			screenShot.setscreenName(DeviceName,CaseName);
			screenShot.takeScreenshot();
			driver.quit();
			log.info(DeviceName+" --------------------------------------");
			log.info(DeviceName+"  测试用例:"+ CaseName +"---End");
			long EndTime = System.currentTimeMillis();
			TestListener.RuntimeStart.put(DeviceName+"-"+CaseName,StartTime);
			TestListener.RuntimeEnd.put(DeviceName+"-"+CaseName,EndTime);		
			throw e;
		}
	}
}
