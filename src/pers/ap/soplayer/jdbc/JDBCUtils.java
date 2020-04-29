package pers.ap.soplayer.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    private static String driver;
    private static String url;

    static{
        InputStream in=JDBCUtils.class.getClassLoader().getResourceAsStream("db.properties");
//        ResourceBundle in = ResourceBundle.getBundle("db");
        Properties properties=new Properties();
        try {
            properties.load(in);
             driver=properties.getProperty("driver");
             url=properties.getProperty("url");
            Class.forName(driver);
            System.out.println("配置文件读成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void close(Connection conn, Statement statement, ResultSet resultSet){
        try {
           if(resultSet!=null){
               resultSet.close();
               resultSet=null;
           }
            if(statement!=null){
                statement.close();
                statement=null;
            }
            if(conn!=null){
                conn.close();
                conn=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
