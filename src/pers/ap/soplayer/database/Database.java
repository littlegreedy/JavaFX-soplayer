package pers.ap.soplayer.database;

import pers.ap.soplayer.jdbc.LyricsFilesDao_Imp;
import pers.ap.soplayer.jdbc.SongFilesDao_Imp;
import pers.ap.soplayer.service.menu.MenuService;
import pers.ap.soplayer.service.view.InstallLyrics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库类
 * 存放两种文件
 */

public class Database {
    private ArrayList<SongFile> listSong=new ArrayList<>();
    private ArrayList<LyricsFile> listLyrics=new ArrayList<>();

    public Database(MenuService menuService){
        SongFilesDao_Imp dao_imp=new SongFilesDao_Imp();
        LyricsFilesDao_Imp lyricsFilesDao_imp=new LyricsFilesDao_Imp();
        try {
            dao_imp.create();
            lyricsFilesDao_imp.create();
        }catch (Exception e){
        }

        List<SongFile> files=dao_imp.select();
//        listSong.addAll(files);
        for (SongFile s:files) {
            listSong.add(s);
            menuService.add_to_list(s.fileName);
        }

        List<LyricsFile> filesLy=lyricsFilesDao_imp.select();
        for (LyricsFile s:filesLy) {
            listLyrics.add(s);
        }

    }
    /**
     * 给库加文件
     * @param musicItem 歌曲文件
     */
    public void add(SongFile musicItem,MenuService menuService){
        new SongFilesDao_Imp().insert(musicItem.fileName,musicItem.url);

        menuService.add_to_list(musicItem.getName());
        listSong.add(musicItem);
    }
    /**
     * 给库加文件
     * @param musicItem 歌词文件
     */
    public void add(LyricsFile musicItem){

        new LyricsFilesDao_Imp().insert(musicItem.fileName,musicItem.url);

        listLyrics.add(musicItem);
    }

    public void clear(int index){

        new SongFilesDao_Imp().delete(this.listSong.get(index).fileName);
        this.listSong.remove(index);
        File lyricsFile=InstallLyrics.matchLyricsFile(this.listSong.get(index),this.listLyrics);
        if(lyricsFile==null)    return;
        new LyricsFilesDao_Imp().delete(lyricsFile.getName());

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
