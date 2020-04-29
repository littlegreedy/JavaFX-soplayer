
package pers.ap.soplayer.jdbc;


import pers.ap.soplayer.database.LyricsFile;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LyricsFilesDao_Imp implements LyDao{
    private static final String SQL_USER_INSET =
            "INSERT INTO testLyrics VALUES(?,?)";
    private static final String SQL_USER_DELETE =
            "DELETE FROM testLyrics WHERE name=? ";

    private static final String SQL_USER_SELECT =
            "SELECT * FROM testLyrics";


    public void create(){
        String sql = "create table testLyrics(Name VARCHAR(20) NOT NULL, url VARCHAR(2083) PRIMARY Key  NOT NULL)";
        int updateResult=-1 ; // 用于获取是否创建成功 如果不成功结果为-1
        Connection conn= JDBCUtils.getConnection();
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            int executeUpdate = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn,preparedStatement,null);
        }
    }
    @Override
    public boolean insert(String name,String url) {
        Connection conn= JDBCUtils.getConnection();
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=conn.prepareStatement(SQL_USER_INSET);



            preparedStatement.setString(1,name);
            preparedStatement.setString(2,url);

            int executeUpdate  =preparedStatement.executeUpdate();

            return executeUpdate > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn,preparedStatement,null);
        }
        return false;
    }
    @Override
    public boolean delete(String name) {
        Connection conn= JDBCUtils.getConnection();
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=conn.prepareStatement(SQL_USER_DELETE);

            preparedStatement.setString(1,name);

            int executeUpdate  =preparedStatement.executeUpdate();

            return executeUpdate > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn,preparedStatement,null);
        }
        return false;
    }


    @Override
    public List<LyricsFile> select() {
        Connection conn= JDBCUtils.getConnection();
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=conn.prepareStatement(SQL_USER_SELECT);
            ResultSet res =preparedStatement.executeQuery();
            ArrayList<LyricsFile> files=new ArrayList<LyricsFile>();
            while(res.next()){
                System.out.println(res.getString("url")+"------------------------");
                System.out.println(res.getString("url").replace("\\","/")+"------------------------");
                files.add(
                        new LyricsFile(res.getString("name"), res.getString("url").replace("\\","/"),
                                new File(res.getString("url").replace("\\","/")))
                        );
            }

            return files;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn,preparedStatement,null);
        }
        return null;
    }
}
