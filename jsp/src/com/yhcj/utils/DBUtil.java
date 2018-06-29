package com.yhcj.utils;
import static com.yhcj.utils.LoadDBconfig.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 鏁版嵁搴撴搷绫�
 * */
public class DBUtil {
	 /**
     * 杩炴帴鏁版嵁搴�
     * @return
     */
    public static Connection getConn(){
        Connection conn=null;
        try {
            //鍔犺浇椹卞姩
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://127.0.0.1:3306/project_db";
            String user="root";
            String password="123456";
            conn=DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 鍏抽棴杩炴帴
     * @param rs
     * @param stat
     * @param conn
     */
    public static void closeConn(ResultSet rs,Statement stat,Connection conn){
        try {
            // 鍏抽棴 ctrl+shift+F format
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeConn(Statement stat,Connection conn){
        try {
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
