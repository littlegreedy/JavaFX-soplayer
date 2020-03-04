package pers.ap.soplayer.toolKit;


/**
 * 配置文件获取异常类
 */
public class InitializeException extends RuntimeException{
    String initGetKey;
    public InitializeException(String initGetKey){
        this.initGetKey=initGetKey;
    }
    //重写object方法，输出异常文字信息
    public String toString(){
//        System.out.println(UnlimitedAppend.appendAll("配置文件读取异常\n请检查.ini配置文件: ",initGetKey,"部分").toString());
//        return UnlimitedAppend.appendAll("配置文件读取异常\n请检查.ini配置文件: ",initGetKey,"部分").toString();
        return null;
    }
}
