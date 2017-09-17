package com.xiaoM.ReportUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.TestListenerAdapter;

import com.xiaoM.Utils.IOMananger;
import com.xiaoM.Utils.Log;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class TestListener  extends TestListenerAdapter{
	Log log= new Log(this.getClass());
	public static String[][] RunCase;//执行测试case
	public static Map<String, String> screenMessageList = new HashMap<String, String>();
	public static Map<String, String> failMessageList=new HashMap<String, String>();
	public static Map<String, Long> RuntimeStart=new HashMap<String, Long>();
	public static Map<String, Long> RuntimeEnd=new HashMap<String, Long>();
	public static List<String> runSuccessMessageList=new ArrayList<String>();
	public static List<String> runFailMessageList=new ArrayList<String>();
	public static List<String> RunDevices=new ArrayList<String>();
	public static String DeviceType;//设备类型
	public static String ResetApp;//是否重置应用
	public static String AppName;
	public static String bundleId;
	public static String OS;
	public static String ProjectPath;//工程路径
	public static String TestCase;//测试用例所在的表
	public static String CasePath;
	public static long StartTime;
	public static long EndTime;
	//配置初始化
	static{	
		//读取配置文件
		Properties pp = new Properties();
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream("config.properties"),"UTF-8");
			pp.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//获取操作系统
		String os = System.getProperty("os.name");
		if(os.contains("Mac")){	
			OS = "MAC";
			String appiumPath = pp.getProperty("APPIUM_JS_PATH");
			System.setProperty(AppiumServiceBuilder.APPIUM_PATH , appiumPath);
		}else if(os.contains("Windows")){
			OS = "WINDOWS";
		}
		ProjectPath = new File(System.getProperty("user.dir")).getPath();// 工程根目录
		TestCase = pp.getProperty("TESTCASE");
		CasePath = ProjectPath +"/testCase/"+ TestCase+".xlsx";
		DeviceType = pp.getProperty("DEVICE_TYPE");
		ResetApp = pp.getProperty("NORESET_APP");
		if(DeviceType.equals("Android")){
			AppName = pp.getProperty("APP_NAME");
		}else{
			bundleId = pp.getProperty("BUNDIEID");
		}
		//获取测试执行用例
		try {
			RunCase = IOMananger.runTime("TestCases", CasePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String logPath = ProjectPath+"/test-output/log/RunLog.log";
		File path = new File(logPath);
		if(path.exists()){
			path.delete();//删除日志文件
		}
	}
}
