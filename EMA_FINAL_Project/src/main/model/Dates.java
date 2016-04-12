package main.model;

import java.util.Date;

public class Dates {
	private Long dateId;
	private String dateIdText;
	private Date appoDate;
	private String appoTime;
	
	public Long getDateId() {
		return dateId;
	}
	public void setDateId(Long dateId) {
		this.dateId = dateId;
	}
	public Date getAppoDate() {
		return appoDate; 
	}
	public void setAppoDate(Date appoDate) {
		this.appoDate = appoDate;
	}
	public String getAppoTime() {
		return appoTime;
	}
	public void setAppoTime(String appoTime) {
		this.appoTime = appoTime;
	}
	public String getDateIdText() {
		if (dateId != null) {
			dateIdText = dateId.toString();
		}
		return dateIdText;
	}
	public void setDateIdText(String dateIdText) {
		this.dateIdText = dateIdText;
	}

}
