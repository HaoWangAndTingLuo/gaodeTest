package gaode;


import java.io.IOException;
import java.util.Date;
import java.util.Timer;



public class GaoDe {
	
	
	//main �������
    public static void main(String[] args) throws IOException {
    	
    	Timer timer = new Timer();
    	System.out.println("����ʼ �����ǣ�"+new Date().toLocaleString());
    	MyTimerTask myTimerTask = new MyTimerTask("No.1");
        timer.schedule(myTimerTask, 0, 5*60000L);
    }
	
	
	

}
