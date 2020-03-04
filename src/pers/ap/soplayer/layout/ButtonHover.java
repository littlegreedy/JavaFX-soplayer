package pers.ap.soplayer.layout;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;

public class ButtonHover implements ChangeListener<Boolean> {
    Button btn;
    /**
     * 设置伪类
     * @param btn 控件指针引用
     */
    public ButtonHover(Button btn){
        this.btn=btn;
    }
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if(newValue.booleanValue()==true){

            btn.setStyle("-fx-opacity :0.5 ");
        }
        else {

            btn.setStyle("-fx-opacity :1.0 ");
        }
    }
}