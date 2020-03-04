package pers.ap.soplayer.database;

import javafx.scene.media.Media;

public class SongFile extends MediaItem {
    protected Media media;
//    String artist;

    /**
     * 歌曲文件类构造方法
     * @param filename 文件名
     * @param url 路径
     * @param media 媒体文件
     */
    public SongFile(String filename,String url,Media media){
        super(filename, url);
        this.media=media;
    }


    public Media use(){
        return media;
    }


}
