package pers.ap.soplayer.toolKit;

import javafx.scene.media.Media;
import pers.ap.soplayer.database.Database;
import pers.ap.soplayer.database.LyricsFile;
import pers.ap.soplayer.database.SongFile;
import pers.ap.soplayer.service.menu.MenuService;

import java.io.File;
import java.util.List;


/**
 * 文件处理者
 */
public class FileHandler {

    /**
     *筛选歌曲及歌词文件
     *@param fileList 文件列表指针
     * @param database 数据库
     * @param menuService 歌曲菜单列表服务
     */
    public  static void effect(List<File> fileList, Database database, MenuService menuService) {
        //分开->去重（Unique.goUniqueXXX）
        for (File f : fileList) {
            String fix = f.getName().substring(f.getName().lastIndexOf("."));
            if (fix.equals(".lrc")) {          //lyrics
                if ( Unique.goUniqueLyrics(database.getListLyrics(),f)) {
                    System.out.println(f.getName());
                    LyricsFile musicItem = new LyricsFile(f.getName(), f.toURI().toString(),f);
                    database.add(musicItem);  //add

                }
            } else if (fix.equals(".mp3") || fix.equals(".wav") || fix.equals(".aac")) {            //song
                if ( Unique.goUniqueSong(database.getListSong(),f)) {
                    System.out.println(f.getName());
                    SongFile musicItem = new SongFile(f.getName(), f.toURI().toString(),new Media(f.toURI().toString()));
                    database.add(musicItem,menuService);  //add
                    try {
//                        menuService.add_to_list(musicItem.getName());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }
        }
    }



}
