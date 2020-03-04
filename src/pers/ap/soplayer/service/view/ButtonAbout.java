package pers.ap.soplayer.service.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import pers.ap.soplayer.toolKit.IniConfig;
import pers.ap.soplayer.toolKit.UnlimitedAppend;
/**
 * 关于
 */
public class ButtonAbout implements EventHandler<ActionEvent> {
    Image image;
    Button btn;
    /**
     * 选择类
     * @param im 窗口图片
     * @param btn 控制按钮
     */
    public ButtonAbout(Image im, Button btn){
        image=im;
        this.btn=btn;
    }

    @Override
    public void handle(ActionEvent event){
        btn.setDisable(true);

        Stage stage = new Stage();
        Text aboutTxt=new Text();
//                    aboutTxt.setTextAlignment(TextAlignment.CENTER);
        aboutTxt.setWrappingWidth(IniConfig.getI("AboutTextWrappingWidth"));


        StringBuffer sB  = UnlimitedAppend.appendAll("音乐就像是谁站在万仞之上，于风暴中厉喊。\n\n" ,
                "[简述]\n这是一款小巧精美的播放器，试验名Libra Player，正式名soPlayer\n",
                "作者： littleGreedy" ,
                "\n\n[导入歌曲说明]\n可通过文件选择器或拖动歌词文件和歌曲文件加入歌单\n\n[歌词文件说明]\n需要与对应的歌曲MP3同",
                "名(.Iry文件)\n\n [当前实现功能]\n*界面精美且支持自定义背景\n" ,
                "*支持播放的音乐格式： MP3文件、WAV文件、AAC文件\n",
                "*支持解析及滚动歌词（.lrc文件）\n",
                "*支持解析歌曲信息（专辑、时长、缩略图等等）\n",
                "*支持频谱动效\n",
                "*支持歌单播放操作（raw）\n",
                "*支持托盘控制（raw）\n",
                "\nLittleGreedy's greedy comes from greedy algorithms"
                );

        aboutTxt.setText(sB.toString());
        HBox aboutPane=new HBox();
        aboutPane.setAlignment(Pos.CENTER);
        aboutPane.setPrefSize(IniConfig.getI("AboutPrefWidth"),IniConfig.getI("AboutPrefHeight"));
        aboutPane.getChildren().add(aboutTxt);
        Scene scene=new Scene(aboutPane,IniConfig.getI("AboutPrefWidth"),IniConfig.getI("AboutPrefHeight"));
        stage.setScene(scene);
        stage.getIcons().add(image);
        stage.show();
        stage.setResizable(false);
        stage.setTitle("About--soPlayer-- By LittleGreedy");
        //不可重复打开
        stage.setOnCloseRequest(event1 ->   btn.setDisable(false));
    }
}
