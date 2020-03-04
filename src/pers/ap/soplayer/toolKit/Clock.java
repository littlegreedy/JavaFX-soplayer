package pers.ap.soplayer.toolKit;

import javafx.util.Duration;

import java.text.DecimalFormat;

/**
 * 时钟类
 */
public class Clock{
    private Duration time=null;

    public Clock(){

    }
    public Clock(Duration time){
        this.time=time;
    }

    public void refurbish(Duration time){
        this.time=time;
    }

    public int getMinutes(){
        return double_to_int(time.toMinutes());
    }

    public int getSeconds(){
        return double_to_int(time.toSeconds()%60);
    }

    public int getMillis(){
        return double_to_int(time.toMillis()%100);
    }

    public long getTotalMillis() {  return double_to_long(time.toMillis());  }

    public int double_to_int(double tmp){
        return (int)tmp;
    }

    public long double_to_long(double tmp){ return (long)tmp; }

    public static String toFormat(int t){    //只初始化一次
        DecimalFormat df=new DecimalFormat("00");
        return df.format(t);
    }

}
