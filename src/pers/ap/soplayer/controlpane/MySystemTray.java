package pers.ap.soplayer.controlpane;



import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pers.ap.soplayer.database.ImgFactory;
import pers.ap.soplayer.layout.MyTrayIcon;
import pers.ap.soplayer.toolKit.IniConfig;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPopupMenuUI;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Objects;

/**
 * 自定义系统托盘(单例模式)
 */
public class MySystemTray {

    private static MySystemTray instance;
    private static JMenuItem playItem;
    private static JMenuItem lastItem;
    private static JMenuItem nextItem;
    private static JMenuItem showItem;
    private static JMenuItem exitItem;
    private static MyTrayIcon trayIcon;
    private static ActionListener playListener;
    private static ActionListener lastListener;
    private static ActionListener nextListener;
    private static ActionListener showListener;
    private static ActionListener exitListener;
    private static MouseListener mouseListener;
//    private Logger logger = LoggerFactory.getLogger(MySystemTray.class);

    /**
     * 静态块
     */
    static{
        //执行stage.close()方法,窗口不直接退出
        Platform.setImplicitExit(false);
        //菜单项(打开)中文乱码的问题是编译器的锅,如果使用IDEA,需要在Run-Edit Configuration在LoginApplication中的VM Options中添加-Dfile.encoding=GBK
        //如果使用Eclipse,需要右键Run as-选择Run Configuration,在第二栏Arguments选项中的VM Options中添加-Dfile.encoding=GBK
        playItem=new JMenuItem("播放/暂停");
        lastItem=new JMenuItem("上一曲");
        nextItem=new JMenuItem("下一曲");
        showItem = new JMenuItem("显示");
        //菜单项(退出)
        exitItem = new JMenuItem("退出");


//        trayIcon = new TrayIcon(image);
        //初始化监听事件(空)
//        actionEvent = e -> {};
        playListener = e -> {};
        nextListener = e -> {};
        lastListener = e -> {};
        showListener = e -> Platform.runLater(() -> {});
        exitListener = e -> {};
        mouseListener = new MouseAdapter() {};
    }

    //get实例化
    public static MySystemTray getInstance(){
        if(instance == null){
            instance = new MySystemTray();
        }
        return instance;
    }

    /**
     * 设置
     */
    private MySystemTray(){
        try {
            //检查系统是否支持托盘
            if (!SystemTray.isSupported()) {
                //系统托盘不支持
//                logger.info(Thread.currentThread().getStackTrace()[ 1 ].getClassName() + ":系统托盘不支持");
                return;
            }

            //系统托盘
            SystemTray tray = SystemTray.getSystemTray();
            //弹出式菜单组件
            final JPopupMenu popup = new JPopupMenu();
            //为JPopupMenu设置UI
            popup.setUI(new BasicPopupMenuUI(){
                @Override
                public void paint(Graphics g, JComponent c){
                    super.paint(g, c);

                    //画弹出菜单左侧的灰色背景
                    g.setColor(new Color(236,237,238));
                    g.fillRect(0, 0, 25, c.getHeight());

                    //画弹出菜单右侧的白色背景
                    g.setColor(new Color(255,255,255));
                    g.fillRect(25, 0, c.getWidth()-25, c.getHeight());
                }
            });
            popup.add(playItem);
            popup.add(lastItem);
            popup.add(nextItem);
            popup.add(showItem);
            popup.add(exitItem);


            //此处不能选择ico格式的图片,要使用16*16的png格式的图片
            URL url = MySystemTray.class.getResource("/pers/ap/soplayer/layout/iconOfTray.png");
            //系统托盘图标
            Image image = Toolkit.getDefaultToolkit().getImage(url);
            //将TrayIcon添加到系统托盘
            trayIcon=new MyTrayIcon(image,"soplayer",popup);
            //设置图标尺寸自动适应
            trayIcon.setImageAutoSize(true);
//            popup.addActionListener(actionEvent);
            //鼠标移到系统托盘,会显示提示文本
//            trayIcon.setToolTip("SOPLAYER");
            tray.add(trayIcon);
        } catch (Exception e) {
            //系统托盘添加失败
            e.printStackTrace();
//            logger.error(Thread.currentThread().getStackTrace()[ 1 ].getClassName() + ":系统添加失败", e);
        }
    }

    /**
     * 更改系统托盘所监听的Stage
     * @param stage 舞台
     */
    public void listen(Stage stage, Player player, ImgFactory imgFactory){
        //防止报空指针异常
        if(showListener == null || exitListener == null || mouseListener == null || showItem == null || exitItem == null || trayIcon == null){
            return;
        }
        //移除原来的事件
        playItem.removeActionListener(playListener);
        lastItem.removeActionListener(lastListener);
        nextItem.removeActionListener(nextListener);
        showItem.removeActionListener(showListener);
        exitItem.removeActionListener(exitListener);
        trayIcon.removeMouseListener(mouseListener);



        //行为事件: 点击"播放/暂停"按钮，播放暂停    含有bug
        playListener=e -> {
            if (player.paseMark) {
                player.mPlayer.play();
                player.paseMark=false;

//                player.bt0.setGraphic(
//                        imgFactory.getImgFlyweight("pause_hover").getView(IniConfig.getI("largeWidth"),IniConfig.getI("largeHeight"))
//                );

            } else {
                player.mPlayer.pause();
                player.paseMark=true;
//                player.bt0.setGraphic(

//                        imgFactory.getImgFlyweight("pase_hover").getView(IniConfig.getI("largeWidth"),IniConfig.getI("largeHeight"))
//                );
            }

        };

        //行为事件: 点击"上一曲
        lastListener=e->{
            player.MARK= Player.Mark.LAST;
            player.initMusic();
        };
        //行为事件: 点击"下一曲"按钮
        nextListener=e -> {
            player.MARK= Player.Mark.NEXT;
            player.initMusic();
        };

        //行为事件: 点击"打开"按钮,显示窗口
        showListener = e -> Platform.runLater(() -> showStage(stage));
        //行为事件: 点击"退出"按钮, 就退出系统
        exitListener = e -> {
            System.exit(0);
        };


//        actionEvent=new ActionListener(){
//             public void actionPerformed(java.awt.event.ActionEvent e) {
////            java.awt.JMenuItem item = (java.awt.JMenuItem) e.getSource();
////            Platform.setImplicitExit(false); //多次使用显示和隐藏设置false
//            JMenuItem item=(JMenuItem)e.getSource();
//
//            if (item==exitItem) {
//                SystemTray.getSystemTray().remove(trayIcon);
//                Platform.exit();
//                return;
//            }
//            if (item==showItem) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        stage.show();
//                    }
//                });
//            }
//            if (item.getLabel().equals("最小化")) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        stage.hide();
//                    }
//                });
//            }
//
//        }
//        };

        //鼠标行为事件: 单机显示stage
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //鼠标左键
                if (e.getButton() == MouseEvent.BUTTON1) {
                    showStage(stage);
                }
            }
        };
        //给菜单项添加事件
        playItem.addActionListener(playListener);
        lastItem.addActionListener(lastListener);
        nextItem.addActionListener(nextListener);
        showItem.addActionListener(showListener);
        exitItem.addActionListener(exitListener);


        //给系统托盘添加鼠标响应事件
        trayIcon.addMouseListener(mouseListener);
    }

    /**
     * 关闭窗口
     * @param stage 舞台
     */
    public void hide(Stage stage){
        Platform.runLater(() -> {
            //如果支持系统托盘,就隐藏到托盘,不支持就直接退出
            if (SystemTray.isSupported()) {
                //stage.hide()与stage.close()等价
                stage.hide();
            } else {
                System.exit(0);
            }
        });
    }

    /**
     * 点击系统托盘,显示界面(并且显示在最前面,将最小化的状态设为false)
     * @param stage
     */
    private void showStage(Stage stage){
        //点击系统托盘,
        Platform.runLater(() -> {
            if(stage.isIconified()){ stage.setIconified(false);}
            if(!stage.isShowing()){ stage.show(); }
            stage.toFront();
        });
    }
}