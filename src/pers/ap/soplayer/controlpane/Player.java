package pers.ap.soplayer.controlpane;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import pers.ap.soplayer.service.view.*;
import pers.ap.soplayer.toolKit.Clock;
import pers.ap.soplayer.database.Database;
import pers.ap.soplayer.database.ImgFactory;
import pers.ap.soplayer.database.SongFile;
import pers.ap.soplayer.layout.ButtonHover;
import pers.ap.soplayer.service.fileService.FileParty;
import pers.ap.soplayer.service.fileService.FileDrag;
import pers.ap.soplayer.service.fileService.SetBackground;
import pers.ap.soplayer.service.menu.MenuCofig;
import pers.ap.soplayer.service.menu.MenuService;
import pers.ap.soplayer.toolKit.IniConfig;
//import  pers.ap.soplayer.controlpane.MySystemTray;
import java.util.ArrayList;


/**
 * 播放器 kernel
 */
public class Player {
    //锚面板
    private AnchorPane ap=new AnchorPane();
    /**
     * 标记
     */
    //    下一首/上一首
    protected enum Mark{NEXT,LAST};
    protected Mark MARK;
    //    播放/暂停
    protected boolean paseMark=true;
    //    播放模式
    private enum PlayPattern {LOOP,SINGLELOOP,RANDOM;}
    private  PlayPattern PLAYPATTERN =PlayPattern.LOOP;
    private boolean mouse;
    //    showPic为true代表展示海报，不展示频谱
    private boolean showPic=true;
    //    声音是否静默
    private  boolean isMute=false;
    //歌词面板显示
    private  boolean lyricsPane=true;
    /**
     * 配置用到的数据
     */
    //鼠标坐标
    private double xOffSet = 0;
    private double yOffSet = 0;

    //宽、图形数量
    private  double recwidth=IniConfig.getD("rectangleWidth");
    private  int rectangle_num=IniConfig.getI("rectangleNum");

    /**
     * 显示title artist的文本字体、颜色
     */
    private Font fontArtist=new Font(IniConfig.getI("fontArtistSize"));
    private Font fontTitle=new Font(IniConfig.getI("fontTitleSize"));
    private Color colorArtist=Color.web(IniConfig.get("colorArtist"),IniConfig.getD("opacityArtist"));
    private Color colorTitle=Color.web(IniConfig.get("colorTitle"),IniConfig.getD("opacityTitle"));

    /**
     * 播放参数
     */
    //播放器
    protected MediaPlayer mPlayer;
    //歌表
    private ArrayList<SongFile> mList;
    // 歌曲列表索引
    private int index;
    //数据库
    private  Database database;

    /**
     * 控件
     */
    Button bt0 ;   //播放暂停键
    Button bt1 ;  //上一首
    Button bt1R ;  //下一首
    Button bt2 ;    //退出
    Button bt3 ;    //最大化
    Button bt3R ;    //最小化
    Label  labelView = new Label("");   //歌曲信息
    Button btList ;  //歌曲列表菜单
    Button bt2add; //文件选择器按钮
    Button btModel; //模式
    Button btAbout ; //关于
    Button btVolume; //音量条
    Button btPic; //海报展示按钮
    Text text=new Text(); //播放时间文本
    Label songTitle=new Label();  //歌名标签
    Label songArtist=new Label();  //歌手名标签
    ImageView iVSong=new ImageView();  //海报图片
    Slider sVolume = new Slider();  //音量条
    Slider sPlayBack = new Slider(); //播放进度条

    RadioMenuItem rMI1;  //set Background
    RadioMenuItem rMI1_opacity; //歌词展示
    RadioMenuItem rMI2;  //play information
    RadioMenuItem rMI3;  //usage


    /**
     * 图片
     */
    protected ImgFactory imgFactory;  //factory
    private  ImageView pasePic;
    private  ImageView pausePic;
    private  ImageView pListPic;
    private  ImageView pListRPic;

    //歌曲时间
    TimeOfPlayBack timeOfPlayBack=new TimeOfPlayBack();
    //歌词模块
    InstallLyrics installLyrics=new InstallLyrics(songTitle,songArtist);

    /**
     * 部分功能类声明
     */
    MenuService menuService;  //歌曲列表菜单服务
    FileParty fileParty;   //文件添加，方式——选择器
    FileDrag fileDrag;     //文件添加，方式——拖动
    SetBackground newBackground; //图片文件添加，方式——拖动
    ChartsSpectrumData chartsSpectrumData; //频谱图
    AboutEffect aboutEffect=new AboutEffect();  //用法说明

    //时钟类
     Clock current=new Clock();
     Clock total=new Clock();


    //构造器
    Player(Database database, ImgFactory iF){
        this.database = database;
        imgFactory=iF;
    }


    /**
     * 故事的开始
     * @param stage 主舞台
     */
    Pane theStoryBegins(Stage stage) {

        //初始化媒体数据
         mList=database.getListSong();

        /**
         * 功能类初始化
         */


        // 歌词模块
        installLyrics.initLyrics(ap);
//        fileResource.invokeFileDrag(ap,menuService);

        //图表模块
        chartsSpectrumData=new ChartsSpectrumData(recwidth, rectangle_num);
        chartsSpectrumData.chartConfig(ap);


        //歌曲海报
        ap.getChildren().add(labelView);
        //定义海报在面板的位置
        AnchorPane.setTopAnchor(labelView,IniConfig.getD("labelViewTop"));
        AnchorPane.setLeftAnchor(labelView,IniConfig.getD("labelViewLeft"));
        AnchorPane.setBottomAnchor(labelView,IniConfig.getD("labelViewBottom"));
//        labelView.setMaxSize(100,100);


        //菜单模块
        MenuCofig menuCofig=new MenuCofig();
        menuCofig.createMenu(ap);
        menuService=new MenuService();
        menuService.initMenu(menuCofig.getMenuVBox());   //初始化歌单
        menuService.lVMenu.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener(){
            @Override
            public void invalidated(Observable observable) {
                for(Integer i:menuService.lVMenu.getSelectionModel().getSelectedIndices())   //设置为单选，故只是循环一次
                    if(i!=0)    //listView的第零项不允许被选择
                        initMusic_Index(i-1);                                                   //读取可读数组的值
            }
        });


        //数据库操作接口—— 添加歌曲和歌词文件
        fileParty=new FileParty(database);
        fileDrag=new FileDrag(database);
        fileDrag.invokeFileDrag(ap,menuService);

        //about
//        aboutEffect.startAbout(imgFactory);
        //图标模块

        ImageView backgroundPic=imgFactory.getImgFlyweight("background_img").getView(IniConfig.getI("PLAYERWIDTH"),IniConfig.getI("PLAYERHEIGHT"));
        ImageView volumePic=imgFactory.getImgFlyweight("volume_hover").getView(IniConfig.getI("minorWidth"),IniConfig.getI("minorHeight"));
        ImageView volumelessPic=imgFactory.getImgFlyweight("volumeless_hover").getView(IniConfig.getI("minorWidth"),IniConfig.getI("minorHeight"));

        pListPic=imgFactory.getImgFlyweight("pList_hover").getView();
        pListRPic=imgFactory.getImgFlyweight("pListR_hover").getView();
        pasePic=imgFactory.getImgFlyweight("pase_hover").getView(IniConfig.getI("largeWidth"),IniConfig.getI("largeHeight"));
        pausePic=imgFactory.getImgFlyweight("pause_hover").getView(IniConfig.getI("largeWidth"),IniConfig.getI("largeHeight"));

        //按钮控件初始化
        bt0 = new Button("",pasePic);   //播放暂停键
        bt1 = new Button("",imgFactory.getImgFlyweight("last_hover").getView());  //上一首
        bt1R = new Button("",imgFactory.getImgFlyweight("next_hover").getView());  //下一首
        bt2 = new Button("",imgFactory.getImgFlyweight("close_hover").getView());    //退出
        bt3 = new Button("",imgFactory.getImgFlyweight("fill_hover").getView());    //最大化
        bt3R = new Button("",imgFactory.getImgFlyweight("min_hover").getView());    //最小化
        //        Label label = new Label("", new ImageView(imSURL));   //歌曲信息
        btList = new Button("",imgFactory.getImgFlyweight("pListR_hover").getView());   //歌曲列表菜单
        bt2add=new Button("",imgFactory.getImgFlyweight("add_hover").getView());    //文件选择器按钮
        btModel=new Button("",imgFactory.getImgFlyweight("loop2_hover").getView());  //模式
        btAbout =new Button("",imgFactory.getImgFlyweight("about_hover").getView(IniConfig.getI("mWidth"),IniConfig.getI("mHeight"))); //关于
        btVolume=new Button("",volumePic);  //音量条
        btPic =new Button("",imgFactory.getImgFlyweight("picture_hover").getView(IniConfig.getI("mWidth"),IniConfig.getI("mHeight"))); //海报展示按钮

        //按钮伪类
        bt0.hoverProperty().addListener(new ButtonHover(bt0));
        bt1.hoverProperty().addListener(new ButtonHover(bt1));
        bt1R.hoverProperty().addListener(new ButtonHover(bt1R));
        bt2.hoverProperty().addListener(new ButtonHover(bt2));
        bt2add.hoverProperty().addListener(new ButtonHover(bt2add));
        btModel.hoverProperty().addListener(new ButtonHover( btModel));
        btList.hoverProperty().addListener(new ButtonHover( btList));
        bt3.hoverProperty().addListener(new ButtonHover(bt3));
        bt3R.hoverProperty().addListener(new ButtonHover(bt3R));
        btAbout.hoverProperty().addListener(new ButtonHover(btAbout));
        btVolume.hoverProperty().addListener(new ButtonHover(btVolume));
        btPic.hoverProperty().addListener(new ButtonHover(btPic));

        //按钮设置背景
        btAbout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#fff00000"),null,null)));
        bt3.setBackground(new Background(new BackgroundFill(Paint.valueOf("#fff00000"),null,null)));
        bt3R.setBackground(new Background(new BackgroundFill(Paint.valueOf("#fff00000"),null,null)));
        bt2.setBackground(new Background(new BackgroundFill(Paint.valueOf("#fff00000"),null,null)));
        bt0.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        bt1.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        bt1R.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        btList.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        btModel.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        bt2add.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        btVolume.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
        btPic.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));

        //按钮设置说明
        RepresentTIp.instance(bt0,"播放/暂停\nCtrl+空格");
        RepresentTIp.instance(bt1,"上一首\nCtrl+小键盘<-");
        RepresentTIp.instance(bt1R,"下一首\nCtrl+小键盘->");
        RepresentTIp.instance(bt2,"关闭");
        RepresentTIp.instance(bt3,"最大化");
        RepresentTIp.instance(bt3R,"最小化");
        RepresentTIp.instance(bt2add,"添加\nCtrl+O");
        RepresentTIp.instance(btModel,"播放模式");
        RepresentTIp.instance(btList,"播放列表");
        RepresentTIp.instance(btAbout,"关于");
        RepresentTIp.instance(btVolume,"音量");
        RepresentTIp.instance(btPic,"歌曲海报");
        RepresentTIp.instance(text,"播放时间");
        RepresentTIp.instance(sPlayBack,"播放时间");
        RepresentTIp.instance(sVolume,"音量");

        //海报展示事件
        btPic.setOnAction(event -> {
            if(!showPic){
                showPic=true;
                labelView.setVisible(true);
            }
            else{
                showPic=false;
                labelView.setVisible(false);
            }
        });

        //鼠标-音量条事件
        btVolume.setOnMouseClicked(e->{
            if(!isMute){
                isMute=true;
                btVolume.setGraphic(volumelessPic);
                sVolume.setValue(0);
                mPlayer.setMute(true);
            }
            else{
                isMute=false;
                sVolume.setValue(30);
                btVolume.setGraphic(volumePic);
                mPlayer.setMute(false);
            }
        });

        //播放 暂停
        bt0.setOnAction(e -> {
            if (paseMark) {
                mPlayer.play();
                paseMark=false;
                bt0.setGraphic(
                        imgFactory.getImgFlyweight("pause_hover").getView(IniConfig.getI("largeWidth"),IniConfig.getI("largeHeight"))
                );
            } else {
                mPlayer.pause();
                paseMark=true;
                bt0.setGraphic(
                        imgFactory.getImgFlyweight("pase_hover").getView(IniConfig.getI("largeWidth"),IniConfig.getI("largeHeight"))
                );
            }
        });


        //后一首 前一首
        bt1.setOnAction(e -> {
//            mark="LAST";
            MARK=Mark.LAST;
            initMusic();
            //mPlayer.seek(Duration.ZERO);
        });
        bt1R.setOnAction(e -> {
//            mark="NEXT";
            MARK=Mark.NEXT;
            initMusic();
        });
        //bt0.setPickOnBounds();
        //关闭stage
        bt2.setOnAction(e -> {
//            stage.close()；
            MySystemTray.getInstance().hide(stage);
        });

        //stage的最大化
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        bt3.setOnAction(e->{
            if( stage.getWidth()!=primaryScreenBounds.getWidth()) {
//            stage.setMaximized(true);
                backgroundPic.setFitWidth(primaryScreenBounds.getWidth());
                backgroundPic.setFitHeight(primaryScreenBounds.getHeight());
                stage.setX(primaryScreenBounds.getMinX());
                stage.setY(primaryScreenBounds.getMinY());
                stage.setWidth(primaryScreenBounds.getWidth());
                stage.setHeight(primaryScreenBounds.getHeight());
                //菜单尺寸更新
                menuService.menuSize(menuCofig.getMenuVBox());
//                dect =primaryScreenBounds.getWidth()/1280;
//                System.out.println(dect);
//                recwidth*=dect;
//                r*=dect;
            }
            else{
                backgroundPic.setFitWidth(IniConfig.getI("PLAYERWIDTH"));
                backgroundPic.setFitHeight(IniConfig.getI("PLAYERHEIGHT"));
                stage.setWidth(IniConfig.getI("PLAYERWIDTH"));
                stage.setHeight(IniConfig.getI("PLAYERHEIGHT"));
                stage.centerOnScreen();
                //菜单尺寸更新
                menuService.menuSize(menuCofig.getMenuVBox());
//                recwidth/=dect;
//                r/=dect;
            }
        });

        //stage最小化
        bt3R.setOnAction(e->{
            stage.setIconified(true);
            menuService.menuSize(menuCofig.getMenuVBox());
//            MySystemTray.getInstance().listen(stage);
        });



        //按钮菜单事件
        btList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuCofig.showOrHideMenu(btList,pListPic,pListRPic);
            }
        });

        //about页面的icon
        btAbout.setOnAction(new ButtonAbout(imgFactory.getImgFlyweight("stage_icon").get(),btAbout));

        //文件选择器添加歌曲
        bt2add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileParty.invokeFileChooser(menuService);
            }
        });

        //播放模式 列表循环 - 单曲循环 -随机播放
        btModel.setOnAction(e-> {
            if(PLAYPATTERN.equals(PlayPattern.LOOP)){
                PLAYPATTERN=PlayPattern.SINGLELOOP;
                btModel.setGraphic(
                        imgFactory.getImgFlyweight("loop1_hover").getView());
                System.out.println("Single");
            }
            else if(PLAYPATTERN.equals(PlayPattern.SINGLELOOP)){
                PLAYPATTERN=PlayPattern.RANDOM;
                btModel.setGraphic(
                        imgFactory.getImgFlyweight("random_hover").getView());
                System.out.println("random");
            }
            else if(PLAYPATTERN.equals(PlayPattern.RANDOM)){
                PLAYPATTERN=PlayPattern.LOOP;
                btModel.setGraphic(
                        imgFactory.getImgFlyweight("loop2_hover").getView());
                System.out.println("loop");
            }
        });

        //播放器
        //mediaText2.getMarkers().put("a",Duration.seconds(5));
//        mList.add(new Media(URL3));
//        String songName=(String) mList.get(index).getMetadata().get("title")+"\n"+(String) mList.get(index).getMetadata().get("artist");
//        String songName2=(String) mList.get(index).getMetadata().get("title")+"\n"+(String) mList.get(index).getMetadata().get
//        System.out.println("---------------"+songName);

//--------------------------------------------------
//       initMusic();

//        System.out.println(lVMenu.getItems());

        //播放器音量条初始化
        sVolume.setValue(IniConfig.getI("volumeInitValue"));   //播放器音量条
        sVolume.setMaxWidth(IniConfig.getI("volumeSliderMaxWidth"));
        sVolume.setMinWidth(IniConfig.getI("volumeSliderMinWidth"));

        //播放器进度条初始化
        sPlayBack.setMaxWidth(IniConfig.getI("playbackSliderMaxWidth"));
        sPlayBack.setMinWidth(IniConfig.getI("playbackSliderMinWidth"));

        //鼠标拖拽进度条
        sPlayBack.setOnMousePressed(event -> mouse=true);
        sPlayBack.setOnMouseReleased(event->{
            mouse=false;
            try {
                mPlayer.seek(Duration.seconds(sPlayBack.getValue()));
            }catch (Exception e){
                e.printStackTrace();
            }
            //刷新时间对象
            current.refurbish(mPlayer.getCurrentTime());
            total.refurbish(mPlayer.getTotalDuration());

            TimeOfPlayBack.timeShowStart(text,current,total);
//            timeOfPlayBack.lyricsStart(mPlayer,installLyrics.getLyricText(),installLyrics.getLyrics());
            timeOfPlayBack.lyricsStart(installLyrics.getLyricText(),current,installLyrics.getLyrics());
        });


        //时钟文本初设置
        text.setText("00:00 / 00:00");
        text.setFill(Color.valueOf(IniConfig.get("colorTimeText")));

        //场景图弹出菜单 单选模式
        rMI1=new RadioMenuItem("默认皮肤");
        rMI1_opacity=new RadioMenuItem("歌词显示");
        rMI2=new RadioMenuItem("歌曲详情");
        rMI3=new RadioMenuItem("用法说明");
        ToggleGroup toggleGroup=new ToggleGroup();
        rMI1.setToggleGroup(toggleGroup);
        rMI1_opacity.setToggleGroup(toggleGroup);
        rMI2.setToggleGroup(toggleGroup);
        rMI3.setToggleGroup(toggleGroup);
        ContextMenu contextMenu=new ContextMenu();
        contextMenu.getItems().addAll(rMI1,rMI1_opacity,rMI2,rMI3);
        stage.addEventHandler(MouseEvent.MOUSE_CLICKED,  (MouseEvent  me) ->  {
            if (me.getButton() == MouseButton.SECONDARY  || me.isControlDown())  {
                contextMenu.show(stage, me.getScreenX(), me.getScreenY());
            } else  {
                contextMenu.hide();
               }
        });
        rMI1.setOnAction(event -> {
            newBackground.defaultImg();
        });


//        rMI1_opacity.setSelected(true);
//        sOpacityL.setMaxWidth(100);
//        sOpacityL.setMinWidth(100);
////            sPlayBack.setMin(0);
//        sOpacityL.setValue(40);
//        System.out.println( sOpacityL.showTickLabelsProperty());
        rMI1_opacity.setOnAction(event -> {
            if(lyricsPane){
                rMI1_opacity.setSelected(false);
                installLyrics.getLyricPane().setVisible(false);
                lyricsPane=false;

            }
            else{

                rMI1_opacity.setSelected(true);
                installLyrics.getLyricPane().setVisible(true);
                lyricsPane=true;
            }
        });

        rMI3.setOnAction(event -> {
            aboutEffect.startAbout(imgFactory);
        });

//            float opacity=0.4f;
//            ap.getChildren().add(sOpacityL);
//            sOpacityL.setLayoutX(me.getScreenX());
//            sOpacityL.setLayoutY(me.getScreenY());
//
//            sOpacityL.showTickLabelsProperty().bindBidirectional(rMI1_opacity.selectedProperty());
//
////            rMI1_opacity.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent me)->{
//
//            System.out.println(rMI1_opacity.selectedProperty());
//            System.out.println( sOpacityL.showTickLabelsProperty());
////                if (me.getButton() == MouseButton.SECONDARY  || me.isControlDown()){
////                    sOpacityL.setLayoutX(me.getScreenX());
////                    sOpacityL.setLayoutY(me.getScreenY());
////                }
////            });
////            ap.getChildren().remove(sOpacityL);
//            installLyrics.setLyricsPane(opacity);




//        ap.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
//            @Override
//            public void handle(ContextMenuEvent event) {
//                if (event.getButton() == MouseButton.SECONDARY  || event.isControlDown())
//                    contextMenu.show(ap, event.getScreenX(),event.getScreenY());
//            }
//        });




        /**
         * 上端布局
         */
        HBox hB0 = new HBox(IniConfig.getI("topBoxSpacing"));

        hB0.setMargin(bt2, new Insets(IniConfig.getI("topBoxTop"), IniConfig.getI("topBoxRight"), IniConfig.getI("topBoxBottom"), IniConfig.getI("topBoxLeft")));
        hB0.setPrefHeight(IniConfig.getI("topBoxPrefHeight"));
        hB0.setAlignment(Pos.CENTER_RIGHT);
        hB0.getChildren().addAll(btPic,btAbout,bt3R,bt3,bt2);

        hB0.setOnMousePressed(event -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        hB0.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffSet);
            stage.setY(event.getScreenY() - yOffSet);
        });

        //文件预设模块
        newBackground=new SetBackground(hB0,backgroundPic);
        fileDrag.invokeFileDrag(newBackground);
//        fileResource.invokeFileDrag(newBackground);


        /**
         * 下端布局
         */
        HBox hB = new HBox(IniConfig.getI("BottomBoxSpacing"));
        hB.setPrefHeight(IniConfig.getI("BottomBoxPrefHeight"));
        hB.setAlignment(Pos.CENTER);
        hB.getChildren().addAll(btList,bt1, bt0, bt1R, sPlayBack, text,
                btVolume,sVolume,btModel,bt2add);//label,
        //


//        label.setMaxWidth(175);       //歌曲信息
//        hB.setMargin(label,new Insets(0, hB.getSpacing(),25,label.getLayoutX()));


        /**
         * 左端布局
         */
//        VBox vB=new VBox(5);
//        //vB.setFillWidth();
//        vB.setPrefWidth(300);
//        vB.setAlignment(Pos.BOTTOM_CENTER);
        // vB.getChildren().addAll(label);
        //vB.setMargin(label,new Insets(600,20,100,20));

        /**
         * 根布局
         */
        BorderPane rootBP = new BorderPane();
        rootBP.setTop(hB0);
        rootBP.setBottom(hB);
        rootBP.setCenter(ap);
        rootBP.setBackground(new Background(new BackgroundFill(Color.color(0.5,0.5,0.5,0.1),new CornerRadii(IniConfig.getI("BorderPaneCornerRadius")),null)));//Color.valueOf("#696961")


        StackPane rootS=new StackPane();
        //栈面板设置背景图
//        rootS.setBackground(new Background(new BackgroundFill(Color.color(0.5,0.5,0.5,0.1),new CornerRadii(IniConfig.getI("BorderPaneCornerRadius")),null)));//Color.valueOf("#696961")
        rootS.getChildren().addAll(backgroundPic,rootBP);



        return rootS;
    }

    //------------------------------------------------------------------------------------------------------------------------------

    /**
     * 初始化歌曲（启动）
     */
     void initMusic() {
        try {
//            mList=database.getListSong();
            //计算下一首的index
            getPlayListIndex();
            //填装media，激活mediaplayer
            fillPlayer();
            //获取歌曲频谱
            handleAudioSpectrum();
        }catch (Exception e){
            e.printStackTrace();
        }

            //mediaplayer状态为就绪
           Platform.runLater(new Runnable() {
               @Override
               public void run() {
                   mPlayer.setOnReady(new Runnable() {
                       @Override
                       public void run() {
                           try {
                               //刷新显示信息
                               updateSongInfo();
                               //同步及调整音量大小
                               synchronizedVolume();
                               //同步及调整播放时间
                               synchronizedTime();
                           }catch (Exception e){
                               e.printStackTrace();
                           }
                       }
                   });
               }
           });
//        mPlayer.setOnMarker(new EventHandler<MediaMarkerEvent>() {
//            @Override
//            public void handle(MediaMarkerEvent event) {
//                System.out.println(event.getMarker());
//                //event.getMarker().getKey().equals()
//            }
//        });
    }

    /**
     * 初始化歌曲（启动）   重载函数
     * @param i ，被定义为播放指定index的曲目
     */
     void initMusic_Index(int i) {
        try {
//            mList=database.getListSong();
            //重载函数，得到人为交换选定的index
            getPlayListIndex(i);
            fillPlayer();
            handleAudioSpectrum();
        }catch (Exception e){
            e.printStackTrace();
        }
         Platform.runLater(new Runnable() {
             @Override
             public void run() {
                 mPlayer.setOnReady(new Runnable() {
                     @Override
                     public void run() {
                         try {
                             //刷新显示信息
                             updateSongInfo();
                             //同步及调整音量大小
                             synchronizedVolume();
                             //同步及调整播放时间
                             synchronizedTime();
                         }catch (Exception e){
                             e.printStackTrace();
                         }
                     }
                 });
             }
         });


//        mPlayer.setOnMarker(new EventHandler<MediaMarkerEvent>() {
//            @Override
//            public void handle(MediaMarkerEvent event) {
//                System.out.println(event.getMarker());
//                //event.getMarker().getKey().equals()
//            }
//        });
    }
    //计算下一首的index
    private void getPlayListIndex(){
        if (mPlayer != null) {
            mPlayer.dispose();
            System.gc();
            //控制下一首和后一首
            if (MARK==Mark.NEXT) {
                index++;
//                index = mList.indexOf(mPlayer.getMedia()) + 1;
//                System.out.println("mPlayer.getMedia(): "+mPlayer.getMedia().getSource());
                if (index >= mList.size()) {
                    index = 0;
                }
            }
            else if(MARK==Mark.LAST){
                index--;
//                index = mList.indexOf(mPlayer.getMedia()) - 1;
                if (index < 0) {
                    index = mList.size() - 1;
                }
            }
        }
        else {
            //如果此前无播放（程序刚刚初始化）则定义从第一首开始放
            index = 0;
        }
    }
    //计算下一首的index
    private void getPlayListIndex(int i){
        if(mPlayer!=null) {
            mPlayer.dispose();
            System.gc();
        }
        index=i;
    }
    //填充mediaPlayer
    private void fillPlayer()  {
        try{
            SongFile sF=mList.get(index);
            Media curMedia=sF.use();
            //填充来自Database得media
//            mPlayer.stop();
            mPlayer = new MediaPlayer(curMedia);
            //异常处理事件
            mPlayer.setOnError(()->System.out.println("media error"
                    + mPlayer.getError().toString()));
            //填充歌曲文件之后填充（匹配）歌词文件
            installLyrics.fillLyrics(sF,database.getListLyrics());
            setAutoPlay();   //recommend
            }catch (Exception e){
                e.printStackTrace();
        }
    }

    //设置自动播放（推荐）
    private void setAutoPlay(){
        mPlayer.setAutoPlay(true);
        paseMark=false;
        bt0.setGraphic(pausePic);
    }


    private void handleAudioSpectrum() throws Exception{
        //-------------------------------------------------------------------------------------------
        // mPlayer.setAudioSpectrumInterval(1.0);
        mPlayer.setAudioSpectrumListener(new AudioSpectrumListener() {
            @Override
            public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
//                ChartsSpectrumData Cs=new ChartsSpectrumData(ap);
//                Cs.buildCharts(100,300,8,4,magnitudes);
                //System.out.print(timestamp);
                //更新高度数据并绘制矩形阵频谱    showPic为false代表展示频谱，不展示海报
                chartsSpectrumData.refurbishRecChart(magnitudes,showPic);
                //更新高度数据并绘制环形阵频谱   showPic为false代表展示频谱，不展示海报
                chartsSpectrumData.refurbishCirChart(magnitudes,showPic);

//                buildCharts(startX,startY,recwidth,rectangle_num,magnitudes);
//                for(int i=0;i<rectangle_num;i++){
//                    rectangles2[i].setHeight(ChartsSpectrumData.handleMagnitudes(magnitudes[i]));
//                }
            }
        });
//-------------------------------------------------------------------------------------------
    }

    //handle Song Info
    private void updateSongInfo() throws Exception{
        //在setOnReady线程执行
//        ObservableList<Track> ttttt=mList.get(index).getTracks();
//        for(Track s:ttttt){
//            System.out.println(s);
//        }

        //获取播放文件数据
        ObservableMap<String,Object> map=mList.get(index).use().getMetadata();

        //更新播放文件数据——歌名歌手标签
        Information.info_to_Label(songTitle,map,"title",fontTitle,colorTitle);
        Information.info_to_Label(songArtist,map,"artist",fontArtist,colorArtist);
//        for(String key: map.keySet())
//            System.out.println(key+"- "+map.get(key));
        //更新播放文件数据——歌曲海报图片
        Information.info_to_Image(labelView,iVSong,map);

        //更新播放文件数据——歌曲详细页面
        Information.songInfoRepresent(rMI2,mPlayer,map,imgFactory.getImgFlyweight("stage_icon").get());

    }

    private void synchronizedTime() throws Exception{
        sPlayBack.setValue(0);
        sPlayBack.setMin(0);
        sPlayBack.setMax(mPlayer.getTotalDuration().toSeconds());
        //时间事件
        currentTimeProperty();
//                System.out.println(mPlayer.getStopTime().toMillis());

    }
    /**
     * 歌曲时间文本，歌词滚动时间控制
     */
//synchronized
    private  void currentTimeProperty() throws Exception{
        mPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            //                    ContinuousAudioDataStream
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                if(!mouse){
                    sPlayBack.setValue(newValue.toSeconds());
                    current.refurbish(mPlayer.getCurrentTime());
                    total.refurbish(mPlayer.getTotalDuration());
                    //歌曲时间文本
                    TimeOfPlayBack.timeShowStart(text,current,total);
                    //歌词滚动

                    timeOfPlayBack.lyricsStart(installLyrics.getLyricText(),current,installLyrics.getLyrics());
                }
                //歌曲结束，下一步做什么依赖此时播放模式
                double exp=10E-2;
                if(Math.abs(newValue.toSeconds()-mPlayer.getStopTime().toSeconds()) < exp ){
                    playPattern();
                }
            }
        });
    }

    //音量条监听事件
    private void synchronizedVolume() throws Exception{
        mPlayer.volumeProperty().bind(sVolume.valueProperty().divide(100));
    }


    //循环    单曲    随机播放
    private void playPattern(){
        if(PLAYPATTERN==PlayPattern.LOOP) loop();
        else if(PLAYPATTERN==PlayPattern.SINGLELOOP) singleLoop();
        else if(PLAYPATTERN==PlayPattern.RANDOM) randomPlay();
    }

    //因为数据结构暂时采用实现了Collections的数组容器，随机播放可以采用shuffle算法
    private void randomPlay(){
//      int i= (int)Math.floor(Math.random()*(mList.size()+1));
        int i=0;
        while(true) {
            i = (int) (Math.random() * mList.size());
           if(i!=index) break;

        }
//        Collections.shuffle(mList);
        //计算随机数
        initMusic_Index(i);
    }

    private void singleLoop(){
        //无限循环，当前时间的监听事件的一个参数，不以媒体结束时间为上限，可用来计算播放该音乐的总时间
        mPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mPlayer.play();
    }

    private void loop(){
        MARK=Mark.NEXT;
        initMusic();
    }

}

