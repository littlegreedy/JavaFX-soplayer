package pers.ap.soplayer.service.view;

import javafx.collections.ObservableMap;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pers.ap.soplayer.toolKit.Clock;
import pers.ap.soplayer.toolKit.IniConfig;
import pers.ap.soplayer.toolKit.InitializeException;
import pers.ap.soplayer.toolKit.UnlimitedAppend;

/**
 * 展示歌曲信息
 */
public class Information {

    /**
     * 歌曲详情页面
     * @param rMI2 控件——弹出菜单项
     * @param mPlayer 播放数据
     * @param map   媒体数据
     * @param img 图片
     */
    public  static void songInfoRepresent(RadioMenuItem rMI2, MediaPlayer mPlayer, ObservableMap<String,Object> map, Image img) {
        rMI2.setOnAction(event -> {
            Stage stageInfo = new Stage();
            Text aboutTxt=new Text();
//                    aboutTxt.setTextAlignment(TextAlignment.CENTER);
            aboutTxt.setWrappingWidth(400);
//            File file=new File(mList.get(index).getUrl());   //计算比特率 时间因素暂时搁置
//            System.out.println(file.toPath());
//             System.out.println(file.length());

            //时钟对象
            Clock sTime=new Clock(mPlayer.getStopTime());

//            new StringBuffer();
//            sB.append( "曲名:\n");
//            sB.append( map.get("title").toString());
//            sB.append("\n\n歌手:\n");
//            sB.append( map.get("artist").toString());
//            sB.append("\n\n专辑:\n");
//            sB.append( map.get("album").toString());
//            sB.append("\n\n时长: ");
//            sB.append(sTime.getMinutes());
//            sB.append(":");
//            sB.append(sTime.getSeconds());
//            sB.append("                                      genre: ");
//            sB.append(map.get("genre").toString());

            //拼接字符串
            StringBuffer sB  = UnlimitedAppend.appendAll("曲名:\n",map.get("title").toString(),
                    "\n\n歌手:\n",map.get("artist").toString(),
                    "\n\n专辑:\n",map.get("album").toString(),
                    "\n\n时长: ",Integer.toString(sTime.getMinutes()),":",Integer.toString(sTime.getSeconds()),
                    "                                      genre: ",map.get("genre").toString());

            //设置文本
            aboutTxt.setText(sB.toString());
            HBox aboutPane=new HBox();
            aboutPane.setAlignment(Pos.CENTER);

            aboutPane.setPrefSize(IniConfig.getI("InfoPreWidth"),IniConfig.getI("InfoPreHeight"));

            aboutPane.getChildren().add(aboutTxt);
            Scene scene=new Scene(aboutPane,IniConfig.getI("InfoPreWidth"),IniConfig.getI("InfoPreHeight"));
            stageInfo.setScene(scene);
            stageInfo.getIcons().add(img);
            stageInfo.show();
            stageInfo.setResizable(false);
            stageInfo.setTitle("歌曲详情  -- LibraPlayer -- By LittleGreedy");
        });
    }

    /**
     * 曲名歌手显示
     * @param label 控件——标签
     * @param map   媒体数据
     * @param info 区别曲名、歌手的key
     * @param font 字体
     * @param color 字体颜色
     */
    public  static void info_to_Label(Label label,ObservableMap<String,Object> map,String info,Font font,Color color){
        label.setMaxWidth(IniConfig.getI("labelMaxWidth"));
        label.setAlignment(Pos.CENTER);
        label.setText((String) map.get(info));
        label.setFont(font);
        label.setTextFill(color);
    }

    /**
     * 海报显示
     * @param labelView 控件——标签
     * @param iVSong ImageView对象用来展示海报
     * @param map   媒体数据
     */
    public  static void info_to_Image(Label labelView,ImageView iVSong,ObservableMap<String,Object> map){
        iVSong.setImage((Image)map.get("image"));
//        iVSong.setPreserveRatio(true);
        iVSong.setFitWidth(IniConfig.getI("ImageViewSongFitWidth"));
        iVSong.setFitHeight(IniConfig.getI("ImageViewSongFitHeight"));
//        iVSong.setStyle("-fx-border-radius:50%");
        labelView.setGraphic(iVSong);
    }
}
