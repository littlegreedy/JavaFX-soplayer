package pers.ap.soplayer.database;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 图片文件享元实例化
 */
class ImgEntity extends ImgFlyweight {
    private Image img;
    private ImageView iV;

    ImgEntity(String url) {
        this.img=new Image(url);
        iV=new ImageView(this.img);
    };

    @Override
    public Image get(){
        return img;
    }

    @Override
    public ImageView getView(){
        iV.setFitWidth(30);
        iV.setFitHeight(30);
        iV.setPreserveRatio(true);
        iV.setSmooth(true);
        return iV;
    }
    /**
     * 返回ImageView类型的对象实例
     * @param width 宽
     * @param height 高
     */
    @Override
    public ImageView getView( double width,double height){
        iV.setFitWidth(width);
        iV.setFitHeight(height);
//        iV.setPreserveRatio(true);
        iV.setSmooth(true);
        return iV;
    }

}

/**
 * 图片文件非享元实例化
 */
class ImgUnsharedEntity extends ImgFlyweight{
    private Image img;
    private ImageView iV;

    ImgUnsharedEntity(String url) {
        this.img=new Image(url);
        iV=new ImageView(this.img);
    };

    @Override
    public Image get(){
        return img;
    }

    @Override
    public ImageView getView(){
        return iV;
    }

    @Override
    public ImageView getView( double width,double height){
        iV.setFitWidth(width);
        iV.setFitHeight(height);
        return iV;
    }
}
