package pers.ap.soplayer.service.fileService;


import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
/**
 * 设置背景类
 */
public class SetBackground {
    private ImageView bg;
    private  Image defaultImage;    //默认背景图片
    protected Pane pane;

    /**
     * 构造器
     * @param pane 添加面板接口
     * @param bg 默认背景
     */
    public SetBackground(Pane pane,ImageView bg){
        this.pane=pane;
        this.bg=bg;
        this.defaultImage = bg.getImage();
    }
    /**
     * 设置新背景
     * @param im 新背景图
     */
    public void setNewImg(Image im){
        System.out.println(im);
        bg.setImage(im);
    }
    /**
     * 设置默认背景
     */
    public void defaultImg(){
        bg.setImage(defaultImage);
    }
}
