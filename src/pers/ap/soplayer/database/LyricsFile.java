package pers.ap.soplayer.database;

import java.io.File;


public class LyricsFile extends MediaItem {
    File lyricsFile;
    /**
     * 歌词文件类构造方法
     * @param filename 文件名
     * @param url 路径
     * @param lyricsFile 文件
     */
    public LyricsFile(String filename,String url,File lyricsFile){
        super(filename, url);
        this.lyricsFile=lyricsFile;
    }


    public File use() {
        return lyricsFile;

    }
}
