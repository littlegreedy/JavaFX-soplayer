package pers.ap.soplayer.jdbc;



import javafx.scene.media.Media;
import pers.ap.soplayer.database.SongFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongFilesDao_Imp implements OpDao{

    private static final String SQL_USER_INSET =
            "INSERT INTO test VALUES(?,?)";

    private static final String SQL_USER_DELETE =
            "DELETE FROM test WHERE name=?";

    private static final String SQL_USER_SELECT =
            "SELECT * FROM test";


    public void create(){
        String sql = "create table Test(Name VARCHAR(20) NOT NULL, url VARCHAR(2083) PRIMARY Key  NOT NULL)";
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
    public List<SongFile> select() {
        Connection conn= JDBCUtils.getConnection();
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=conn.prepareStatement(SQL_USER_SELECT);

//            preparedStatement.setString(1,url);

            ResultSet res =preparedStatement.executeQuery();

            ArrayList<SongFile> files=new ArrayList<SongFile>();
            while(res.next()){
                files.add(
                        new SongFile(res.getString("name"), res.getString("url"),new Media(res.getString("url"))
                ));
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
