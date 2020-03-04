package pers.ap.soplayer.service.fileService;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pers.ap.soplayer.toolKit.FileHandler;

import pers.ap.soplayer.database.Database;
import pers.ap.soplayer.service.menu.MenuService;

import java.io.File;
import java.util.List;

/**
 * 歌曲歌词文件选择器添加
 */
public class FileParty {

    Database database;

    /**
     * 数据库操作权限支持
     * @param database 数据库
     */
    public FileParty(Database database) {
        this.database = database;
//        this.mList=mList;
    }

    /**
     * 通过文件选择器获取文件
     * @param menuService 操作歌曲列表菜单
     */
    public void invokeFileChooser(MenuService menuService) {
        FileChooser fC = new FileChooser();
        Stage stage = new Stage();
        fC.setTitle("歌曲添加");
        //文件选择器的过滤
        fC.getExtensionFilters().addAll(
                // new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                // new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio and Lyrics", "*.wav", "*.mp3", "*.aac","*.lrc"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("Lyrics Files", "*.lrc")
                //new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        List<File> fileList = fC.showOpenMultipleDialog(stage);

        if(fileList!=null)
            FileHandler.effect(fileList,database,menuService);      //歌词文件添加去重
//        database.listSong();

//        mList=database.getListSong();

    }



}
