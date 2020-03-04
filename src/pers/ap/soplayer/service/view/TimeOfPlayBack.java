package pers.ap.soplayer.service.view;

import pers.ap.soplayer.toolKit.Clock;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

import java.util.TreeMap;

/**
 * 播放时间类
 */
public class TimeOfPlayBack {
    private volatile Clock current;
    private volatile Clock total;
    private Lyrics lyricsClass=new Lyrics();
//    private Text text;


    /**
     * 显示时间
     * @param text 时间显示文本
     * @param current 代表当前时间的Clock对象
     * @param total   代表结束时间的Clock对象
     */
    //synchronized
    public  static void timeShowStart(Text text,Clock current,Clock total){
        try {
//            current = new Clock(mP.getCurrentTime());
//            total = new Clock(mP.getTotalDuration());
            StringBuffer textStr = new StringBuffer();
            textStr.append(Clock.toFormat(current.getMinutes()));
            textStr.append(":" );
            textStr.append(Clock.toFormat(current.getSeconds()));
            textStr.append(" / ");
            textStr.append(Clock.toFormat(total.getMinutes()));
            textStr.append(":" );
            textStr.append(Clock.toFormat(total.getSeconds()));

//            (Clock.toFormat(current.getMinutes()) + ":" + Clock.toFormat(current.getSeconds()) + " / "
//                    + Clock.toFormat(total.getMinutes()) + ":" + Clock.toFormat(total.getSeconds()));

            text.setText(textStr.toString());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    /**
     * 刷新和维护歌词
     * @param lyricText 放歌词的文本控件
     * @param current  时钟对象
     * @param lyrics 歌词哈希表
     */
    public void lyricsStart( Text[] lyricText,Clock current, TreeMap<Long, String> lyrics){
        try {
//            current = new Clock(mP.getCurrentTime());
//            lyricsClass = new Lyrics(current, lyrics);
            lyricsClass.setTime_and_lyrics(current,lyrics);
            lyricsClass.open(lyricText);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

