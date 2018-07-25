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
import java.util.*;

import com.aventstack.extentreports.ExtentTest;
import com.xiaoM.BeginScript.BeginAppScript;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class IOMananger {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static String date = dateFormat.format(new Date());

    /**
     * 读取excel
     */
    public static String[][] readExcelDataXlsx(XSSFWorkbook workbook, String sheetName) {
        XSSFSheet sheet = workbook.getSheet(sheetName);//读取sheet
        if (sheet != null) {
            int lastrowNum = sheet.getLastRowNum() + 1;//获取总行数
            int collNum = sheet.getRow(0).getLastCellNum();//获取列数
            String[][] user = new String[lastrowNum][collNum];
            for (int rowNum = 0; rowNum < lastrowNum; rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);
                for (int j = 0; j < collNum; j++) {
                    if (row.getCell(j) != null) {
                        row.getCell(j).setCellType(CellType.STRING);
                        user[rowNum][j] = row.getCell(j).getStringCellValue();
                    }
                }
            }
            return user;
        } else {
            return null;
        }
    }

    /**
     * 获取指定单元格所在的行数
     *
     * @param sheetName   Sheet
     * @param cellContent 文本
     */
    static int locateTextFromExcel(XSSFWorkbook workbook, String sheetName, String cellContent) throws IOException {
        int i = 0;
        XSSFSheet sheet = workbook.getSheet(sheetName);//读取sheet
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
    public static String[][] runTime(XSSFWorkbook workbook, String sheetname) {
        String[][] Date = readExcelDataXlsx(workbook, sheetname);
        List<String> ID = new LinkedList<>();
        List<String> Module = new LinkedList<>();
        List<String> CaseName = new LinkedList<>();
        List<String> Remark = new LinkedList<>();
        for (int i = 1; i < Date.length; i++) {
            if (Date[i][0].toLowerCase().equals("y")) {
                ID.add(Date[i][1]);
                Module.add(Date[i][2]);
                CaseName.add(Date[i][3]);
                Remark.add(Date[i][4]);
            }
        }
        String[][] runTime = new String[CaseName.size()][4];
        for (int k = 0; k < CaseName.size(); k++) {
            runTime[k][0] = ID.get(k);
            runTime[k][1] = Module.get(k);
            runTime[k][2] = CaseName.get(k);
            runTime[k][3] = Remark.get(k);
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
     *
     * @param filePath
     * @throws FileNotFoundException
     */
    public static List<String> readTxtFile(String filePath) {
        List<String> txt = null;
        Scanner in = null;
        try {
            txt = new ArrayList<>();
            in = new Scanner(new File(filePath));
            while (in.hasNext()) {
                String str = in.nextLine();
                if (!str.isEmpty()) {
                    txt.add(str);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return txt;
    }

    /**
     * 写入数据到txt文本中
     *
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
        String logPath = BeginAppScript.ProjectPath + "/test-output/log/runlogs/" + date;
        List<String> BrowserLog = IOMananger.readTxtFile(BeginAppScript.ProjectPath + "/test-output/log/runLog.log");
        File destDir = new File(logPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd(HH.mm.ss)");
        String date = dateFormat.format(System.currentTimeMillis());
        String logDriverPath = logPath + "/" + TestCategory + "_" + date + ".log";
        StringBuilder sb = new StringBuilder();
        for (String logDriver : BrowserLog) {
            if (logDriver.contains(TestCategory)) {
                sb.append(logDriver + "\r\n");
                IOMananger.saveToFile(logDriverPath, logDriver);
            }
        }
        BeginAppScript.logList.put(TestCategory, "<spen>运行日志：</spen></br><pre>" + sb.toString() + "</pre>");
    }

    /**
     * 判断电脑本地是否存在某文件，存在则删除
     */
    public static void deleteFile(String[] Paths) {
        for (String path : Paths) {
            try {
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                System.out.println("删除文件失败，文件路径：" + path);
            }
        }
    }

    /**
     * 写入数据到txt文本中
     *
     * @param fileDir  路径
     * @param FileName 文件名
     * @param conent
     */
    public static void saveToFile(String fileDir, String FileName, String conent) {
        File destDir = new File(fileDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        String Path = fileDir + "/" + FileName + ".txt";
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
            ExtentTest extentTest = BeginAppScript.extent.createTest("启动测试失败");
            extentTest.fail(e.getMessage());
            BeginAppScript.extent.flush();
            System.exit(0);
        }
        return workbook;
    }

    public static XSSFWorkbook getDeviceExcel() {
        XSSFWorkbook workbook = null;
        String devicesPath;
        try {
            if (BeginAppScript.DeviceType.toLowerCase().equals("android")) {
                devicesPath = BeginAppScript.ProjectPath + "/devices/AndroidDevices.xlsx";
            } else {
                devicesPath = BeginAppScript.ProjectPath + "/devices/iOSDevices.xlsx";
            }
            InputStream is = new FileInputStream(devicesPath);
            workbook = new XSSFWorkbook(is);
            workbook.close();
        } catch (Exception e) {
            ExtentTest extentTest = BeginAppScript.extent.createTest("启动测试失败");
            extentTest.fail(e.getMessage());
            BeginAppScript.extent.flush();
            System.exit(0);
        }
        return workbook;
    }

    public static void deleteDirectory(File file) {
        if (file.exists()) {
            if (file.isFile()) {// 表示该文件不是文件夹
                file.delete();
            } else {
                // 首先得到当前的路径
                String[] childFilePaths = file.list();
                for (String childFilePath : childFilePaths) {
                    File childFile = new File(file.getAbsolutePath() + "/" + childFilePath);
                    deleteDirectory(childFile);
                }
                file.delete();
            }
        }
    }

    public static Map<String, String> getCommonParam(XSSFWorkbook workbook) {
        Map<String, String> commonParam = new HashMap<>();
        String[][] Params = readExcelDataXlsx(workbook, "公共参数");
        if (Params.length > 1) {
            for (int i = 1; i < Params.length; i++) {
                commonParam.put(Params[i][1].trim(), Params[i][2].trim());
            }
        }
        return commonParam;
    }

    public static void main(String[] args) {
        XSSFWorkbook workbook = getCaseExcel("C:\\Users\\xiaoM\\Desktop\\AwTestFrame\\testCase\\163WebMail\\main.xlsx");
        Map<String, String> commonParam = getCommonParam(workbook);
        for (String in : commonParam.keySet()) {
            String str = commonParam.get(in);//得到每个key多对用value的值
            System.out.println(in + "     " + str);
        }
    }
}
