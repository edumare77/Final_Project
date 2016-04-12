package main.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.model.UserGroups;

public class UserGroupsDAO extends MainDAO {
	
	private final String SQL_FIND_ALL = "SELECT * FROM USER_GROUPS";
	private final String SQL_DELETE_BY_USER_GROUP = "DELETE FROM USER_GROUPS WHERE USER_GROUP = ?";
	private final String SQL_INSERT = "INSERT INTO USER_GROUPS (USER_GROUP, DESCRIPT, CREATED_DATE, MODIFIED_DATE,) VALUES (?, ?, NOW(), NOW())";
	
	
	
			
	

	
	public List<UserGroups> findAll() {
		
		List<UserGroups> list = new ArrayList<UserGroups>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			
			ps = con.prepareStatement(SQL_FIND_ALL); 
	
			//get UserGroups data from database
			rs =  ps.executeQuery();		
		
			while(rs.next()){
				UserGroups group = new UserGroups();
		
				group.setUserGroup(rs.getString("USER_GROUP"));
				group.setDescript(rs.getString("DESCRIPT"));
				group.setCreatedDate(rs.getDate("CREATED_DATE"));
				group.setModifiedDate(rs.getDate("MODIFIED_DATE"));
		
				//store all data into a List
				list.add(group);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
            } catch (SQLException e) {
            	e.printStackTrace();
            }
	        
			
		}
		
		return list;
	}
	
	public void delete(UserGroups group) {
		
		PreparedStatement ps = null;
		int idx = 1;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			ps = con.prepareStatement(SQL_DELETE_BY_USER_GROUP);
			
			ps.setString(idx++, group.getUserGroup());
	
			//get UserGroups data from database
			ps.executeUpdate();
			
			 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}
	
	public int insert(UserGroups group) {
		
		PreparedStatement ps = null;
		int idx = 1;
		int result = 0;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			ps = con.prepareStatement(SQL_INSERT);
			
			ps.setString(idx++, group.getUserGroup());
			ps.setString(idx++, group.getDescript());
	
	
			//get UserGroups data from database
			result = ps.executeUpdate();		
		
			
			 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
		
		return result;
	}
	

}
