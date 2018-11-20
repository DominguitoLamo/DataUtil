package com.jdbc;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

/*@author:ChonLam
**Created on 2018/11/20
 */

public class SimpleConnector {
    private Properties properties = new Properties();
    private Connection connection = null;
    private String ProjectPath = this.getClass().getClassLoader().getResource("").getPath();
    public SimpleConnector(){
        HashMap<String,String> JdbcConfig = getJdbcConfig();
        try {
            Class.forName(JdbcConfig.get("driver"));
            this.connection = DriverManager.getConnection(JdbcConfig.get("host"),JdbcConfig.get("username"),JdbcConfig.get("password"));
        }catch (ClassNotFoundException cx){
            System.out.println("Cannot find the mysql connector.");
        }catch (SQLException ex){
            System.out.println("Cannot enter mysql. Please ensure the config is correct");
            ex.printStackTrace();
        }
    }

    private HashMap<String,String> getJdbcConfig(){
        HashMap<String,String> JdbcConfig = new HashMap<>();
        try(InputStream ProperIn = new BufferedInputStream(new FileInputStream(ProjectPath + "/jdbcconfig.properties"))){
            properties.load(ProperIn);
            String driver = properties.getProperty("driver");
            String host = properties.getProperty("host");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            JdbcConfig.put("driver",driver);
            JdbcConfig.put("host",host);
            JdbcConfig.put("username",username);
            JdbcConfig.put("password",password);
            return JdbcConfig;
        }catch(IOException ex){
            System.out.println("Cannot find the file. Please ensure the path.");
        }
        return JdbcConfig;
    }

    public void close(){
        try {
            connection.close();
        }catch (SQLException ex){
            System.out.println("Cannot Close. Please check the connection of Sql");
        }
    }

    public Connection getConnection(){
        return this.connection;
    }

    public static void main(String[] args){
        SimpleConnector simpleConnector = new SimpleConnector();
        Connection connection = simpleConnector.getConnection();
        System.out.println(connection);
    }
}
