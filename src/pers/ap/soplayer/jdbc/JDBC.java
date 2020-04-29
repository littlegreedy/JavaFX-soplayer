package pers.ap.soplayer.jdbc;

//import team.suns.dao.UserDao_Imp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JDBC {
    /**
     * 使用JDBC连接University数据库
     */
//    public static Connection getConnection() throws Exception{
//        Connection conn = null; // 创建一个connection
//
//////            加载驱动
////            Class.forName("com.mysql.cj.jdbc.Driver");
//////            连接数据库，连接对象
////            String url = "jdbc:mysql://localhost:3308/data?"
////                    + "user=YaboSun&password=root"
////                    + "&useUnicode=true&characterEncoding=UTF8"
////                    + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
////            String USER ="root";
////            String PASSWORD ="1000";
//        conn = JDBCUtils.getConnection();
//
//        return conn;
//    }


    public static void main(String[] args) throws Exception{
        String sql; // 用于后面对应不同的sql语句
        Connection connection=null;
        connection= JDBCUtils.getConnection(); // 获取一个connection

        if(connection==null){
            System.out.println("getConnection()返回值为null");
            return;
        }
        System.out.println("数据库连接成功");

        /**
         * 发车
         * 创建一个statement
         * Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除
         * executeQuery可以实现查询
         */
        try {
            Statement statement = connection.createStatement();
//            search(statement);
//            testConnect(statement);


            connection.close();

            System.out.println("数据库连接成功断开");
        } catch (SQLException e) {
            e.getStackTrace();
            System.out.println("SQL 执行错误");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    static void testConnect(Statement statement){
        String sql; // 用于后面对应不同的sql语句

        sql = "create table Test(Name VARCHAR(20), url VARCHAR(2083))";
        int updateResult ; // 用于获取是否创建成功 如果不成功结果为-1
        try {
            updateResult = statement.executeUpdate(sql);
            if (updateResult != -1) {
                sql = "INSERT INTO Test VALUES ('我的', 'jshwshwshbhywswahmhwh')";
                updateResult = statement.executeUpdate(sql);
                sql = "INSERT INTO Test VALUES ('hh', 'sahsbabhsj')";
                updateResult = statement.executeUpdate(sql);
                sql = "SELECT * FROM Test";
                ResultSet resultSet = statement.executeQuery(sql); // executeQuery会返回结果的集合，否则返回空值
                while (resultSet.next()) {
                    String Tno = resultSet.getString("Tno");
                    String Tname = resultSet.getString("Tname");
                    int Tage = resultSet.getInt("Tage");
                    System.out.println(Tno + " " + Tname + " " + Tage);
                }
            }
        } catch (SQLException e) {
            System.out.println("Test表已经存在");
            e.printStackTrace();
        }
        sql = "DROP TABLE Test";
        try {
            updateResult = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Test表已经删除");
    }


}
