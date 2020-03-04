package pers.ap.soplayer.toolKit;
/**
 * 处理文件名，获得去掉拓展名的文件名字
 */
public class SmallHandleFile {
    /**
     * @param fName 文件路径
     * @return 资源路径、去掉拓展名的文件名字
     */
    public static String getFileName(String fName){
       return fName.substring(fName.lastIndexOf("/")+1,fName.lastIndexOf("."));

//        fileName = fName.substring(fName.lastIndexOf("\\")+1);

//        System.out.println("方法二：fileName = " + fileName);

    }
    /**
     * @param fName 文件名字
     * @return 去掉拓展名的文件名字
     */
    public static String toFileName(String fName){
        return fName.substring(0,fName.lastIndexOf("."));
    }
}
