package pers.ap.soplayer.service.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import pers.ap.soplayer.database.SongFile;
import pers.ap.soplayer.toolKit.IniConfig;

import java.util.ArrayList;
/**
 * 歌曲菜单维护类
 */
public class MenuService {

    private String[] songNameList={"----------------------------歌单-------------------------------"};
    ObservableList<String> items= FXCollections.observableArrayList(songNameList);
    public ListView<String> lVMenu = new ListView<String>(items);
//    private ObservableList<String> items;
    /**
     * 生成listView
     * @param pane 菜单面板
     */
    public void initMenu(VBox pane){
        lVMenu.setPrefSize(IniConfig.getI("listViewMenuPrefWidth"), IniConfig.getI("listViewMenuPrefHeight"));
        lVMenu.setStyle("-fx-text-alignment: center");
        this.menuSize(pane);
        lVMenu.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        pane.getChildren().add(lVMenu);
    }

    //菜单实时大小
    public void menuSize(VBox pane){
        lVMenu.setPrefSize(pane.getWidth(),pane.getPrefHeight());
    }

    /**
     * 对列表的添加操作
     * @param nameStr 文件名
     */
    public void add_to_list(String nameStr){
        items.add(nameStr);
//        lVMenu.refresh();
//        try {
//            for (int i = 0; i < mList.size(); i++) {
//                items.add(mList.get(i).getMetadata().get("title") + "\n" + (String) mList.get(i).getMetadata().get("artist").toString());
////            ObservableMap<String, Object> mList_metadata = mList.get(i).getMetadata();
////            System.out.println(mList_metadata);
////                System.out.println(songNameList[i]);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
