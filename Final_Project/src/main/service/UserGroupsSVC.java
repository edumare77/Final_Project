package main.service;
import java.util.List;

import main.dao.UserGroupsDAO;
import main.model.UserGroups;

public class UserGroupsSVC {
	
   private UserGroupsDAO userGroupsDAO;
	
	public UserGroupsSVC() {
		userGroupsDAO = new UserGroupsDAO();
	}
	
	
	public List<UserGroups> findAll() {
		
		List<UserGroups> list = userGroupsDAO.findAll();
		
		return list;
	}
	
	public void delete(UserGroups group) {
		
		userGroupsDAO.delete(group);
	}
	
	public boolean insert(UserGroups group) {
		
		if (userGroupsDAO.insert(group) == 1) {
			return true;
		} 
		
		return false;
	}



}
