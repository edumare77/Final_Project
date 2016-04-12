package main.service;

import java.util.ArrayList;
import java.util.List;

import main.dao.UserDAO;
import main.exceptions.DAOException;
import main.exceptions.ServiceException;
import main.model.User;

public class UserSVC {
	private UserDAO userDAO;

	public UserSVC() {
		userDAO = new UserDAO();
	}

	
	
	public List<User> findAllPatients() throws ServiceException {
		List<User> list = new ArrayList<User>();
		try {
			list = userDAO.findAllPatients();
		} catch (DAOException e) {
			e.printStackTrace();
			throw (new ServiceException());
		}

		return list;
	}
	
	public User findPatientByNhsNo(String nhsNo) throws ServiceException {
		User user = new User();
		try {
			user = userDAO.findPatientByNhsNo(nhsNo);
		} catch (DAOException e) {
			e.printStackTrace();
			throw (new ServiceException());
		}

		return user;
	}


	public void delete(User group) throws ServiceException {
		try {
			
			userDAO.delete(group);
		} catch (DAOException e) {
			e.printStackTrace();
			throw (new ServiceException());
		}
	}

	public boolean insert(User newUser) throws ServiceException {

		try {
			if (userDAO.insert(newUser) == 1) {
				System.out.println("user auth svc");
				return true;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw (new ServiceException());
		}

		return false;
	}
	
	public boolean updateUser(User newUser) throws ServiceException {

		try {
			if (userDAO.updateUser(newUser) == 1) {
				System.out.println("user auth svc");
				return true;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw (new ServiceException());
		}

		return false;
	}

}
