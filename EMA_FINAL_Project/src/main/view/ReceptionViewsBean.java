package main.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import main.exceptions.ServiceException;
import main.model.Auth;
import main.model.MedStaff;
import main.model.User;
import main.model.UserGroups;
import main.service.AppoSVC;
import main.service.AuthSVC;
import main.service.MedStaffSVC;
import main.service.UserGroupsSVC;
import main.service.UserSVC;

/*
 * Este bean realizará todas las operaciones de las vistas correspondientes a reception
 */
public class ReceptionViewsBean {

	private UserSVC userSVC = new UserSVC();
	private MedStaffSVC medStaffSVC = new MedStaffSVC();
	private UserGroupsSVC userGroupsSVC = new UserGroupsSVC();
	private AuthSVC authSVC = new AuthSVC();
	private AppoSVC appoSVC = new AppoSVC();

	private User newPatient = null;
	private User modifyPatient = null;
	private User detailPatient = null;
	
	private MedStaff newMedStaff = null;
	private MedStaff modifyMedStaff = null;
	private MedStaff detailMedStaff = null;

	private List<User> patientList;
	private List<MedStaff> docNoList;
	private List<MedStaff> nurseNoList;

	public List<User> getPatientList() {

		try {
			patientList = userSVC.findAllPatients();
		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Could't get patient list");
		}

		return patientList;

	}

	public List<MedStaff> getDocNoList() {

		try {
			// TODO: Habrá que consultar los meds, es decir los user con MED_ID
			// y con MED_CLASS correspondiente
			docNoList = medStaffSVC.findAllDocs();
		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Could't get doctors list");
		}

		return docNoList;

	}

	public List<MedStaff> getNurseNoList() {

		try {
			// TODO: Habrá que consultar las nurse, es decir los user con MED_ID
			// y con MED_CLASS correspondiente
			nurseNoList = medStaffSVC.findAllNurses();
		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Could't get nurses list");
		}

		return nurseNoList;

	}
	
	public void deletePatient(User c) {

		try {
			
			// Primero borramos su posibles appo
			appoSVC.deleteByPatient(c);
			
			// Necesitamos una objeto Auth con el NhsNo, creamos uno y lo cargamos, ya que 
			// solo recibimos los datos de user
			Auth auth = new Auth();
			auth.setNhsNo(c.getNhsNo());
			authSVC.deleteByNhsNo(auth);

			// TODO: ¿Si borramos un paciente, borramos tambien de AUTH o q???
			userSVC.delete(c);		
			

		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Error deleting patient.");
		}

		ViewUtil.addMessage("Deleting patient Nhs_no: " + c.getNhsNo() + " ....");

	}

	public void deleteMedStaff(MedStaff medStaff) {

		try {
			
			// Primero borramos su posibles appo
			appoSVC.deleteByMedStaff(medStaff);
			
			Auth auth = new Auth();
			auth.setNhsNo(medStaff.getNhsNo());
			authSVC.deleteByNhsNo(auth);
			
			// TODO: ¿Si borramos un paciente, borramos tambien de AUTH o q???
			userSVC.delete(medStaff);

			medStaffSVC.deleteByMedId(medStaff);

		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage(
					"Error deleting " + medStaff.getMedClass() + " with id " + medStaff.getMedId() + ".");
		}

		ViewUtil.addMessage("Deleting med " + medStaff.getNhsNo() + " ....");

	}

	

	public String savePatient() {

		try {

			// Hay que insertar primero el user y despues el auth, si
			// insertarmos primero el auth fallará al no
			// existir el user con NHS_NO que tenemos como referencia (la FK de
			// Auth)
			//System.out.println(newPatient.getNhsNo());
			userSVC.insert(newPatient);
			//System.out.println(newPatient.getName());

			// Aqui falta que el auth tenga cargado el NHS_NO que lo guardamos
			// en newUser
			Auth newAuth = newPatient.getAuth();
			newAuth.setNhsNo(newPatient.getNhsNo());
			newAuth.getUserGroup().setUserGroup("patient");
			//newPatient.setAuth(newAuth);

			// Insertamos en auth
			authSVC.insert(newAuth);
			//System.out.println("insert userauthSVC");
			
           
		} catch (ServiceException e) {

			ViewUtil.addErrorMessage("Couldn't save new Patient...");
			return "/reception/patientNew";
		}
		

		ViewUtil.addMessage("User saved successfully");
		return "/reception/home";
		
	}
	
	public String saveMedStaff() {

		System.out.println("insert user");

		try {			

			// Hay que insertar primero el user y despues el auth, si
			// insertarmos primero el auth fallará al no
			// existir el user con NHS_NO que tenemos como referencia (la FK de
			// Auth)
			medStaffSVC.insert(newMedStaff);
			

			// Despues en User
			userSVC.insert(newMedStaff);

			// Aqui falta que el auth tenga cargado el NHS_NO que lo guardamos
			// en newUser
			Auth newAuth = newMedStaff.getAuth();
			newAuth.setNhsNo(newMedStaff.getNhsNo());
			newAuth.getUserGroup().setUserGroup("med_staff");

			// Insertamos en auth
			authSVC.insert(newAuth);

		} catch (ServiceException e) {

			ViewUtil.addErrorMessage("Couldn't save new MedStaff...");
			return "/reception/medStaffNew.xhtml";
		}

		ViewUtil.addMessage("MedStaff saved successfully");
		return "/reception/home";
	}	
	

	public String updatePatient() {

		System.out.println("modifyPatient");
		
         try {
        	 
        	 userSVC.updateUser(modifyPatient);
        	 
         } catch (ServiceException e) {

 			ViewUtil.addErrorMessage("Couldn't modify patient data");
 			return "/reception/patientModify";
 		}
 		

 		ViewUtil.addMessage("Patient modified successfully");
 		return "/reception/home";
	}
	
	
	
	public String updateMedStaff() {

		System.out.println("modify Med Staff");
		
        try {
			medStaffSVC.updateMedStaff(modifyMedStaff);
			userSVC.updateUser(modifyMedStaff);
       	 
        } catch (ServiceException e) {

			ViewUtil.addErrorMessage("Couldn't modify medical staff");
			return "/reception/medStaffModify";
		}
		

		ViewUtil.addMessage("Medical staff modified successfully");
		return "/reception/home";
	}

	public List<SelectItem> getAllUserGroups() {

		List<SelectItem> items = new ArrayList<SelectItem>();
		List<UserGroups> userGroupsList = userGroupsSVC.findAll();
		for (UserGroups userGroup : userGroupsList) {
			items.add(new SelectItem(userGroup.getUserGroup(), userGroup.getDescript()));
		}
		return items;
	}

	public User getNewPatient() {
		if (newPatient == null) {
			newPatient = new User();
		}
		return newPatient;
	}

	public void setNewPatient(User newUser) {
		this.newPatient = newUser;
	}

	public User getModifyPatient() {
		if (modifyPatient == null) {
			try {
				FacesContext fc = FacesContext.getCurrentInstance();
				
				String nhsNoParam = getNhsNoParam(fc);
				if (nhsNoParam == null) {
					modifyPatient = new User();
				} else {
					modifyPatient = userSVC.findPatientByNhsNo(nhsNoParam);
				}				
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		return modifyPatient;
	}

	public User getDetailPatient() {
		if (detailPatient == null) {
			try {
				FacesContext fc = FacesContext.getCurrentInstance();
				detailPatient = userSVC.findPatientByNhsNo(getNhsNoParam(fc));
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		return detailPatient;
	}

	public void setDetailPatient(User detailPatient) {
		this.detailPatient = detailPatient;
	}

	public void setModifyPatient(User modifyPatient) {
		this.modifyPatient = modifyPatient;
	}
	
	

	public MedStaff getNewMedStaff() {
		if (newMedStaff == null) {
			newMedStaff = new MedStaff();
		}
		return newMedStaff;
	}

	public void setNewMedStaff(MedStaff newMedStaff) {
		this.newMedStaff = newMedStaff;
	}

	public MedStaff getModifyMedStaff() {
		if (modifyMedStaff == null) {
			try {
				FacesContext fc = FacesContext.getCurrentInstance();
				String medIdParam = getMedIdParam(fc);
				if (medIdParam == null) {
					modifyMedStaff = new MedStaff();
				} else {
					modifyMedStaff = medStaffSVC.findMedStaffByMedId(medIdParam);
				}
				
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		return modifyMedStaff;
	}

	public void setModifyMedStaff(MedStaff modifyMedStaff) {
		this.modifyMedStaff = modifyMedStaff;
	}

	public MedStaff getDetailMedStaff() {
		if (detailMedStaff == null) {
			try {
				FacesContext fc = FacesContext.getCurrentInstance();
				detailMedStaff = medStaffSVC.findMedStaffByMedId(getMedIdParam(fc));
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		return detailMedStaff;
	}

	public void setDetailMedStaff(MedStaff detailMedStaff) {
		this.detailMedStaff = detailMedStaff;
	}

	public String getNhsNoParam(FacesContext fc) {

		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("nhsNo");

	}
	
	public String getMedIdParam(FacesContext fc) {

		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("medId");

	}

	public UserSVC getUserSVC() {
		return userSVC;
	}

	public void setUserSVC(UserSVC userSVC) {
		this.userSVC = userSVC;
	}
	
	

}
