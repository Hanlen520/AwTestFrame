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

import com.aventstack.extentreports.ExtentTest;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xiaoM.ReportUtils.TestListener;

public class IOMananger {
	//设置日期格式
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//获取当前日期
	private static String date = dateFormat.format(new Date());
	/**
	 * 读取excel
	 */
	public static String[][] readExcelDataXlsx(XSSFWorkbook workbook,String sheetName){
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
			return user;
		}else{
			return null;
		}
	}

	/**
	 * 获取指定单元格所在的行数
	 * @param sheetName Sheet
	 * @param cellContent 文本
	 */
	static int locateTextFromExcel(String sheetName, String cellContent) throws IOException {
		int i = 0;
		XSSFSheet sheet = TestListener.workbook.getSheet(sheetName);//读取sheet
		if (sheet != null) {
			int rowNum = sheet.getLastRowNum();
			for (int j = 0; j <= rowNum; j++) {
				XSSFRow row = sheet.getRow(j);
				if (row != null) {
					if (row.getCell(0) != null) {
						row.getCell(0).setCellType(CellType.STRING);
						if (row.getCell(0).getRichStringCellValue().getString().trim().equals(cellContent)) {
							return i;
						}
					}
				}
				i++;
			}
		} else {
			throw new IOException("该 Sheet [ " + sheetName + " ] 不存在");
		}
		return i;
	}


	/**
	 * 获取执行测试用例
	 */
	public static String[][] runTime(String sheetname){
		String[][] Date =  readExcelDataXlsx(TestListener.workbook,sheetname);
		List<String> ID = new LinkedList<>();
		List<String> Type = new LinkedList<>();
		List<String> Description = new LinkedList<>();
		List<String> CaseName = new LinkedList<>();
		for(int i=1;i<Date.length;i++){
			if(Date[i][0].equals("YES")){
				ID.add(Date[i][1]);
				Type.add(Date[i][2]);
				Description.add(Date[i][3]);
				CaseName.add(Date[i][4]);
			}
		}
		String[][] runTime  = new String[CaseName.size()][4];
		for(int k =0;k<CaseName.size();k++){
			runTime[k][0]=ID.get(k);
			runTime[k][1]=Type.get(k);
			runTime[k][2]=Description.get(k);
			runTime[k][3]=CaseName.get(k);
		}
		return runTime;
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
	public static List<String> readTxtFile(String filePath) {
		List<String> txt = null;
		Scanner in = null;
		try {
			txt = new ArrayList<>();
			in = new Scanner(new File(filePath));
			while(in.hasNext()){
				String str=in.nextLine();
				if(!str.isEmpty()){
					txt.add(str);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			if (in!=null){
				in.close();
			}
		}
		return txt;
	}
	
	/**
	 * 写入数据到txt文本中
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
	 */
	public static void DealwithRunLog(String TestCategory) {
		String logPath = TestListener.ProjectPath+"/test-output/log/runlogs/"+date;
		List<String> BrowserLog = IOMananger.readTxtFile(TestListener.ProjectPath+"/test-output/log/runLog.log");
		File destDir = new File(logPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd(HH.mm.ss)");
		String date = dateFormat.format(System.currentTimeMillis());
		String logDriverPath  = logPath +"/"+ TestCategory+"_"+date+".log";
		StringBuilder sb = new StringBuilder();
		for(String logDriver:BrowserLog){
			if(logDriver.contains(TestCategory)){
				sb.append(logDriver +"\r\n");
				IOMananger.saveToFile(logDriverPath, logDriver);
			}
		}
		TestListener.logList.put(TestCategory,"<spen>运行日志：</spen></br><pre>"+sb.toString()+"</pre>");
	}
	
	/**
	 *判断电脑本地是否存在某文件，存在则删除
	 */
	public static void deleteFile(String[] Paths){
		for(String path:Paths){
			try {
				File file = new File(path);
				if(file.exists()){
					file.delete();
				}
			} catch (Exception e) {
				System.out.println("删除文件失败，文件路径："+ path);
			}
		}	
	}
	/**
	 * 写入数据到txt文本中
	 * @param fileDir 路径
	 * @param FileName	文件名
	 * @param conent
	 */
	public static void saveToFile(String fileDir,String FileName, String conent) {
		File destDir = new File(fileDir);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		String Path = fileDir +"/"+ FileName+".txt";
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
	public static XSSFWorkbook getCaseExcel(String path) {
		XSSFWorkbook workbook = null;//读取Excel
		try {
			InputStream is = new FileInputStream(path);
			workbook = new XSSFWorkbook(is);
			workbook.close();
		} catch (Exception e) {
			ExtentTest extentTest = TestListener.extent.createTest("启动测试失败");
			extentTest.fail(e.getMessage());
			TestListener.extent.flush();
			System.exit(0);
		}
		return workbook;
	}

	public static XSSFWorkbook getDeviceExcel() {
		XSSFWorkbook workbook = null;
		String devicesPath;
		try {
			if(TestListener.DeviceType.toLowerCase().equals("android")){
				devicesPath = TestListener.ProjectPath + "/devices/AndroidDevices.xlsx";
			}else{
				devicesPath = TestListener.ProjectPath + "/devices/iOSDevices.xlsx";
			}
			InputStream is = new FileInputStream(devicesPath);
			workbook =  new XSSFWorkbook(is);
			workbook.close();
		} catch (Exception e) {
			ExtentTest extentTest = TestListener.extent.createTest("启动测试失败");
			extentTest.fail(e.getMessage());
			TestListener.extent.flush();
			System.exit(0);
		}
		return workbook;
	}

}
