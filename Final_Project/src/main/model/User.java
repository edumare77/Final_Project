package main.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
	
	private Long nhsNo;
	private String name;
	private String surname;
	private String address;
	private Long telephone;
	private Long mobilephone;
	private String gender;
	private String ethnicity;
	private Date dob;
	private String dobText;
	private Date createdDate;
	private Date ModifiedDate;	
	private Auth auth;
	private Long medId;
	
	public User() {
		
		auth = new Auth();
	}
	
	public Long getNhsNo() {
		return nhsNo;
	}
	public void setNhsNo(Long nhsNo) {
		this.nhsNo = nhsNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getTelephone() {
		return telephone;
	}
	public void setTelephone(Long telephone) {
		this.telephone = telephone;
	}
	public Long getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(Long mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public Date getDob() {
		if (dobText != null && !dobText.isEmpty()) {
			
			try {				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				dob = sdf.parse(dobText);
			} catch (ParseException e) {
				System.err.println(e.getMessage());
			}
		}
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
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
	
	public Auth getAuth() {
		return auth;
	}
	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public String getDobText() {
		if (dob != null) {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			dobText = df.format(dob);
		}
		return dobText;
	}

	public void setDobText(String dobText) {
		this.dobText = dobText;
	}

	public Long getMedId() {
		return medId;
	}

	public void setMedId(Long medId) {
		this.medId = medId;
	}
}
