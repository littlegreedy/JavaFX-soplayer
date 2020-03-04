package pers.ap.soplayer.database;

import java.util.ArrayList;

/**
 * 数据库类
 * 存放两种文件
 */

public class Database {
    private ArrayList<SongFile> listSong=new ArrayList<>();
    private ArrayList<LyricsFile> listLyrics=new ArrayList<>();

    public Database(){

    }
    /**
     * 给库加文件
     * @param musicItem 歌曲文件
     */
    public void add(SongFile musicItem){
        listSong.add(musicItem);
    }
    /**
     * 给库加文件
     * @param musicItem 歌词文件
     */
    public void add(LyricsFile musicItem){
        listLyrics.add(musicItem);
    }

    public ArrayList<SongFile> getListSong(){
        return listSong;
    }

    public ArrayList<LyricsFile> getListLyrics(){
        return listLyrics;
    }

    /**
     * 测试函数
     */
    public void listSong(){
       for(MediaItem mediaItem :listSong)
           System.out.println("list:"+ mediaItem.getName());
    }
    /**
     * 测试函数
     */
    public void listLyrics(){
        for(MediaItem mediaItem :listLyrics)
            System.out.println("list:"+ mediaItem.getName());
    }
}
