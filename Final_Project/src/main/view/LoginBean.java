package main.view;

import java.util.ArrayList;
//import java.io.Serializable;
import java.util.List;

import main.exceptions.ServiceException;
import main.model.Auth;
import main.service.AuthSVC;

public class LoginBean {

	// private static final long serialVersionUID = 1094801825228386363L;

	private AuthSVC authSVC;

	// Almacena el usuario loggado no usar para otra cosa
	private Auth loggedUser = null;
	
	// New user es el que va a intentar logar
	private Auth newAuth = null;
	
	
	boolean showReceptionMenu = false;
	boolean showPatientMenu = false;
	boolean showMedMenu = false;

	public LoginBean() {
		authSVC = new AuthSVC();

	}

	

	public List<Auth> getAuthList() {
		List<Auth> list = new ArrayList<Auth>();
		try {
			list = authSVC.findAll();

		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Error getting auth list");
		}

		return list;
	}

	

	public String login() {

		try {
			Auth authUser = authSVC.findByUserName(newAuth.getUserName());

			if (authUser != null) {

				if (authUser.getPassword().equals(newAuth.getPassword())) {	
					
					// Si el password coincide tienemos al usuario loggado
					loggedUser = authUser;					

					if (authUser.getUserGroup().getUserGroup().equals("med_staff")) {

						showMedMenu = true;
						showPatientMenu = false;
						showReceptionMenu = false;
						return "med_staff";
					}

					if (authUser.getUserGroup().getUserGroup().equals("patient")) {
						showMedMenu = false;
						showPatientMenu = true;
						showReceptionMenu = false;
						return "patient";
					}
					if (authUser.getUserGroup().getUserGroup().equals("reception")) {
						showMedMenu = false;
						showPatientMenu = false;
						showReceptionMenu = true;
						return "reception";
					}

				} else {

					return "invalid";
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Error login user");
		}

		// System.out.println("nul, invalid");
		return "invalid";

	}

	public String updatePassword() {

		String result = null;

		try {

			if (loggedUser.getPassword() != null && loggedUser.getPassword() != "") {

				result = authSVC.updatePassword(loggedUser.getUserName(), loggedUser.getPassword());
			}
			if (result.equals("updated")) {

				System.out.println(loggedUser.getPassword());
				System.out.println(loggedUser.getUserName());
				return "updated";

			}

		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Error updating password");
		}

		return "invalid";
	}

	public Auth getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(Auth loggedUser) {
		this.loggedUser = loggedUser;
	}	



	public Auth getNewAuth() {
		if (newAuth == null) {
			newAuth = new Auth();
		}
		return newAuth;
	}



	public void setNewAuth(Auth newAuth) {
		this.newAuth = newAuth;
	}



	public boolean isShowReceptionMenu() {
		return showReceptionMenu;
	}



	public void setShowReceptionMenu(boolean showReceptionMenu) {
		this.showReceptionMenu = showReceptionMenu;
	}



	public boolean isShowPatientMenu() {
		return showPatientMenu;
	}



	public void setShowPatientMenu(boolean showPatientMenu) {
		this.showPatientMenu = showPatientMenu;
	}



	public boolean isShowMedMenu() {
		return showMedMenu;
	}



	public void setShowMedMenu(boolean showMedMenu) {
		this.showMedMenu = showMedMenu;
	}

	
}
