package com.xiaoM.Utils;

import java.sql.*;

public class SqlHelper {
    // 定义要使用的变量
    private  Connection conn = null;
    private  String url = "";
    private  String userName = "";
    private  String password = "";

    SqlHelper(String driver, String url, String userName, String password  ) throws ClassNotFoundException {
        this.url = url;
        this.userName = userName;
        this.password = password;
        Class.forName(driver);
    }

    /**
     * 得到连接
     */
    private Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 更新
     * @param sql
     */
    public  void executeUpdate(String sql){
    	conn = getConnection();
    	try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    /**
     * 查询
     * @param sql
     * @return
     */
    public  ResultSet executeQuery(String sql){
    	ResultSet rs = null;
    	conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
		
    }

    /**
     * 关闭连接
     */
    public static void close(ResultSet rs, Statement ps, Connection conn) {
        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if (ps != null)
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}