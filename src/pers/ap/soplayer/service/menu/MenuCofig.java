package pers.ap.soplayer.service.menu;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import pers.ap.soplayer.toolKit.IniConfig;

//菜单所放置的面板类的配置
/**
 * 歌曲列表菜单所放置的面板类的配置
 */
public class MenuCofig {
    VBox menuBox;
    Boolean isShow=false;
//    public MenuCofig(VBox root){
//        menuBox = root;
//    }
    /**
     * 菜单创建
     * @param ap 创建的位置
     */
    public void createMenu(AnchorPane ap){
        menuBox=new VBox();
        menuBox.setVisible(true);
        menuBox.setPrefWidth(IniConfig.getI("menuBoxPrefWidth"));
        menuBox.setPrefHeight(IniConfig.getI("menuBoxPrefHeight"));
        menuBox.setTranslateY(IniConfig.getI("menuBoxTranslateY"));
        menuBox.setBackground(new Background(new BackgroundFill(Color.GREY,null,null)));
        menuBox.setTranslateX(-menuBox.getPrefWidth());
        AnchorPane.setTopAnchor(menuBox,IniConfig.getD("menuBoxTop"));
        AnchorPane.setBottomAnchor(menuBox,IniConfig.getD("menuBoxBottom"));
        ap.getChildren().addAll(menuBox);
    }

    /**
     * 选择类
     * @param btList 列表控制按钮
     * @param pListPic 列表控制按钮图片
     * @param pListRPic 列表控制按钮图片
     */
    public void showOrHideMenu(Button btList, ImageView pListPic,ImageView pListRPic){
        if(!isShow)
            showMenu(btList,pListPic);
        else
            hideMenu( btList ,pListRPic);
    }

    /**
     * 显示菜单
     * @param btList 列表控制按钮
     * @param pListPic 列表控制按钮图片
     */
    private void showMenu(Button btList,ImageView pListPic){
        btList.setDisable(true);
        menuBox.setTranslateX(-menuBox.getPrefWidth());
        Timeline t1=new Timeline();
        KeyValue kv=new KeyValue(menuBox.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf=new KeyFrame(Duration.seconds(IniConfig.getD("AnimationTimeSeconds")),kv);
        t1.getKeyFrames().add(kf);
        t1.setOnFinished(e->{
            isShow=true;
            btList.setGraphic(pListPic);
            btList.setDisable(false);
        });

        t1.play();
    }

    /**
     * 隐藏菜单
     * @param btList 列表控制按钮
     * @param pListRPic 列表控制按钮图片
     */
    private void hideMenu(Button btList,ImageView pListRPic){
        btList.setDisable(true);
        Timeline t1=new Timeline();
        KeyValue kv=new KeyValue(menuBox.translateXProperty(),-menuBox.getPrefWidth());
        KeyFrame kf=new KeyFrame(Duration.seconds(IniConfig.getD("AnimationTimeSeconds")),kv);
        t1.getKeyFrames().add(kf);
        t1.setOnFinished(e->{
            isShow=false;
            btList.setGraphic(pListRPic);
            btList.setDisable(false);
        });
        t1.play();
    }
    /**
     * 返回菜单面板
     */
    public VBox getMenuVBox(){
        return menuBox;
    }



}
