package pers.ap.soplayer.service.view;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import pers.ap.soplayer.database.ImgFactory;

import java.util.ArrayList;



/**
 * 轮播图
 * author: Cap
 *
 */

public class AboutEffect {

    private  double index0_x = 0;
    private  double index1_x = 0;
    private  double index2_x = 0;
    private  double  img_y=0;

    private  double img_z=0;

    private ImageView left_button;
    private ImageView right_button;

    ArrayList<ImageView> image_list;

    Duration time=Duration.seconds(0.5);

    double time_to_double=0.25;


    public void startAbout(ImgFactory imgFactory){
        Pane pane=getView(1200,750,imgFactory);
//        AnchorPane ap=new AnchorPane();
        HBox ap=new HBox();
        ap.setAlignment(Pos.CENTER);
        //ap.setPrefSize(700,360);
        ap.getChildren().add(pane);
//         ap.setBackground(new Background(new BackgroundFill(Color.BLUE,null,null)));
//        AnchorPane.setTopAnchor(pane,100.0);
//        //System.out.println(pane.getLayoutX());
//        AnchorPane.setLeftAnchor(pane,100.0);
        Stage stage=new Stage();
        Scene scene=new Scene(ap,1200,750);
        stage.setAlwaysOnTop(true);
//        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.setTitle("关于-程序信息");
        stage.getIcons().add(new Image("https://cdn.jsdelivr.net/gh/littlegreedy/littlegreedy.github.io@v1.30/img/head.jpg"));
        stage.show();

        left_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                left_to_right(image_list);
            }
        });

        right_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                right_to_left(image_list);
            }
        });

    }

    public Pane getView(int w,int h,ImgFactory imgFactory){

        left_button=new ImageView("file:D:/JavaLab/BackDark.png");
        right_button=new ImageView("file:D:/JavaLab/ForwardDark.png");
        left_button.setFitHeight(80);
        left_button.setFitWidth(80);
        right_button.setFitWidth(80);
        right_button.setFitHeight(80);
//        System.out.println(left_button.prefHeight(-1));

        HBox hb=new HBox(w-2*left_button.prefWidth(-1));
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(left_button,right_button);

        //------------------------------------------


//        ImageView iV1 = new ImageView(this.getClass().getClassLoader().getResource("C144.png").toExternalForm());
        ImageView iV1 = new ImageView( imgFactory.getImgFlyweight("usage2").get());
        iV1.setPreserveRatio(true);
        iV1.setFitWidth(w / 1.8);
        ImageView iV2 = new ImageView( imgFactory.getImgFlyweight("usage1").get());
        iV2.setPreserveRatio(true);
        iV2.setFitWidth(w / 1.8);
        ImageView iV3 = new ImageView( imgFactory.getImgFlyweight("usage3").get());
        iV3.setPreserveRatio(true);
        iV3.setFitWidth(w / 1.8);
//        iV3.setOnMouseEntered(event -> {
//            iV3.setPreserveRatio(false);
//            iV3.setFitHeight(iV3.getFitHeight()*2);
//            iV3.setFitWidth(iV3.getFitWidth()*2);
//        });


        img_z=60;

        index0_x=0-img_z;
        index1_x=w/2-iV2.getFitWidth()/2;
        index2_x=w-iV3.getFitWidth()+img_z;

        img_y=h/2-iV1.prefHeight(-1)/2;



        iV1.setTranslateX(index0_x);
        iV2.setTranslateX(index1_x);
        iV3.setTranslateX(index2_x);

        iV1.setTranslateY(img_y);
        iV2.setTranslateY(img_y);
        iV3.setTranslateY(img_y);

        iV1.setTranslateZ(img_z);
        iV2.setTranslateZ(0);
        iV3.setTranslateZ(img_z);


        image_list=new ArrayList<ImageView>();
        image_list.add(iV1);
        image_list.add(iV2);
        image_list.add(iV3);

        AnchorPane ap=new AnchorPane();
        ap.getChildren().addAll(iV1,iV2,iV3);
        SubScene subScene=new SubScene(ap,w,h, true,SceneAntialiasing.BALANCED);
        PerspectiveCamera camera=new PerspectiveCamera();
        subScene.setCamera(camera);

        StackPane sPane=new StackPane();

        //sPane.setStyle("-fx-background-color:#FFA07A");
        sPane.getChildren().addAll(subScene,hb);

        return sPane;
    }


    public void right_to_left(ArrayList<ImageView> image_list){
        ImageView iV0=image_list.get(0);

        ImageView iV1=image_list.get(1);

        ImageView iV2=image_list.get(2);


        left_to_middle_Animation(iV0);

        middle_to_right_Animation(iV1);
        right_to_left_Animation(iV2);

        image_list.clear();

        image_list.add(iV2);
        image_list.add(iV0);
        image_list.add(iV1);

    }

    public void left_to_right(ArrayList<ImageView> image_list){
        ImageView iV0=image_list.get(0);

        ImageView iV1=image_list.get(1);

        ImageView iV2=image_list.get(2);

        right_to_middle_Animation(iV2);
        middle_to_left_Animation(iV1);
        left_to_right_Animation(iV0);

        image_list.clear();

        image_list.add(iV1);
        image_list.add(iV2);
        image_list.add(iV0);
    }

    public void left_to_middle_Animation(ImageView iV){
        TranslateTransition tt=new TranslateTransition();
        tt.setDuration(time);
        tt.setNode(iV);

        tt.setFromX(index0_x);
        tt.setFromZ(img_z);

        tt.setToX(index1_x);
        tt.setToZ(0);

        tt.play();
    }

    public void middle_to_right_Animation(ImageView iV){
        TranslateTransition tt=new TranslateTransition();
        tt.setDuration(time);

        tt.setFromX(index1_x);
        tt.setFromZ(0);

        tt.setToX(index2_x);
        tt.setToZ(img_z);

        FadeTransition ft1=new FadeTransition(Duration.seconds(time_to_double));
        ft1.setFromValue(1);
        ft1.setToValue(0.8);
        FadeTransition ft2=new FadeTransition(Duration.seconds(time_to_double));
        ft2.setFromValue(0.8);
        ft2.setToValue(1);

        SequentialTransition st=new SequentialTransition();
        st.getChildren().addAll(ft1,ft2);

        ParallelTransition pt=new ParallelTransition();
        pt.setNode(iV);
        pt.getChildren().addAll(tt,st);

        pt.play();
    }

    public void right_to_left_Animation(ImageView iV){
        TranslateTransition tt=new TranslateTransition();
        tt.setDuration(time);
        tt.setNode(iV);

        tt.setFromX(index2_x);
        tt.setFromZ(img_z);

        tt.setToX(index0_x);
        tt.setToZ(img_z);

        tt.play();
    }

    public void right_to_middle_Animation(ImageView iV){
        TranslateTransition tt=new TranslateTransition();
        tt.setDuration(time);
        tt.setNode(iV);

        tt.setFromX(index2_x);
        tt.setFromZ(img_z);

        tt.setToX(index1_x);
        tt.setToZ(0);

        tt.play();
    }

    public void  middle_to_left_Animation(ImageView iV){
        TranslateTransition tt=new TranslateTransition();
        tt.setDuration(time);

        tt.setFromX(index1_x);
        tt.setFromZ(0);

        tt.setToX(index0_x);
        tt.setToZ(img_z);

        FadeTransition ft1=new FadeTransition(Duration.seconds(time_to_double));
        ft1.setFromValue(1);
        ft1.setToValue(0.8);
        FadeTransition ft2=new FadeTransition(Duration.seconds(time_to_double));
        ft2.setFromValue(0.8);
        ft2.setToValue(1);

        SequentialTransition st=new SequentialTransition();
        st.getChildren().addAll(ft1,ft2);

        ParallelTransition pt=new ParallelTransition();
        pt.setNode(iV);
        pt.getChildren().addAll(tt,st);

        pt.play();
    }

    public void left_to_right_Animation(ImageView iV){
        TranslateTransition tt=new TranslateTransition();
        tt.setDuration(time);
        tt.setNode(iV);

        tt.setFromX(index0_x);
        tt.setFromZ(img_z);

        tt.setToX(index2_x);
        tt.setToZ(img_z);

        tt.play();
    }
}
