package pers.ap.soplayer.controlpane;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

public class KeyHandler {
    /**
     * 设置快捷键事件
     * @param player 播放器类，引出控件数据
     * @param scene 主舞台
     */
    static void onKey(Player player, Scene scene){
        //Ctrl+SPACE
        KeyCodeCombination kc1 = new KeyCodeCombination(KeyCode.SPACE, KeyCodeCombination.CONTROL_DOWN);
            scene.getAccelerators().put(kc1, new Runnable() {
            @Override
            public void run() {
//                System.out.println("事件");
                if (player.paseMark) {
                    player.mPlayer.play();
                    player.paseMark=false;
                    player.bt0.setGraphic(
                            player.imgFactory.getImgFlyweight("pause_hover").getView(50,50)
                    );
                } else {
                    player.mPlayer.pause();
                    player.paseMark=true;
                    player.bt0.setGraphic(
                            player.imgFactory.getImgFlyweight("pase_hover").getView(50,50)
                    );
                }
            }
        });

        //Ctrl+小键盘左箭头
        KeyCodeCombination kcL = new KeyCodeCombination(KeyCode.LEFT, KeyCodeCombination.CONTROL_DOWN);
            scene.getAccelerators().put(kcL, new Runnable() {
            @Override
            public void run() {
//                System.out.println("L事件");
                player.MARK= Player.Mark.LAST;
                player.initMusic();
            }
        });

        //Ctrl+小键盘右箭头
        KeyCodeCombination kcR = new KeyCodeCombination(KeyCode.RIGHT, KeyCodeCombination.CONTROL_DOWN);
            scene.getAccelerators().put(kcR, new Runnable() {
            @Override
            public void run() {
//                System.out.println("事件");
                player.MARK= Player.Mark.NEXT;
                player.initMusic();
            }
        });

        //Ctrl+O
        KeyCodeCombination kcO = new KeyCodeCombination(KeyCode.O, KeyCodeCombination.CONTROL_DOWN);
            scene.getAccelerators().put(kcO, new Runnable() {
            @Override
            public void run() {
//                System.out.println("事件");
                player.fileParty.invokeFileChooser(player.menuService);
            }
        });
    }
}
