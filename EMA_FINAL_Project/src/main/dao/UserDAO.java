package main.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import main.exceptions.DAOException;
import main.model.User;



public class UserDAO extends MainDAO {
	
	private final String SQL_FIND_PATIENTS = "SELECT * FROM USER WHERE MED_ID IS NULL";
	private final String SQL_FIND_PATIENT_BY_NHS_NO = "SELECT * FROM USER WHERE NHS_NO = ?";
	private final String SQL_DELETE_BY_NHS_NO = "DELETE FROM USER WHERE NHS_NO = ?";
	private final String SQL_INSERT = "INSERT INTO USER (NHS_NO, NAME, SURNAME, ADDRESS, TELEPHONE, "
			+ "MOBILEPHONE, GENDER, ETHNICITY, DOB, MED_ID, CREATED_DATE, MODIFIED_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
	
	
    
	
	public User findPatientByNhsNo(String nhsNo) throws DAOException {
		
		User user = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int idx = 1;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			
			ps = con.prepareStatement(SQL_FIND_PATIENT_BY_NHS_NO); 
			
			ps.setLong(idx++, Long.parseLong(nhsNo));
	
			//get User data from database
			rs =  ps.executeQuery();		
		
			while(rs.next()){
				user = new User();
				
				user.setNhsNo(rs.getLong("NHS_NO"));
				user.setName(rs.getString("NAME"));
				user.setSurname(rs.getString("SURNAME"));
				user.setAddress(rs.getString("ADDRESS"));
				user.setTelephone(rs.getLong("TELEPHONE"));
				user.setMobilephone(rs.getLong("MOBILEPHONE"));
				user.setGender(rs.getString("GENDER"));
				user.setEthnicity(rs.getString("ETHNICITY"));
				user.setDob(rs.getDate("DOB"));
				user.setMedId(rs.getLong("MED_ID"));
				user.setCreatedDate(rs.getDate("CREATED_DATE"));
				user.setModifiedDate(rs.getDate("MODIFIED_DATE"));
		
				
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
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
		
		return user;
	}
	
	public List<User> findAllPatients() throws DAOException {
		
		List<User> list = new ArrayList<User>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
						
			ps = con.prepareStatement(SQL_FIND_PATIENTS); 
	
			//get User data from database
			rs =  ps.executeQuery();		
		
			while(rs.next()){
				User user = new User();
				
				user.setNhsNo(rs.getLong("NHS_NO"));
				user.setName(rs.getString("NAME"));
				user.setSurname(rs.getString("SURNAME"));
				user.setAddress(rs.getString("ADDRESS"));
				user.setTelephone(rs.getLong("TELEPHONE"));
				user.setMobilephone(rs.getLong("MOBILEPHONE"));
				user.setGender(rs.getString("GENDER"));
				user.setEthnicity(rs.getString("ETHNICITY"));
				user.setDob(rs.getDate("DOB"));
				user.setMedId(rs.getLong("MED_ID"));
				user.setCreatedDate(rs.getDate("CREATED_DATE"));
				user.setModifiedDate(rs.getDate("MODIFIED_DATE"));
		
				//store all data into a List
				list.add(user);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
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
	
	public void delete(User user)  throws DAOException {
		
		PreparedStatement ps = null;
		int idx = 1;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			ps = con.prepareStatement(SQL_DELETE_BY_NHS_NO);
			
			ps.setLong(idx++, user.getNhsNo());
	
			//get user data from database
			ps.executeUpdate();
			
			 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
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
	
	public int insert(User user) throws DAOException {
		System.out.println(user.getNhsNo());
		
		PreparedStatement ps = null;
		int idx = 1;
		int result = 0;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			ps = con.prepareStatement(SQL_INSERT);
			
			ps.setLong(idx++, user.getNhsNo());
			ps.setString(idx++, user.getName());
			ps.setString(idx++, user.getSurname());
			ps.setString(idx++, user.getAddress());
			ps.setLong(idx++, user.getTelephone());
			ps.setLong(idx++, user.getMobilephone());
			ps.setString(idx++, user.getGender());
			ps.setString(idx++, user.getEthnicity());
			ps.setDate(idx++, new Date(user.getDob().getTime()));
			if (user.getMedId() == null) {
				ps.setNull(idx++, Types.BIGINT);
			} else {
				ps.setLong(idx++, user.getMedId());
			}
				
			//get user data from database
			result = ps.executeUpdate();	
		
			
			 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
		System.out.println("user inserted");
		
		return result;
	}
	
	public int updateUser(User user) throws DAOException {
		
		System.out.println("modify Med Staff userdao");
		int result = 0;
		int indx = 1;
		PreparedStatement ps = null;
		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			String sql = "UPDATE USER SET "; 
			
			if(user.getName() != null && user.getName() != "") {
				
				sql += "NAME = ?, ";
			}
			if(user.getSurname() != null && user.getSurname() != "") {
				
				sql += "SURNAME = ?, ";	
			}
			if(user.getAddress() != null && user.getAddress() != "") {
				sql += "ADDRESS = ?, ";
			}
			if(user.getTelephone() != null) {
				sql += "TELEPHONE = ?, ";
			}
			if(user.getMobilephone() != null) {
				sql += "MOBILEPHONE = ?, ";
			}
			if(user.getGender() != null && user.getGender() != "") {
				sql += "GENDER = ?, ";
			}
			if(user.getEthnicity() != null && user.getEthnicity() != "") {
				sql += "ETHNICITY = ?, ";
			}
			if(user.getDob() != null) {
				sql += "DOB = ? ";
			}
			
			sql += " WHERE NHS_NO = ?";
			
			ps = con.prepareStatement(sql);
			
			if(user.getName() != null && user.getName() != "") {
                ps.setString(indx++, user.getName());
			}
			if(user.getSurname() != null && user.getSurname() != "") {
				ps.setString(indx++, user.getSurname());
			}
			if(user.getAddress() != null && user.getAddress() != "") {
				ps.setString(indx++, user.getAddress());
			}
			if(user.getTelephone() != null) {
				ps.setLong(indx++, user.getTelephone());
			}
			if(user.getMobilephone() != null) {
				ps.setLong(indx++, user.getMobilephone());
			}
			if(user.getGender() != null && user.getGender() != "") {
				ps.setString(indx++, user.getGender());
			}
			if(user.getEthnicity() != null && user.getEthnicity() != "") {
				ps.setString(indx++, user.getEthnicity());
			}
			if(user.getDob() != null) {
				ps.setDate(indx++, new Date(user.getDob().getTime()));
			}
			
			ps.setLong(indx++, user.getNhsNo());
			

			result = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
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
