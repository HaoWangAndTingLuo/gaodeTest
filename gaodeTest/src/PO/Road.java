package PO;

import java.sql.Date;

public class Road {
	private Integer roadId;
    private String roadName;
    private Double startLong;
    private Double startLati;
    private Double endLong;
    private Double endLati;
    private Integer roadType;
    private Integer radius;
    private Date time;
    public Integer getRoadId() {
		return roadId;
	}
	public void setRoadId(Integer roadId) {
		this.roadId = roadId;
	}
	public String getRoadName() {
		return roadName;
	}
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public Integer getRoadType() {
		return roadType;
	}
	public void setRoadType(Integer roadType) {
		this.roadType = roadType;
	}
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Double getStartLong() {
		return startLong;
	}
	public void setStartLong(Double startLong) {
		this.startLong = startLong;
	}
	public Double getStartLati() {
		return startLati;
	}
	public void setStartLati(Double startLati) {
		this.startLati = startLati;
	}
	public Double getEndLong() {
		return endLong;
	}
	public void setEndLong(Double endLong) {
		this.endLong = endLong;
	}
	public Double getEndLati() {
		return endLati;
	}
	public void setEndLati(Double endLati) {
		this.endLati = endLati;
	}
	public Integer getRadius() {
		return radius;
	}
	public void setRadius(Integer radius) {
		this.radius = radius;
	}
	@Override
	public String toString() {
		
		return "Road:{roadName:"+roadName+";startLoti:"+startLong+","+startLati+";"
				+ "engLoti:"+endLong+","+endLati+";roadType:"+roadType+";"
						+ "radius:"+(radius==null?"Пе":radius)+";time:"+(time==null?"Пе":time)+"}";
	}
    
}
