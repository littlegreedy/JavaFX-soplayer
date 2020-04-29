package pers.ap.soplayer.jdbc;


import pers.ap.soplayer.database.SongFile;

import java.util.List;

public interface OpDao {

//    int login(User user);

    //添加
    boolean insert(String name,String url);

    //删除
    boolean delete(String sName);


//    boolean update(User user);

    //查询
    List<SongFile> select();

    void create();
}
