package com.xiaoM.Utils;


import com.xiaoM.BeginScript.BeginAppScript;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseConfig {
    final private static String SheetName = "配置";

    public static Map<String, String[]> getDataBaseConfigXlsx(XSSFWorkbook workbook) throws IOException {
        Map<String, String[]> DataBaseConfig = new HashMap<>();
        XSSFSheet sheet = workbook.getSheet(SheetName);//读取sheet
        if (sheet != null) {
            int startRowNum = IOMananger.locateTextFromExcel(workbook,SheetName, "数据库配置") + 2;//获取开始行
            int lastRowNum = sheet.getLastRowNum() + 1;//获取总行数
            String[] data;
            for (int rowNum = startRowNum; rowNum < lastRowNum; rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);
                if (row == null) {
                    break;
                }
                data = new String[6];
                for (int j = 0; j < data.length; j++) {
                    if (row.getCell(j) != null) {
                        row.getCell(j).setCellType(CellType.STRING);
                        data[j] = row.getCell(j).getStringCellValue().trim();
                    }
                }
                DataBaseConfig.put(data[1], data);
            }
        } else {
            throw new IOException("该 Sheet [ " + SheetName + " ] 不存在");
        }
        return DataBaseConfig;
    }

    public static Map<String, String[]> getReportConfigXlsx(XSSFWorkbook workbook) throws IOException {
        Map<String, String[]> ReportConfig = new HashMap<String, String[]>();
        XSSFSheet sheet = BeginAppScript.workbook.getSheet(SheetName);//读取sheet
        if (sheet != null) {
            int startRowNum = IOMananger.locateTextFromExcel(workbook,SheetName, "在线测试报告配置") + 2;//获取开始行
            int lastRowNum = sheet.getLastRowNum() + 1;//获取总行数
            String[] data;
            for (int rowNum = startRowNum; rowNum < lastRowNum; rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);
                if (row == null) {
                    break;
                }
                data = new String[4];
                for (int j = 0; j < data.length; j++) {
                    if (row.getCell(j) != null) {
                        row.getCell(j).setCellType(CellType.STRING);
                        data[j] = row.getCell(j).getStringCellValue().trim();
                    }
                }
                ReportConfig.put(data[1], data);
            }
        } else {
            throw new IOException("该 Sheet [ " + SheetName + " ] 不存在");
        }
        return ReportConfig;
    }

    public static Map<String, String[]> getOcrConfigXlsx(XSSFWorkbook workbook) throws IOException {
        Map<String, String[]> OcrConfig = new HashMap<String, String[]>();
        XSSFSheet sheet = workbook.getSheet(SheetName);//读取sheet
        if (sheet != null) {
            int startRowNum = IOMananger.locateTextFromExcel(workbook,SheetName, "文字识别配置") + 2;//获取开始行
            int lastRowNum = sheet.getLastRowNum() + 1;//获取总行数
            String[] data;
            for (int rowNum = startRowNum; rowNum < lastRowNum; rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);
                if (row == null) {
                    break;
                }
                data = new String[5];
                for (int j = 0; j < data.length; j++) {
                    if (row.getCell(j) != null) {
                        row.getCell(j).setCellType(CellType.STRING);
                        data[j] = row.getCell(j).getStringCellValue().trim();
                    }
                }
                OcrConfig.put(data[1], data);
            }
        } else {
            throw new IOException("该 Sheet [ " + SheetName + " ] 不存在");
        }
        return OcrConfig;
    }
}