package gaode;


import java.io.IOException;
import java.util.Date;
import java.util.Timer;



public class GaoDe {
	
	
	//main 方法入口
    public static void main(String[] args) throws IOException {
    	
    	Timer timer = new Timer();
    	System.out.println("程序开始 现在是："+new Date().toLocaleString());
    	MyTimerTask myTimerTask = new MyTimerTask("No.1");
        timer.schedule(myTimerTask, 0, 5*60000L);
    }
	
	
	

}
