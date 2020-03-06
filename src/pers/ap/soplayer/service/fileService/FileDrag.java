package pers.ap.soplayer.service.fileService;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import pers.ap.soplayer.toolKit.FileHandler;
import pers.ap.soplayer.database.Database;
import pers.ap.soplayer.service.menu.MenuService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 文件推拽模块类
 * 仅支持获取需要的文件，其余处理交由模块处理类处理
 */
public class FileDrag {

    private Database database;
    /**
     * 构造器 获取数据库操作权限
     * @param database 数据库
     */
    public FileDrag(Database database) {
        this.database = database;
    }

    /**
     * 歌曲歌词文件拖动添加
     * @param pane 支持的面板区域
     * @param menuService 操作歌曲列表菜单
     */
    public void invokeFileDrag(Pane pane, MenuService menuService){

        pane.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //提供视觉效果 下同
                pane.setBorder(new Border(new BorderStroke(Paint.valueOf("#FFF0F5"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1))));
            }
        });

        pane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                pane.setBorder(new Border(new BorderStroke(Paint.valueOf("#FFF0F500"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1))));
            }
        });

        pane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.COPY);
            }
        });

        pane.setOnDragDropped(new EventHandler<DragEvent>(){
            @Override
            public void handle(DragEvent event) {
                pane.setBorder(new Border(new BorderStroke(Paint.valueOf("#FFF0F500"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1))));
//                event.acceptTransferModes(TransferMode.COPY);
                //拖拽板
                Dragboard db=event.getDragboard();
                if(db.hasFiles()) {
//                    System.out.println("666");
                    List<File> rawFiles = db.getFiles();
//                    List<File> files = filter(rawFiles);
//                    FileHandler fileHandler=new FileHandler();
                    FileHandler.effect(rawFiles,database,menuService);    //过滤+去重
                }

            }
//        return null;
        });
    }

    /**
     * 背景图片文件拖动添加
     * @param setClass 背景设置操作类
     */
    public void invokeFileDrag(SetBackground setClass){
        setClass.pane.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                setClass.pane.setBorder(new Border(new BorderStroke(Paint.valueOf("#FFF0F5"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1))));
            }
        });

        setClass.pane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                setClass.pane.setBorder(new Border(new BorderStroke(Paint.valueOf("#FFF0F500"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(1))));
            }
        });

        setClass.pane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(event.getTransferMode());
            }
        });

        setClass.pane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                setClass.pane.setBorder(new Border(new BorderStroke(Paint.valueOf("#FFF0F500"), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2))));
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
//                    db.getContent(DataFormat.IMAGE);
//                    Image image=(Image)O;
                    List<File> files= db.getFiles();
                    try {
                        //逐一读取文件
                        for (File f : files) {
                            InputStream is = new FileInputStream(f);
                            BufferedImage bi = ImageIO.read(is);
                            Image image = SwingFXUtils.toFXImage(bi, null);
                            setClass.setNewImg(image);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
