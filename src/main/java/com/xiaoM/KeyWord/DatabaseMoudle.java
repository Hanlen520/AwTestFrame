package com.xiaoM.KeyWord;

import com.xiaoM.BeginScript.BeginAppScript;
import com.xiaoM.Driver.AppiumXMDriver;
import com.xiaoM.Utils.Location;
import com.xiaoM.Utils.Log;
import com.xiaoM.Utils.SqlHelper;

import java.sql.ResultSet;
import java.util.Map;

public class DatabaseMoudle {
    private Log log = new Log(this.getClass());
    private String TestCategory;
    private Map<String, Object> returnMap;

    public DatabaseMoudle( String TestCategory, Map<String, Object> returnMap) {
        this.TestCategory = TestCategory;
        this.returnMap = returnMap;
    }

    public boolean dataBaseUpdata(Location location){
        try {
            Map<String, String[]> dataBaseConfig = BeginAppScript.DataBaseConfig;
            String[] dataBase = dataBaseConfig.get(location.getParameter());
            String dataBaseType = dataBase[2].toLowerCase();
            String dataBaseDriver;
            String url = dataBase[3];
            String userName = dataBase[4];
            String passWord = dataBase[5];
            switch (dataBaseType) {
                case "mysql":
                    url = url + "?useSSL=false&serverTimezone=UTC";
                    dataBaseDriver = "com.mysql.cj.jdbc.Driver";
                    break;
                default:
                    log.error(TestCategory + ": 不支持该数据库操作[ " + dataBaseType + " ]");
                    return false;
            }
            SqlHelper sqlHelper = new SqlHelper(dataBaseDriver, url, userName, passWord);
            String sql = location.getValue();
            sqlHelper.executeUpdate(sql);
            log.info(TestCategory + ": 执行数据库操作成功 [ " + location.getValue() + " ]");
        } catch (Exception e) {
            log.error(TestCategory + ": 执行数据库操作失败 [ " + location.getValue() + " ]");
            return false;
        }
        return true;
    }

    public Object dataBaseQuery(Location location){
        try {
            Map<String, String[]> dataBaseConfig = BeginAppScript.DataBaseConfig;
            String[] dataBase = dataBaseConfig.get(location.getParameter());
            String dataBaseType = dataBase[2].toLowerCase();
            String dataBaseDriver;
            String url = dataBase[3];
            String userName = dataBase[4];
            String passWord = dataBase[5];
            switch (dataBaseType) {
                case "mysql":
                    url = url + "?useSSL=false&serverTimezone=UTC";
                    dataBaseDriver = "com.mysql.cj.jdbc.Driver";
                    break;
                default:
                    log.error(TestCategory + ": 不支持该数据库操作[ " + dataBaseType + " ]");
                    return false;
            }
            SqlHelper sqlHelper = new SqlHelper(dataBaseDriver, url, userName, passWord);
            String sql = location.getValue();
            ResultSet result = sqlHelper.executeQuery(sql);
            if (result.first()) {
                String getresult = result.getString(1);
                log.info(TestCategory + ": 执行数据库操作成功 [ " + location.getValue() + " ]");
                return getresult;
            }
        } catch (Exception e) {
            log.error(TestCategory + ": 执行数据库操作失败 [ " + location.getValue() + " ]");
            return false;
        }
        return true;
    }
}
