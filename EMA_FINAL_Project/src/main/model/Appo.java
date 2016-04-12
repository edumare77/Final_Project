package main.model;


public class Appo {
	
	private Dates date;
	private User patient;
	private MedStaff medStaff;
	private String reason;
	private String diag;
	private String presc;
	
	public Appo() {
		date = new Dates();
		patient = new User();
		medStaff = new MedStaff();
	}
	
	
	
	public Dates getDate() {
		return date;
	}
	public void setDate(Dates date) {
		this.date = date;
	}	
	
	
	public User getPatient() {
		return patient;
	}
	public void setPatient(User patient) {
		this.patient = patient;
	}
	public MedStaff getMedStaff() {
		return medStaff;
	}
	public void setMedStaff(MedStaff medStaff) {
		this.medStaff = medStaff;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDiag() {
		return diag;
	}
	public void setDiag(String diag) {
		this.diag = diag;
	}
	public String getPresc() {
		return presc;
	}
	public void setPresc(String presc) {
		this.presc = presc;
	}

}
