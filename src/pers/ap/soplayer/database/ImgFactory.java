package pers.ap.soplayer.database;


import pers.ap.soplayer.database.ImgFlyweight;

import java.util.HashMap;

/**
 * 简单工厂
 */
public class ImgFactory {
    private HashMap<String, ImgEntity> flyweights=new HashMap<String, ImgEntity>();


    /**
     * 工厂初始化
     */
    public ImgFactory(){
        //绝对路径 请修改否则错

        String imFill=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/fill-hover.png").toExternalForm();
        String imClose=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/close-hover.png").toExternalForm();
        String imMin=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/min-hover.png").toExternalForm();
        String imAbout=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/about-hover.png").toExternalForm();
        String imPase=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/pase-hover.png").toExternalForm();
        String imPause=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/pause-hover.png").toExternalForm();
        String imNext=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/next-hover.png").toExternalForm();
        String imLast=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/last-hover.png").toExternalForm();
        String imVolume = this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_img/volume.png").toExternalForm();
        String imPListR = this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_img/current-music.png").toExternalForm();
        String imPList= this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_img/music_list.png").toExternalForm();
        String imBackground= this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_other/bg.jpg").toExternalForm();
        String imAdd=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/add-hover.png").toExternalForm();
        String imRandom=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/play-random-hover.png").toExternalForm();
        String imLoop2=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/loop-hover.png").toExternalForm();
        String imLoop1=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/loop-one-hover.png").toExternalForm();
        String imVolumeless=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/volumeless-hover.png").toExternalForm();
        String  imPicture=this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_hover/picture-hover.png").toExternalForm();


        flyweights.put("stage_icon",new ImgEntity(
                this.getClass().getClassLoader().getResource("pers/ap/soplayer/img/icon_other/stage_icon.png").toExternalForm()));
        flyweights.put("fill_hover",new ImgEntity(imFill));
        flyweights.put("close_hover",new ImgEntity(imClose));
        flyweights.put("min_hover",new ImgEntity(imMin));
        flyweights.put("about_hover",new ImgEntity(imAbout));
        flyweights.put("pase_hover",new ImgEntity(imPase));
        flyweights.put("pause_hover",new ImgEntity(imPause));
        flyweights.put("next_hover",new ImgEntity(imNext));
        flyweights.put("last_hover",new ImgEntity(imLast));
        flyweights.put("volume_hover",new ImgEntity(imVolume));
        flyweights.put("volumeless_hover",new ImgEntity(imVolumeless));
        flyweights.put("pList_hover",new ImgEntity(imPList));
        flyweights.put("pListR_hover",new ImgEntity(imPListR));
        flyweights.put("background_img",new ImgEntity(imBackground));
        flyweights.put("random_hover",new ImgEntity(imRandom));
        flyweights.put("add_hover",new ImgEntity(imAdd));
        flyweights.put("loop2_hover",new ImgEntity(imLoop2));
        flyweights.put("loop1_hover",new ImgEntity(imLoop1));
        flyweights.put("picture_hover",new ImgEntity(imPicture));


    }

    /**
     * 工厂输出实例
     * @param key 客户请求key
     */

    public ImgFlyweight getImgFlyweight(String key){
        return (ImgFlyweight)flyweights.get(key);
    }
}
