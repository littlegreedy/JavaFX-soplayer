package pers.ap.soplayer.service.view;

import pers.ap.soplayer.toolKit.Clock;

import com.sun.istack.internal.Nullable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pers.ap.soplayer.toolKit.IniConfig;
import pers.ap.soplayer.toolKit.SmallHandleFile;
import pers.ap.soplayer.database.LyricsFile;
import pers.ap.soplayer.database.SongFile;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 歌词展示的维护类
 */
class Lyrics {
    //时钟
     private Clock current=null;
    //歌词映射表
     TreeMap<Long, String> lyrics;
    //时间的标记
    static Long check1 = 0L;   //center

    Lyrics(){

    }

    Lyrics(Clock c, TreeMap<Long, String> lyrics) {
        this.current = c;
        this.lyrics=lyrics;
    }
    /**
     * 海报显示
     * @param c 时钟
     * @param lyrics 歌词
     */
    public void setTime_and_lyrics(Clock c, TreeMap<Long, String> lyrics){
        this.current = c;
        this.lyrics=lyrics;
    }
    /**
     *  获得歌词的时间
     * @return 返回“当前”的时间
     */
     Long getTimeScroll() throws Exception{
        @Nullable Long check2 = lyrics.floorKey(handleTime(current ,100L)); //偏移量
        if(check2==null) return null;
        try {
            //确保歌词映射的每句歌词不被重复显示
            if (check1 == check2 || check2 == 0L) {
                return -10L;    //0L不可
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        check1 = check2;
        return check2;
    }

    /**
     *  提供偏移量
     * @param c 时钟
     * @param shift 人为设定的时间的偏移量
     * @return 返回“当前”的时间
     */
    private Long handleTime(@Nullable Clock c, Long shift){
        return c.getTotalMillis() + shift;
    }

//    Iterator handleIterator(){
//
//        Iterator its=lyrics.keySet().iterator();
//        String timeNow=Clock.toFormat(current.getMinutes())+":"+Clock.toFormat(current.getSeconds())+"."+Clock.toFormat(current.getMillis());
//        while(its.hasNext() && (String)its.next()!=lyrics.ceilingKey(timeNow)){
//
//        }
//        return its;
//    }
//                Iterator iter=map.keySet().iterator();
//                int index=2;
//                while(iter.hasNext() && index!=5){
//                    lyricText[index].setText(lyrics.get((Long)iter.next()));
//                    index++;
//                }

    /**
     *  刷新歌词文本
     * @param lyricText 歌词文本
     */
     void  open(Text[] lyricText) throws  Exception{
        Long key=this.getTimeScroll();
        if(key==null || !lyrics.containsKey(key) )  //key重复或未查询到key，则退出函数
            return ;
        String center = lyrics.get(key);   //value
        lyricText[1].setText(center);  //初始化高亮核心层
        if (lyrics.lowerKey(key) != null) {      //刷新高亮层的上一层的text
            lyricText[0].setText(lyrics.get(lyrics.lowerKey(key)));
        }
        if (lyrics.higherKey(key) != null) {    //刷新高亮层的下三层的text
            try {
                SortedMap<Long, String> map =lyrics.tailMap(lyrics.higherKey(key));   //剩余未播放的歌词填入排序map

//                WeakHashMap<> whm=lyrics.tailMap(lyrics.higherKey(key));
                System.gc();
                     //遍历刷新三个text
                int index = 2;
                int limit=map.size();   //剩余未播放的歌词数量
                for (Map.Entry entry : map.entrySet()) {
                    if (index > 4) break;     //只能刷新2、3、4三层
                    String value = (String) entry.getValue();
                    if(limit>=index) {
                        lyricText[index].setText(value);
                    }
                    else {    //拒绝剩余刷新歌词  因为foreach循环遍历 map.entrySet()
                             lyricText[index].setText("");
                    }
                    //                    int key = (int) entry.getKey();
                         index++;
                }
            }catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


//    Boolean check(String str){
//        return lyrics.containsKey(str);
//    }
//    void open(Text[] lyricText, Iterator iter ){
//        //if go to the position-----------------------------------
////        iter=handleIterator();
//        String timeScroll=this.getTimeScroll();
//        if(lyrics.containsKey(timeScroll)){
//            if (iter.hasNext()) {
//                nn=5;
//                for (int i = 0; i <= nn - 2; i++) {
//                    lyricText[i].setText(lyricText[i + 1].getText());
//                }
//                Object o=iter.next();
//                lyricText[nn - 1].setText(lyrics.get(o));
//            }
//            else {
//                System.out.println(" "+"接下去没有歌词了");
//                for (int i = 0; i <= nn - 2; i++) {
//                    lyricText[i].setText(lyricText[i + 1].getText());
//                }
//                nn--;
//                lyricText[nn].setText(" ");
//            }
//        }
//    }
}



/**
 *  歌词初始化
 */
public class InstallLyrics {
    //歌词面板
    VBox ap=new VBox(10);

    Font font=new Font(IniConfig.getI("fontSize"));
    Font fontCenter=new Font(IniConfig.getI("fontCenterSize"));
    Color colorCom=Color.web(IniConfig.get("colorCom"),IniConfig.getD("opacityCom"));
    Color colorCenter=Color.web(IniConfig.get("colorCenter"),IniConfig.getD("opacityCenter"));
    //相关控件
    Label songTitle;
    Label songArtist;

    //歌词文本数量
    int nn=5;
    //高亮歌词文本索引
    int centerIndex=1;
    //歌词位置
    double ix=0;
    double[] iy=new double[nn];
    //歌词文本
    Text lyricTextC=new Text();
    Text[] lyricText=new Text[nn];
    //歌词哈希表
    volatile TreeMap<Long,String> lyrics=new TreeMap<Long,String>();
    //迭起器
    Iterator iter ;


    /**
     *  歌词初始化构造器
     * @param t1 歌名标签
     * @param t2 歌手名标签
     */
    public InstallLyrics(Label t1,Label t2){
        songTitle=t1;
        songArtist=t2;
    }

    /**
     *  歌词初始化
     * @param root 外界面板，用来设置歌词面板锚定的位置
     */
    public void initLyrics(AnchorPane root) {
        ap.setBackground(new Background(new BackgroundFill(Color.color(0.5,0.5,0.5,0.3),new CornerRadii(15),null)));//Color.valueOf("#696961")


        // ap.setPrefSize(width,height);
        ap.setAlignment(Pos.CENTER);
        ap.setPrefSize(IniConfig.getI("lyricsBoxPreWidth"),IniConfig.getI("lyricsBoxPrefHeight"));
//        ap.setMaxSize(600,580);
        AnchorPane.setTopAnchor(ap,IniConfig.getD("lyricsBoxTop"));
        AnchorPane.setRightAnchor(ap,IniConfig.getD("lyricsBoxRight"));
        AnchorPane.setBottomAnchor(ap,IniConfig.getD("lyricsBoxBottom"));
        AnchorPane.setLeftAnchor(ap,IniConfig.getD("lyricsBoxLeft"));
        root.getChildren().add(ap);

        //所有歌词文本的总长度
        double heightLyrics=IniConfig.getD("lyricsTextShiftHeight");
        //每个歌词文本高度 y位置
        for(int j=0;j<nn;j++){
            iy[j]=(j+1)*heightLyrics/nn;
        }
        //每个歌词文本x位置
        ix= IniConfig.getI("lyricsTextStartX");

        //Text initiate-------------------------
        ap.getChildren().addAll( songTitle,songArtist);
        for(int i=0;i<nn;i++){
            lyricText[i]=new Text();
            lyricText[i].setTranslateX(ix);
            lyricText[i].setTranslateY(iy[i]);
            lyricText[i].setFont(font);
            lyricText[i].setFill(colorCom);
            lyricText[i].setWrappingWidth(IniConfig.getI("lyricsTextWrappingWidth"));
            lyricText[i].setTextAlignment(TextAlignment.CENTER);
            ap.getChildren().add(lyricText[i]);
        }
        //centralization
        lyricTextC=lyricText[centerIndex];
        lyricTextC.setFont(fontCenter);
        lyricTextC.setFill(colorCenter);

//        fillLyrics();
        }
    /**
     *  歌词填充
     * @param songFile 歌曲文件
     * @param lFile 存放歌词的列表指针
     */
     public void fillLyrics(SongFile songFile, ArrayList<LyricsFile> lFile) {
         //Text initiate-------------------------
         try {
             //词曲匹配
             File f = matchLyricsFile(songFile,lFile);
             //放置null指针错误
             if(f==null) return;
             //如果匹配到，则删除之前存放的歌词
             killLyrics(lyrics);
             //读取文件
             FileInputStream fis = new FileInputStream(f);
             InputStreamReader isr = new InputStreamReader(fis,"gbk"); //指定以gbk编码读入
             BufferedReader br = new BufferedReader(isr);
             String thisLine;
             //正则
             Pattern pattern = Pattern.compile("\\d{2}:\\d{2}.\\d{2}");
//             BufferedReader br = new BufferedReader(new FileReader(f));

             while ((thisLine = br.readLine()) != null) {
                 handleLyricsFile(thisLine, pattern);  //URLDecoder.decode( thisLine, "GBK" )
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         //test result：    result 不能填装key为空的歌词
         //利用迭代器刷新余下文本
         iter = lyrics.keySet().iterator();
         //initiate mediaPlayer on Ready  && lyric introduce successfully---------------------------
         for (int i = 3; i < nn; i++) {
             if (iter.hasNext()) {
                 Object o = iter.next();
                 lyricText[i].setText(lyrics.get(o));
             }
         }
         //至此完成nn个文本的全部初始化刷新
    }
    /**
     *  正则收集时间轴右边的歌词文本
     * @param thisLine 歌词文件的每一行
     * @param pattern 用来匹配时间轴的正则样式
     */
    private void handleLyricsFile(String thisLine,Pattern pattern){
//        System.out.println(thisLine);
        //时间   \\d{2}:\\d{2}:\\{2}
        String timeS;
        String word;

        Matcher matcher = pattern.matcher(thisLine);
        if (matcher.find()) {
            timeS = matcher.group();
            int indexWord = 10;
            //格式：【xx：xx：xx】word    用来匹配word
            word = thisLine.charAt(indexWord - 1) == ']' ? thisLine.substring(indexWord) : thisLine.substring(indexWord + 1);

//                    if(word.isEmpty()) word="_";
//                    System.out.println(matcher.group()+" "+word);
            //时间转换成以ms为单位
            long timeL = timeToLongMs(timeS);
            //添加到歌词哈希表
            lyrics.put(timeL, word);
//            System.out.println(timeL + " " + word);
        }
    }

    /**
     *  清除歌词哈希表 并 刷新为空显示
     * @param lyrics 歌词哈希表
     */
    private void killLyrics( TreeMap<Long,String> lyrics) {
        lyrics.clear();
        for(int i=0;i<nn;i++) {
            lyricText[i].setText(" ");
        }
        System.gc();
    }

    public TreeMap<Long, String> getLyrics() {
        return lyrics;
    }

    public Text[] getLyricText(){
        return lyricText;
    }

    public VBox getLyricPane(){
        return  ap;
    }
    public Iterator getIter(){
        return iter;
    }

    public void setLyricsPane(float op){
       ap.setOpacity(op);
    }

    /**
     *  ms为单位的时间
     * @param time 字符串time
     * @return 时间数字
     */
    private static long timeToLongMs(String time){
        String []s=time.split("\\:");
        int min=Integer.parseInt(s[0]);
        String []ss=s[1].split("\\.");
        int sec=Integer.parseInt(ss[0]);
        int ms=Integer.parseInt(ss[1]);
        return min*60*1000+sec*1000+ms*10;
    }


    /**
     * 歌词文件匹配当前歌曲
     * @param songFile 歌曲文件
     * @param lFile  歌词文件列表指针
     * @return 歌词文件 or NULL
     */
    private File matchLyricsFile(SongFile songFile, ArrayList<LyricsFile> lFile)  {
        for(LyricsFile lF:lFile) {
//            System.out.println(SmallHandleFile.getFileName(lF.getName())+SmallHandleFile.getFileName(URLDecoder.decode( media.getSource(), "UTF-8" )));
//            if(SmallHandleFile.getFileName(lF.getName()).equals(SmallHandleFile.getFileName(URLDecoder.decode( media.getSource(), "UTF-8" )))){
//                return lF;
//            }
//            System.out.println(SmallHandleFile.toFileName(songFile.getName())+" ------------------------------ "+SmallHandleFile.toFileName(lF.getName()));
            //按文件名字匹配
            if (SmallHandleFile.toFileName(songFile.getName()).equals(SmallHandleFile.toFileName(lF.getName()))) {
                return lF.use();
            }
        }
        return null;
    }

}
