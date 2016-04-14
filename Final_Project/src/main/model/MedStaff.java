package main.model;

import java.util.Date;

public class MedStaff extends User {
	
	private String room;
	private String medClass;
	private Date createdDate;
	private Date ModifiedDate;
	
	
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getMedClass() {
		return medClass;
	}
	public void setMedClass(String medClass) {
		this.medClass = medClass;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return ModifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		ModifiedDate = modifiedDate;
	}

}
