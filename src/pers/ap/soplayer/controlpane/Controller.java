package pers.ap.soplayer.controlpane;
import  pers.ap.soplayer.controlpane.MySystemTray;
import javafx.stage.Stage;
import pers.ap.soplayer.database.ImgFactory;

public class Controller {
    static void  deploySystemTray(Stage stage, Player player, ImgFactory imgFactory) {
        //托盘模块
        MySystemTray.getInstance().listen(stage,player,imgFactory);
    }
}
