package pers.ap.soplayer.database;

import javafx.scene.media.Media;

import java.io.File;

public class MediaItem {

    protected String url;
    protected String fileName;

    /**
     * 文件类构造方法
     * @param fileName 文件名
     * @param url 路径
     */
    public MediaItem(String fileName, String url){
       this.fileName = fileName;
       this.url=url;

    }


    public String getName(){
        return fileName;
    }

    public String getUrl(){
        return url;
    }
}
