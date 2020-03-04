package pers.ap.soplayer.database;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 享元设计——图片抽象类
 */
public abstract class ImgFlyweight {
    public abstract Image get();
    public abstract ImageView getView();

    public abstract ImageView getView(double width,double height);
}

