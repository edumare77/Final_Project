package main.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import main.exceptions.ServiceException;
import main.model.Appo;
import main.model.Auth;
import main.model.MedStaff;
import main.service.AppoSVC;
import main.service.MedStaffSVC;

public class MedStaffViewsBean {

	private AppoSVC appoSVC = new AppoSVC();
	private MedStaffSVC medStaffSVC = new MedStaffSVC();

	private List<Appo> regAppsList = new ArrayList<Appo>();
	private List<Appo> nextAppsList = new ArrayList<Appo>();

	private Appo modifyAppo;
	private Appo detailAppo;

	private LoginBean loginBean;
	private MedStaff loggedMedStaff = null;
	private Auth loggedUser = null;

	public MedStaffViewsBean() {

	}

	public List<Appo> getRegAppsList() {

		try {
			regAppsList = appoSVC.findAllPatientRegAppoByMedId(getLoggedMedStaff().getMedId());
		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Error getting appointments list");
		}
		return regAppsList;
	}

	public void setRegAppsList(List<Appo> regAppsList) {
		this.regAppsList = regAppsList;
	}

	public List<Appo> getNextAppsList() {

		try {
			nextAppsList = appoSVC.findAllPatientNextAppoByMedId(getLoggedMedStaff().getMedId());
		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Error getting appointments list");
		}
		return nextAppsList;
	}

	public void setNextAppsList(List<Appo> nextAppsList) {
		this.nextAppsList = nextAppsList;
	}
	
	public String updateAppo() {

		
         try {
        	 
        	 appoSVC.updateAppo(modifyAppo);
        	 
         } catch (ServiceException e) {

 			ViewUtil.addErrorMessage("Couldn't register appointment data");
 			return "/med/appoModify";
 		}
 		

 		ViewUtil.addMessage("Appointment registered successfully");
 		return "/med/home";
	}

	public Appo getModifyAppo() {
		if (modifyAppo  == null) {
			try {
				FacesContext fc = FacesContext.getCurrentInstance();
				
				String dateIdParam = getDateIdParam(fc);
				String nhsNoParam = getNhsNoParam(fc);
				Long medId = getLoggedMedStaff().getMedId();
				if (dateIdParam == null || nhsNoParam == null || medId == null) {
					modifyAppo = new Appo();
				} else {
					Appo appo = new Appo();
					appo.getDate().setDateId(Long.parseLong(dateIdParam));
					appo.getPatient().setNhsNo(Long.parseLong(nhsNoParam));
					appo.getMedStaff().setMedId(medId);
					modifyAppo = appoSVC.findAppo(appo);
				}
				
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		return modifyAppo;
	}

	public void setModifyAppo(Appo modifyAppo) {
		this.modifyAppo = modifyAppo;
	}

	public Appo getDetailAppo() {
		if (detailAppo == null) {
			try {
				FacesContext fc = FacesContext.getCurrentInstance();
				Appo appo = new Appo();
				appo.getDate().setDateId(Long.parseLong(getDateIdParam(fc)));
				appo.getPatient().setNhsNo(Long.parseLong(getNhsNoParam(fc)));
				appo.getMedStaff().setMedId(getLoggedMedStaff().getMedId());
				detailAppo = appoSVC.findAppo(appo);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		return detailAppo;
	}
	
	public String getDateIdParam(FacesContext fc) {

		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("dateId");

	}
	
	
	
	public String getNhsNoParam(FacesContext fc) {

		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("nhsNo");

	}

	public void setDetailAppo(Appo detailAppo) {
		this.detailAppo = detailAppo;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Auth getLoggedUser() {
		if (loggedUser == null) {
			loggedUser = getLoginBean().getLoggedUser();
		}
		return loggedUser;
	}

	public MedStaff getLoggedMedStaff() {
		if (loggedMedStaff == null) {
			try {
				loggedMedStaff = medStaffSVC.findMedStaffByNhsNo(getLoggedUser().getNhsNo());
			} catch (ServiceException e) {
				e.printStackTrace();
				ViewUtil.addErrorMessage("Couldnt get logeed medStaff");
			}
			
		}
		
		return loggedMedStaff;
	}

	public void setLoggedMedStaff(MedStaff loggedMedStaff) {
		this.loggedMedStaff = loggedMedStaff;
	}

}
