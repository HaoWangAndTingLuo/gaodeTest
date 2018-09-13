package gaode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import PO.Road;

import PO.TrafficRoad;

public class MyTimerTask extends TimerTask{
	private static final String MyKey="your key";
	private String name;
	private Road road;
	private List<Road> roads;
	private final String pathname="D:\\trafficStatus\\";

	private BufferedWriter out;
	
    public MyTimerTask(String inputName){
    	this.name=inputName;
    	roads=new ArrayList<Road>();
    	Double startLong=113.994386d;
    	Double startLati=30.723296d;
    	Double endLong=114.244571d;
    	Double endLati=30.525338d;
    	Double longInterval=0.06;
    	Double latiInterval=0.06;
    	Double longJian=endLong-startLong;
    	Double latiJian=startLati-endLati;
    	int longTimes=(int)(longJian/longInterval+1);
    	int latiTimes=(int)(latiJian/latiInterval+1);
    	int k=0;
    	for(int i=0;i<longTimes;i++) {
    		Double thisStartLong=startLong+i*longInterval;
    		Double thisEndLong;
    		if(i!=(longTimes-1))thisEndLong=thisStartLong+longInterval;
    		else thisEndLong=endLong;
    		for(int j=0;j<latiTimes;j++) {
    			Double thisStartLati=startLati-j*latiInterval;
    			Double thisEndLati;
    			if(j!=(latiTimes-1))thisEndLati=thisStartLati-latiInterval;
        		else thisEndLati=endLati;
    			k++;
    			Road road=new Road();
    			road.setStartLong(thisStartLong);
    			road.setStartLati(thisStartLati);
    			road.setEndLong(thisEndLong);
    			road.setEndLati(thisEndLati);
    			road.setRoadId(k);
    			road.setRoadType(1);
    			roads.add(road);
    		}
    	
    	}
    	
    	
    }
        
       
		@Override
		public void run(){
			String nowTime=(new Date().toLocaleString()).replaceAll(" ", "_").replaceAll(":", "-");//文件名
			File f=new File(pathname);
			 if(!f.exists()){
			   f.mkdirs();
			  }
			String strFile=pathname+nowTime+".txt";
			File file=new File(strFile);
			File fileParent = file.getParentFile();  
			if(!fileParent.exists()){ 
				fileParent.mkdirs();  
			}
			
			try {
				file.createNewFile();
				out = new BufferedWriter(new FileWriter(file));
			int numOfFail=0;
			out.write("roadName\t"
					+ "status\t"
					+ "direction\t"
					+ "angle\t"
					+ "speed\t"
					+ "lcodes\t"
					+ "polyline\n");
				try {
					for(Road road:roads) {
						boolean flag=this.saveResult(road);
						if(!flag) {
							numOfFail++;
							System.out.println("roadid"+road.getRoadId()+"startLoti:"+road.getStartLong()+","+road.getStartLati()+";"+"endLoti:"+road.getEndLong()+","+road.getEndLati());
						}
					}
					System.out.println("all :"+roads.size()+", failed:"+numOfFail);
					System.out.println("程序开始 现在是："+new Date().toLocaleString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				
				out.flush();
				out.close();	
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	  }
		
   	
	public boolean saveResult(Road road){
		
		if(road==null||road.getEndLati()==null||road.getEndLong()==null||road.getStartLati()==null||road.getStartLong()==null||road.getRoadType()==null) return false;
		Integer roadType=road.getRoadType();
		String url = "https://restapi.amap.com/v3/traffic/status/";
		JSONObject joResult;
		try {
		if(roadType==1) {
			url+="rectangle?rectangle="+road.getStartLong()+","+road.getStartLati()+";"+road.getEndLong()+","+road.getEndLati()+"&extensions=all&level=6&key="+MyKey;
		}else if(roadType==2) {
			url+="circle?location="+road.getStartLong()+","+road.getStartLati()+"&radius="+road.getRadius()+"&extensions=all&key="+MyKey;
		}else if(roadType==3) {
			url+="road?name="+road.getRoadName()+"&extensions=all&adcode=420100&key="+MyKey;
		}
		String result=this.getResponse(url);
	//	System.out.println(result);
		joResult=new JSONObject(result);
    	if(joResult!=null) {
    		int resultStatus=joResult.getInt("status");
    		if(resultStatus==1) {//
    			JSONObject joTrafficinfo=joResult.getJSONObject("trafficinfo");
    		//	String description=joTrafficinfo.getString("description");
    		//	JSONObject joEvaluation=joTrafficinfo.getJSONObject("evaluation");
    		//	String evadescription=joEvaluation.getString("description");
    		//	String expedite=joEvaluation.getString("expedite");
    		//	String blocked=joEvaluation.getString("blocked");
    		//	String congested=joEvaluation.getString("congested");
    		//	String unknown=joEvaluation.getString("unknown");
    		//	Integer status=joEvaluation.getInt("status");
    			JSONArray jsRoads=joTrafficinfo.getJSONArray("roads"); 
    			for(int i=0;i<jsRoads.length();i++) {
    				JSONObject JoRoad=jsRoads.getJSONObject(i);
    			    String roadName=JoRoad.getString("name");
    			    Integer roadStatus=JoRoad.getInt("status");
    			    int speed=0;
    			    String lcodes=JoRoad.getString("lcodes");
    			    String polyline=JoRoad.getString("polyline");
    			    String direction=JoRoad.getString("direction");
    			    Integer angle=JoRoad.getInt("angle");
    			    if(JoRoad.toString().contains("speed")) speed=JoRoad.getInt("speed");
    			    out.write(roadName+"\t"+roadStatus+"\t"+direction+"\t"+angle+"\t"+speed+"\t"+lcodes+"\t"+polyline+"\n"); 
    			  // 
    			  //  TrafficRoad trafficRoad=new TrafficRoad();
    			  
    			  //  trafficRoad.setName(roadName);
    			   // trafficRoad.setLcodes(lcodes);
    			   // trafficRoad.setPolyline(polyline);
    			   // trafficRoad.setStatus(roadStatus);
    			   // trafficRoad.setDirection(direction);
    			   // trafficRoad.setAngle(angle);
    			   // trafficRoad.setSpeed(speed);
    			   
    			}
    		}else return false;
    	}else return false;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	public static String getResponse(String serverUrl){
        //用JAVA发起http请求，并返回json格式的结果
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
