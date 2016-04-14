package main.service;

import java.util.ArrayList;
import java.util.List;

import main.dao.AuthDAO;
import main.exceptions.DAOException;
import main.exceptions.ServiceException;
import main.model.Auth;

public class AuthSVC {
	
	private AuthDAO authDAO;
	
	public AuthSVC() {
		authDAO = new AuthDAO();
	}
	
	
	public List<Auth> findAll() throws ServiceException {
		
		List<Auth> list = new ArrayList<Auth>();
		
		try {
			list = authDAO.findAll();
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		
		
		return list;
	}
	
	public void deleteByUsername(Auth auth) throws ServiceException {
		
		try {
			authDAO.deleteByUsername(auth);
		
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	public void deleteByNhsNo(Auth auth) throws ServiceException {

		try {
			authDAO.deleteByNhsNo(auth);

		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	public boolean insert(Auth auth) throws ServiceException {
		
		try {
			if (authDAO.insert(auth) == 1) {
				System.out.println("user svc");
				return true;
			} 
		
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		
		return false;
	}
    public Auth findByUserName(String userName) throws ServiceException {
		
    	Auth newAuth = null;
    	
    	try {
    	
    		newAuth = authDAO.findByUserName(userName);
    		
	    } catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
    	
		return newAuth;
	}
    
	public String updatePassword(String username, String newPass) throws ServiceException {
		
		try {
			if (authDAO.updatePassword(username, newPass).equals("updated")) {
				
				System.out.println(newPass + "svc");
				
				return "updated";
			}
			
			return "invalid";
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
}
