package pers.ap.soplayer.controlpane;
import java.util.concurrent.TimeUnit;

/**
 * 测试线程的区域
 */
 class RunThread extends Thread{
    volatile private boolean isRunning = true;
    public boolean isRunning(){
        return isRunning;
    }

    public void setRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        System.out.println("进入run...");
        while(isRunning==true){}
        System.out.println("线程停止了");
    }
}

class Run {
    public static void main(String[] args){
        try{
            RunThread thread = new RunThread();
            thread.start();
            TimeUnit.MILLISECONDS.sleep(1000);
            thread.setRunning(false);
            System.out.println("赋值为false");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}