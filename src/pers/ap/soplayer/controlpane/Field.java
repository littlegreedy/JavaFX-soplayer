package pers.ap.soplayer.controlpane;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pers.ap.soplayer.database.Database;
import pers.ap.soplayer.database.ImgFactory;
import pers.ap.soplayer.database.ImgFlyweight;
import pers.ap.soplayer.service.menu.MenuService;
import pers.ap.soplayer.toolKit.IniConfig;




public class Field extends Application {

    //屏幕宽长
    private final int PLAYERWIDTH = IniConfig.getI("PLAYERWIDTH");
    private final int PLAYERHEIGHT =IniConfig.getI("PLAYERHEIGHT");

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        // primaryStage.setScene(new Scene(root, 300, 275));
        // new core().initLayout(primaryStage);
//        Player player =new Player();
//        player.initPlayer();
        //图片工厂
        ImgFactory fF=new ImgFactory();
        MenuService menuService=new MenuService();

        //数据库类
        Database database =new Database(menuService);



        //播放类
        Player player=new Player(database,fF,menuService);
        //系统托盘
        Controller.deploySystemTray(primaryStage,player,fF);

        Scene scene = new Scene(player.theStoryBegins(primaryStage), PLAYERWIDTH, PLAYERHEIGHT);
        primaryStage.setScene(scene);
        //快捷键事件
        KeyHandler.onKey(player,scene);
        //场景透明
        scene.setFill(Color.TRANSPARENT);
        //css引入
        scene.getStylesheets().addAll(getClass().getResource("app.css").toExternalForm(),getClass().getResource("listStyles.css").toExternalForm());
//        primaryStage.setFullScreen(true);
        //枚举的透明简洁的舞台style
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        Stage secondStage=new Stage();
//        secondStage.initStyle(StageStyle.UTILITY);
//        secondStage.setOpacity(0.);
//        secondStage.show();

//        primaryStage.initOwner(secondStage);
        primaryStage.initStyle(StageStyle.UNDECORATED);

//        primaryStage.setMaxHeight(0);
//        primaryStage.setMaxWidth(0);
//        primaryStage.setX(Double.MAX_VALUE);
        primaryStage.setTitle("soplayer");
        primaryStage.setOnCloseRequest(event -> {
            primaryStage.setIconified(true);
        });

//        primaryStage.initStyle(StageStyle.TRANSPARENT);
        //primaryStage.setMaximized(false);
//        primaryStage.setResizable(false);
        ImgFlyweight stage_icon= fF.getImgFlyweight("stage_icon");
        //程序图标
        primaryStage.getIcons().add(stage_icon.get());
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
