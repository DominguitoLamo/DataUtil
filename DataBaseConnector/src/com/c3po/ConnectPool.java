package com.c3po;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
public class ConnectPool {
    private static DataSource dataSource=null;
    static{
        dataSource=new ComboPooledDataSource("mysql");
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection conn=null;
        try {
            conn=dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * @param conn
     */
    public static void closeConn(Connection conn){
        try {
            if(conn!=null && conn.isClosed()){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        System.out.println(connection);
    }

}
