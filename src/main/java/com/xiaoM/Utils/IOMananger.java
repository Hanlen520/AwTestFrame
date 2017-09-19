package com.xiaoM.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xiaoM.ReportUtils.TestListener;

public class IOMananger {
	//设置日期格式
	static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	//获取当前日期
	static String date=dateFormat.format(new Date()).toString();
	/**
	 * 读取excel
	 * @param sheetName
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String[][] readExcelDataXlsx(String sheetName,String path) throws IOException {
		InputStream is = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(is);//读取Excel
		XSSFSheet sheet = workbook.getSheet(sheetName);//读取sheet
		if(sheet!=null){
			int lastrowNum = sheet.getLastRowNum()+1;//获取总行数
			int collNum = sheet.getRow(0).getLastCellNum();//获取列数	 
			String[][] user = new String[lastrowNum][collNum];
			for(int rowNum=0;rowNum<lastrowNum;rowNum++){
				XSSFRow row = sheet.getRow(rowNum);
				for(int j=0;j<collNum;j++){
					if(row.getCell(j)!=null){
						row.getCell(j).setCellType(CellType.STRING);	
						user[rowNum][j] = row.getCell(j).getStringCellValue();
					}
				}	
			}
			workbook.close();
			return user;
		}else{
			System.out.println(sheet+"is null!");
			workbook.close();
			return null;
		}	
	}
	/**
	 * 获取执行测试用例
	 * @param sheetName
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String[][] runTime(String sheetName,String path) throws IOException{
		String[][] Date =  readExcelDataXlsx(sheetName,path);
		List<String> BrowserName = new LinkedList<String>();
		List<String> caseName = new LinkedList<String>();
		for(int i=1;i<Date.length;i++){
			if(Date[i][0].equals("YES")){
				BrowserName.add(Date[i][2]);
				caseName.add(Date[i][4]);	
			}
		}
		String[][] runTime  = new String[caseName.size()][2];
		for(int k =0;k<caseName.size();k++){
			runTime[k][0]=BrowserName.get(k);
			runTime[k][1]=caseName.get(k);
		}
		return runTime;
	}
	
	public static List<String> getRunDevices(){
		List<String> list = new ArrayList<String>();
		List<String> devicelist = new ArrayList<String>();
		XSSFWorkbook workbook = null ;
		String devicesPath ;
		if(TestListener.DeviceType.equals("Android")){
			devicesPath = TestListener.ProjectPath + "/devices/AndroidDevices.xlsx";
		}else{
			devicesPath = TestListener.ProjectPath + "/devices/iOSDevices.xlsx";
		}
		try {
			InputStream is = new FileInputStream(devicesPath);
			workbook = new XSSFWorkbook(is);//读取Excel
			int sheetNum = workbook.getNumberOfSheets();
			for(int i=0;i<sheetNum;i++){
				list.add(workbook.getSheetName(i));
			}
			String devicesList = TestListener.RunDevices.toString();
			for(int i=0;i<list.size();i++){
				if(devicesList.contains(list.get(i))){
					devicelist.add(list.get(i));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return devicelist;
	}
	/**
	 * 功能：Java读取txt文件的内容
	 * 步骤：1：先获得文件句柄
	 * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流
	 * 4：一行一行的输出。readline()。
	 * 备注：需要考虑的是异常情况
	 * @param filePath
	 * @throws FileNotFoundException 
	 */
	public static List<String> readTxtFile(String filePath) throws FileNotFoundException{
		List<String> txt = new ArrayList<String>();
		Scanner in = new Scanner(new File(filePath));  
        while(in.hasNext()){ 
        	String str=in.nextLine(); 
        	if(!str.isEmpty()){
        		txt.add(str.toString());
        	}
        }
        in.close();
		return txt;
	}
	
	/**
	 * 写入数据到txt文本中
	 * @param fileDir 路径
	 * @param FileName	文件名
	 * @param conent
	 */
	public static void saveToFile(String Path, String conent) {
		BufferedWriter bw = null;
		try {
			/**
			 * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
			 * 清空重新写入：把第二个参数设为false
			 */
			FileOutputStream fo = new FileOutputStream(Path, true);
			OutputStreamWriter ow = new OutputStreamWriter(fo);
			bw = new BufferedWriter(ow);
			bw.append(conent);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (Exception e) {
			System.out.println("写入数据失败！！！");
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 处理日志
	 * @param workSpase
	 * @param data
	 * @throws IOException
	 */
	public static void DealwithRunLog(String DeviceName) {	
		String logPath = TestListener.ProjectPath+"/test-output/log/";
		List<String> DriversLog = null;
		try {
			DriversLog = IOMananger.readTxtFile(logPath+"RunLog.log");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		logPath = logPath + DeviceName;
		File destDir = new File(logPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		String logDriverPath  = logPath +"/"+ DeviceName+"-"+date+".log";	
		for(String logDriver:DriversLog){
			if(logDriver.contains(DeviceName)){
				IOMananger.saveToFile(logDriverPath, logDriver);
			}
		}
	}
	public static void main(String[]args) throws IOException{
		TestListener.RunDevices.add("三星i9192");
		TestListener.RunDevices.add("模拟器2");
		TestListener.RunDevices.add("模拟器");
		List<String> runDevices = IOMananger.getRunDevices();
		
		for(int i=0;i<runDevices.size();i++){
			System.out.println(runDevices.get(i));
			IOMananger.DealwithRunLog(runDevices.get(i));
		}
	}
}
