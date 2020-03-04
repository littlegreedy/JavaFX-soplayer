package pers.ap.soplayer.toolKit;

import pers.ap.soplayer.database.LyricsFile;
import pers.ap.soplayer.database.SongFile;

import java.io.File;
import java.util.ArrayList;
/**
 * 文件去重
 */
public class Unique {

//    public static <E>Boolean goUnique(E[] list, File f){
//        //保证良好的可读性
//        //wantAdd 确定是否重复
//        boolean wantAdd = true;
//        for (E lf : list) {
//            if (lf.getName().equals(f.getName())) {
//                wantAdd = false;
//                return  wantAdd;
//            }
//        }
//        return  wantAdd;
//    }

    /**
     * 歌曲文件去重
     * @param list 文件列表指针
     * @param f 目标文件
     */
    public static <E>Boolean goUniqueSong(ArrayList<SongFile> list, File f){
        //保证良好的可读性
        //wantAdd 确定是否重复
        boolean wantAdd = true;
        for (SongFile lf : list) {
            if (lf.getName().equals(f.getName())) {
                wantAdd = false;
                return  wantAdd;
            }
        }
        return  wantAdd;
    }
    /**
     * 歌词文件去重
     * @param list 文件列表指针
     * @param f 目标文件
     */
    public static <E>Boolean goUniqueLyrics(ArrayList<LyricsFile> list, File f){
        //保证良好的可读性
        //wantAdd 确定是否重复
        boolean wantAdd = true;
        for (LyricsFile lf : list) {
            if (lf.getName().equals(f.getName())) {
                wantAdd = false;
                return  wantAdd;
            }
        }
        return  wantAdd;
    }
}
