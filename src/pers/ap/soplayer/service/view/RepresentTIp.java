package pers.ap.soplayer.service.view;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;

public class RepresentTIp {
    public static void instance(Node node,String describe){
        Tooltip tooltip=new Tooltip(describe);
        tooltip.setStyle("-fx-background-color: #fff00000");

        Tooltip.install(node,tooltip);
    }
}


