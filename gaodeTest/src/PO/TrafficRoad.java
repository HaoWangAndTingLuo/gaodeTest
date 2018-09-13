package PO;

import java.sql.Date;

public class TrafficRoad {
	private Integer tRId;
	private String name;
	private String lcodes;
	private Integer angle;
	private Integer speed;
	private String polyline;
	private Integer status;
	private String direction;
	private Integer traficInfoId;
	
	public Integer gettRId() {
		return tRId;
	}
	public void settRId(Integer tRId) {
		this.tRId = tRId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLcodes() {
		return lcodes;
	}
	public void setLcodes(String lcodes) {
		this.lcodes = lcodes;
	}
	public Integer getAngle() {
		return angle;
	}
	public void setAngle(Integer angle) {
		this.angle = angle;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public String getPolyline() {
		return polyline;
	}
	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Integer getTraficInfoId() {
		return traficInfoId;
	}
	public void setTraficInfoId(Integer traficInfoId) {
		this.traficInfoId = traficInfoId;
	}
	
	

}
