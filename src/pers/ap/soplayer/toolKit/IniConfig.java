package pers.ap.soplayer.toolKit;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
/**
 *配置类  .ini文件
 */
public class IniConfig {

    static String fileString=null;
    static{
         fileString= "config.ini";
    }
    public  IniConfig()
    {
    }

    /**
     * 获得配置的数据
     *@param key 配置名目
     * @return 字符串格式
     */
    public static String get (String key) throws InitializeException{
        Properties ini = null;
        File file=new File(fileString);
        try
        {
            ini = new Properties ();
            ini.load (new FileInputStream(file));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        if(!ini.containsKey (key))
        {
            throw  new InitializeException(key);
        }
        return ini.get(key).toString();
    }
    /**
     * 获得配置的数据
     *@param key 配置名目
     * @return 整型格式
     */
    public static int getI (String key) throws InitializeException{
        Properties ini = null;
        File file=new File(fileString);
        try
        {
            ini = new Properties ();
            ini.load (new FileInputStream(file));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        if(!ini.containsKey (key))
        {
            throw  new InitializeException(key);
        }
        return Integer.parseInt(ini.get(key).toString());
    }
    /**
     * 获得配置的数据
     *@param key 配置名目
     * @return 浮点双精度格式
     */
    public static double getD (String key) throws InitializeException{
        Properties ini = null;
        File file=new File(fileString);
        try
        {
            ini = new Properties ();
            ini.load (new FileInputStream(file));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        if(!ini.containsKey (key))
        {
            throw  new InitializeException(key);
        }
        return Double.parseDouble(ini.get(key).toString());
    }

}

