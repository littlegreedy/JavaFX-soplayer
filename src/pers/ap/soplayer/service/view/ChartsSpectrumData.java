package pers.ap.soplayer.service.view;

import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import pers.ap.soplayer.toolKit.IniConfig;
/**
 * 频谱图表创建和维护
 */
public class ChartsSpectrumData {
    private  double width;
    private int rectangle_num;
    private  Pane chartP=new Pane();
    private  Pane chartP2=new Pane();
    private Rectangle[] rectangles=new Rectangle[IniConfig.getI("rectangleLimitNum")];
    private Rectangle[] rectangles2=new Rectangle[IniConfig.getI("rectangleLimitNum")];

    /**
     * 构造
     * @param width 矩形本身宽
     * @param rectangle_num 显示矩形数量
     */
    public ChartsSpectrumData( double width, int rectangle_num) {
        this.width = width;
        this.rectangle_num = rectangle_num;
    }
    /**
     * 计算每个矩形之间的偏移量
     * @param width 自身宽
     * @return double 返回偏移量
     */
    public static double getShift(double width){
        return width*1.6;
    }

    public static double handleMagnitudes(float value){
        float f=Math.abs(value);
        float change=100-(100/(60/f));
        return (change)*2+4;
    }

    /**
     * 刷新线性图表
     * @param magnitudes 频谱
     * @param showPic 代表海报是否显示
     */
    public void refurbishRecChart(float[] magnitudes,boolean showPic){
        //更新高度数据并绘制矩形阵频谱
        //仅做一次判断
        if(showPic) {
            for (int i = 0; i < rectangle_num; i++) {
//            rectangles[i].setX(startX+i* ChartsSpectrumData.getShift(width));
//            rectangles[i].setY(startY);
//                rectangles[i].setWidth(width);
                rectangles[i].setHeight(0);
//                rectangles[i].setRotate(-180);

            }
            return;
        }
        for(int i=0;i<rectangle_num;i++) {
//            rectangles[i].setX(startX+i* ChartsSpectrumData.getShift(width));
//            rectangles[i].setY(startY);
            rectangles[i].setWidth(width);
            rectangles[i].setHeight(ChartsSpectrumData.handleMagnitudes(magnitudes[i])*IniConfig.getD("heightRatio"));
            rectangles[i].setRotate(-180);
//            rectangles[i].setStyle("-fx-border-color: linear-gradient(to right,#00fffc,#fff600) ");
//            if(magnitudes[i]>-40) {
//                Stop[] stops = new Stop[] { new Stop(0,Color.YELLOW ), new Stop(1, Color.web("#00b5e5"))};
//                LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,stops);//-rectangles[i].getY()*0.3
//                rectangles[i].setFill(lg1);
//                rectangles[i].setEffect(designEffect(rectangles[i].getX(), rectangles[i].getY(), rectangles[i].getHeight(), rectangles[i].getWidth(),Color.YELLOW));
//            }
//           System.out.print(rectangles[i].getLayoutY()+" ");
        }
//        System.out.println();
        //System.out.println(magnitudes[0]+" "+ChartsSpectrumData.handleMagnitudes(magnitudes[0]));
    }

    /**
     * 刷新环形图表
     * @param magnitudes 频谱
     * @param showPic 代表海报是否显示
     */
    public  void refurbishCirChart(float[] magnitudes,boolean showPic){
        //更新高度数据并绘制环形阵频谱   showPic为false代表展示频谱，不展示海报
        //一层if
        if(showPic) {
            for (int i = 0; i < rectangle_num; i++) {
                rectangles2[i].setHeight(0);
            }
            return;
        }
        for(int i=0;i<rectangle_num;i++){
            rectangles2[i].setHeight(ChartsSpectrumData.handleMagnitudes(magnitudes[i])*IniConfig.getD("heightRatio"));
        }
    }
    /**
     * 绘图特效 ColorInput
     * 用法如rectangles[i].setEffect(designEffect(rectangles[i].getX(), rectangles[i].getY(), rectangles[i].getHeight(), rectangles[i].getWidth(),Color.YELLOW));
     * @param x 位置x
     * @param y 位置y
     * @param h 高度
     * @param w 宽度
     * @param color Paint类对象
     * @return 返回对象Effect
     */
    public Effect designEffect(double x, double y, double h, double w, Paint color){
        ColorInput c=new ColorInput();
        c.setX(x);
        c.setY(y);
        c.setHeight(h);
        c.setWidth(w);
        c.setPaint(color);

        return c;
    }

    /**
     * 图片初始化
     * @param ap 显示位置 面板
     */
    public void chartConfig(AnchorPane ap){

        //线性图表
        double startX= IniConfig.getI("startX");
        double startY=IniConfig.getI("startY");
        for(int i=0;i<rectangle_num;i++) {
            rectangles[i] = new Rectangle(startX + i * ChartsSpectrumData.getShift(width), startY, width, IniConfig.getD("chartPPrefHeight"));
//            rectangles[i].setX(startX+ i * ChartsSpectrumData.getShift(width));
//            rectangles[i].setY(startY);
//            rectangles[i].setWidth(width);
//            rectangles[i].setHeight(0);
            rectangles[i].setRotate(90);
            rectangles[i].setFill(Color.web(IniConfig.get("colorChartP"),IniConfig.getD("opacityChartP")));
            chartP.getChildren().add(rectangles[i]);
        }

        //环形图表
        double r=IniConfig.getI("chartP2Radius");
        double shiftX=IniConfig.getI("chartP2shiftX");
        double shiftY=IniConfig.getI("chartP2shiftY");
        double changeAngle =(double)360/rectangle_num;
        for(int i=0;i<rectangle_num;i++) {
            rectangles2[i] = new Rectangle(shiftX + r * Math.cos(Math.PI * 2 * i / rectangle_num),
                    shiftY + r * Math.sin(Math.PI * 2 * i / rectangle_num), width, IniConfig.getD("chartP2PrefHeight"));
//            rectangles[i].setX(shift + r * Math.cos(Math.PI * 2 * i / rectangle_num));
//            rectangles[i].setY(shift + r * Math.sin(Math.PI * 2 * i / rectangle_num));
//            rectangles[i].setWidth(width);
//            rectangles[i].setHeight(20);
            rectangles2[i].setFill(Color.web(IniConfig.get("colorChartP2"),IniConfig.getD("opacityChartP2")));
            //System.out.println(changeAngle);
            rectangles2[i].setRotate(90 + changeAngle * i);
            chartP2.getChildren().add(rectangles2[i]);
        }

        //两图表的位置设定
        ap.getChildren().addAll(chartP,chartP2);
        AnchorPane.setTopAnchor(chartP, IniConfig.getD("chartPTop"));
        AnchorPane.setLeftAnchor(chartP,IniConfig.getD("chartPLeft"));
        AnchorPane.setBottomAnchor(chartP,IniConfig.getD("chartPBottom"));
        AnchorPane.setTopAnchor(chartP2,IniConfig.getD("chartP2Top"));
        AnchorPane.setLeftAnchor(chartP2,IniConfig.getD("chartP2Left"));
        AnchorPane.setBottomAnchor(chartP2,IniConfig.getD("chartP2Bottom"));
    }
}
