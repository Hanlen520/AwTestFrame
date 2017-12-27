package com.xiaoM.Utils;

import java.util.HashMap;
import java.util.Map;

import com.xiaoM.Android.ResourceMonitoring;
import com.xiaoM.ReportUtils.TestListener;

import io.appium.java_client.AppiumDriver;

public class Run {
	private Log log= new Log(this.getClass());
	private BaseDriver base;
	public void  runCase(String ID,String DeviceName,String Type ,String CaseName) throws Exception{
		AppiumDriver driver;
		String TestCategory = TestListener.Category.get(ID);
		Map<String, Object> map = new HashMap<String, Object>();
		switch (Type.toLowerCase()){
			case "app":
				base = new BaseDriver();
				driver = base.setUpApp(DeviceName,CaseName,TestCategory);
				break;
			case  "wap":
				base = new BaseDriver();
				driver = base.setUpWap(DeviceName,CaseName,TestCategory);
				break;
			default:
				UseDevice.addDevice(DeviceName);
				log.error( "请在 "+TestListener.TestCase+".xlsx 中选择正确的测试类型：APP/WAP");
				throw new Exception( "请在 "+TestListener.TestCase+".xlsx 中选择正确的测试类型：APP/WAP");
		}
		String[][] testStart = IOMananger.readExcelDataXlsx(CaseName,TestListener.CasePath);
		if(testStart!=null){
			ResourceMonitoring RM = null;
			boolean StartRM = false;
			if(TestListener.Resource_Monitoring.toLowerCase().equals("true") && Type.toLowerCase().equals("app")&&TestListener.DeviceType.toLowerCase().equals("android")){
				RM = new ResourceMonitoring();
				RM.startMonitoring(DeviceName,TestCategory);
				StartRM = true;
			}else if(TestListener.Resource_Monitoring.toLowerCase().equals("true") && !Type.toLowerCase().equals("app")){
				throw new Exception("资源监控只适用于 Android 平台的APP");
			}else if(TestListener.Resource_Monitoring.toLowerCase().equals("true") &&!TestListener.DeviceType.toLowerCase().equals("android")){
				throw new Exception("资源监控只适用于 Android 平台的APP");
			}
			long StartTime = System.currentTimeMillis();
			try {
				for(int a=1;a<testStart.length;a++){
					if(testStart[a][0].equals("YES")){
						String ClassName = testStart[a][4].split("::")[0];
						String MethodName = testStart[a][4].split("::")[1];
						log.info(TestCategory +" MethodName:"+MethodName.split("\\(")[0]);
						log.info(TestCategory +" PackageName:com.xiaoM.PageObject");
						log.info(TestCategory +" ClassName:"+ ClassName);
						log.info(TestCategory +" 执行步骤：" + testStart[a][1]);
						log.info(TestCategory +" 步骤名称：" + testStart[a][2]);
						String Steps = testStart[a][1]+":"+testStart[a][2];
						ExecuteScript Method = new ExecuteScript(driver);
						Object result = Method.runScript(ClassName,MethodName,map,Steps,TestCategory);
						map.put(testStart[a][1], result);
						if(result.equals(false)){
							log.error(TestCategory +" 返回值:" + result);
							throw new Exception("返回值为：false");
						}
						log.info(TestCategory +" 返回值:" + result);

					}
				}
				log.info(TestCategory +" -----------------------------------");
				log.info(TestCategory +" 测试用例:"+ CaseName +"---End");
				long EndTime = System.currentTimeMillis();
				TestListener.RuntimeStart.put(TestCategory,StartTime);
				TestListener.RuntimeEnd.put(TestCategory,EndTime);
				if(StartRM){
					RM.stopMonitoring(DeviceName,TestCategory);
				}
			} catch (Exception e) {
				ScreenShot screenShot = new ScreenShot(driver);
				screenShot.setscreenName(TestCategory);
				screenShot.takeScreenshot();
				log.info(TestCategory +" -----------------------------------");
				log.info(TestCategory +" 测试用例:"+ CaseName +"---End");
				long EndTime = System.currentTimeMillis();
				TestListener.RuntimeStart.put(TestCategory,StartTime);
				TestListener.RuntimeEnd.put(TestCategory,EndTime);
				throw e;
			}finally {
				driver.quit();
				UseDevice.addDevice(DeviceName);
				if(base.getAppiumServer().service!=null|| base.getAppiumServer().service.isRunning()){
					base.getAppiumServer().service.stop();
				}
			}
		}else{
			log.error("该测试用例:"+ CaseName +"在 "+TestListener.TestCase+".xlsx 中没有对应的命名的 sheet");
			log.error(TestCategory +" 测试用例:"+ CaseName +"---End");
			log.error(TestCategory +" -----------------------------------");
			throw new Exception();
		}

	}
}
