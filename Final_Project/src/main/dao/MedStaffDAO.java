package main.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.exceptions.DAOException;
import main.model.MedStaff;


public class MedStaffDAO extends MainDAO {
	
	private final String SQL_FIND_ALL = "SELECT * FROM USER U, MED_STAFF M WHERE U.MED_ID = M.MED_ID";
	private final String SQL_FIND_ALL_MEDSTAFF_OF_CLASS = "SELECT * FROM USER U, MED_STAFF M WHERE U.MED_ID = M.MED_ID AND MED_CLASS = ?";
	private final String SQL_FIND_MEDSTAFF_BY_MEDID = "SELECT * FROM USER U, MED_STAFF M WHERE U.MED_ID = M.MED_ID AND M.MED_ID = ?";
	private final String SQL_FIND_MEDSTAFF_BY_NHSNO = "SELECT * FROM USER U, MED_STAFF M WHERE U.MED_ID = M.MED_ID AND U.NHS_NO = ?";
	private final String SQL_DELETE_BY_MED_ID = "DELETE FROM MED_STAFF WHERE MED_ID = ?";
	private final String SQL_INSERT = "INSERT INTO MED_STAFF (MED_ID, ROOM, MED_CLASS, CREATED_DATE, MODIFIED_DATE) VALUES (?, ?, ?, NOW(), NOW())";
	
	
	
	public List<MedStaff> findAll() throws DAOException {
		
		List<MedStaff> list = new ArrayList<MedStaff>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			
			ps = con.prepareStatement(SQL_FIND_ALL); 
	
			//get MedStaff data from database
			rs =  ps.executeQuery();		
		
			while(rs.next()){
				MedStaff medStaff = new MedStaff();
				
				// Parte de User, por que medStaff es un user ampliado
				medStaff.setNhsNo(rs.getLong("NHS_NO"));
				medStaff.setName(rs.getString("NAME"));
				medStaff.setSurname(rs.getString("SURNAME"));
				medStaff.setAddress(rs.getString("ADDRESS"));
				medStaff.setTelephone(rs.getLong("TELEPHONE"));
				medStaff.setMobilephone(rs.getLong("MOBILEPHONE"));
				medStaff.setGender(rs.getString("GENDER"));
				medStaff.setEthnicity(rs.getString("ETHNICITY"));
				medStaff.setDob(rs.getDate("DOB"));
				medStaff.setCreatedDate(rs.getDate("CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("MODIFIED_DATE"));
				
				// Parte de MedStaff					
				medStaff.setMedId(rs.getLong("MED_ID"));
				medStaff.setRoom(rs.getString("ROOM"));
				medStaff.setMedClass(rs.getString("MED_CLASS"));
				medStaff.setCreatedDate(rs.getDate("CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("MODIFIED_DATE"));
		
				//store all data into a List
				list.add(medStaff);
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
	
	public void deleteByMedId(MedStaff medStaff) throws DAOException {
		
		PreparedStatement ps = null;
		int idx = 1;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			ps = con.prepareStatement(SQL_DELETE_BY_MED_ID);
			
			ps.setLong(idx++, medStaff.getMedId());
	
			//get medStaff data from database
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
	
	public int insert(MedStaff medStaff) throws DAOException {
		
		PreparedStatement ps = null;
		int idx = 1;
		int result = 0;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			ps = con.prepareStatement(SQL_INSERT);
			
			ps.setLong(idx++, medStaff.getMedId());
			ps.setString(idx++, medStaff.getRoom());
			ps.setString(idx++, medStaff.getMedClass());
	
			//get medStaff data from database
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

	public List<MedStaff> findAllMedStaffOfClass(String medClass) throws DAOException {
		
			int idx = 1;
			List<MedStaff> list = new ArrayList<MedStaff>();
			ResultSet rs = null;
			PreparedStatement ps = null;
			
			try {
			
				if (con == null || con.isClosed()) {
					con = getConnection();
				}
				
				
				ps = con.prepareStatement(SQL_FIND_ALL_MEDSTAFF_OF_CLASS); 
				ps.setString(idx++, medClass);
		
				//get MedStaff data from database
				rs =  ps.executeQuery();		
			
				while(rs.next()){
					MedStaff medStaff = new MedStaff();
					
					// Parte de User, por que medStaff es un user ampliado
					medStaff.setNhsNo(rs.getLong("NHS_NO"));
					medStaff.setName(rs.getString("NAME"));
					medStaff.setSurname(rs.getString("SURNAME"));
					medStaff.setAddress(rs.getString("ADDRESS"));
					medStaff.setTelephone(rs.getLong("TELEPHONE"));
					medStaff.setMobilephone(rs.getLong("MOBILEPHONE"));
					medStaff.setGender(rs.getString("GENDER"));
					medStaff.setEthnicity(rs.getString("ETHNICITY"));
					medStaff.setDob(rs.getDate("DOB"));
					medStaff.setCreatedDate(rs.getDate("CREATED_DATE"));
					medStaff.setModifiedDate(rs.getDate("MODIFIED_DATE"));
					
					// Parte de MedStaff					
					medStaff.setMedId(rs.getLong("MED_ID"));
					medStaff.setRoom(rs.getString("ROOM"));
					medStaff.setMedClass(rs.getString("MED_CLASS"));
					medStaff.setCreatedDate(rs.getDate("CREATED_DATE"));
					medStaff.setModifiedDate(rs.getDate("MODIFIED_DATE"));
					
			
					//store all data into a List
					list.add(medStaff);
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
	
	public MedStaff findMedStaffByMedId(String medId) throws DAOException {

		int idx = 1;
		MedStaff medStaff = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_FIND_MEDSTAFF_BY_MEDID);
			ps.setString(idx++, medId);

			// get MedStaff data from database
			rs = ps.executeQuery();

			while (rs.next()) {
				medStaff = new MedStaff();

				// Parte de User, por que medStaff es un user ampliado
				medStaff.setNhsNo(rs.getLong("NHS_NO"));
				medStaff.setName(rs.getString("NAME"));
				medStaff.setSurname(rs.getString("SURNAME"));
				medStaff.setAddress(rs.getString("ADDRESS"));
				medStaff.setTelephone(rs.getLong("TELEPHONE"));
				medStaff.setMobilephone(rs.getLong("MOBILEPHONE"));
				medStaff.setGender(rs.getString("GENDER"));
				medStaff.setEthnicity(rs.getString("ETHNICITY"));
				medStaff.setDob(rs.getDate("DOB"));
				medStaff.setCreatedDate(rs.getDate("CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("MODIFIED_DATE"));

				// Parte de MedStaff
				medStaff.setMedId(rs.getLong("MED_ID"));
				medStaff.setRoom(rs.getString("ROOM"));
				medStaff.setMedClass(rs.getString("MED_CLASS"));
				medStaff.setCreatedDate(rs.getDate("CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("MODIFIED_DATE"));

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
		
		return medStaff;
	}
	
	public MedStaff findMedStaffByNhsNo(Long nhsNo) throws DAOException {

		int idx = 1;
		MedStaff medStaff = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_FIND_MEDSTAFF_BY_NHSNO);
			ps.setLong(idx++, nhsNo);

			// get MedStaff data from database
			rs = ps.executeQuery();

			while (rs.next()) {
				medStaff = new MedStaff();

				// Parte de User, por que medStaff es un user ampliado
				medStaff.setNhsNo(rs.getLong("NHS_NO"));
				medStaff.setName(rs.getString("NAME"));
				medStaff.setSurname(rs.getString("SURNAME"));
				medStaff.setAddress(rs.getString("ADDRESS"));
				medStaff.setTelephone(rs.getLong("TELEPHONE"));
				medStaff.setMobilephone(rs.getLong("MOBILEPHONE"));
				medStaff.setGender(rs.getString("GENDER"));
				medStaff.setEthnicity(rs.getString("ETHNICITY"));
				medStaff.setDob(rs.getDate("DOB"));
				medStaff.setCreatedDate(rs.getDate("CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("MODIFIED_DATE"));

				// Parte de MedStaff
				medStaff.setMedId(rs.getLong("MED_ID"));
				medStaff.setRoom(rs.getString("ROOM"));
				medStaff.setMedClass(rs.getString("MED_CLASS"));
				medStaff.setCreatedDate(rs.getDate("CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("MODIFIED_DATE"));

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
		
		return medStaff;
	}
	
	public int updateMedStaff(MedStaff medStaff) throws DAOException {
		
		System.out.println("modify Med Staff msdao");
		int result = 0;
		int indx = 1;
		PreparedStatement ps = null;
		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			String sql = "UPDATE MED_STAFF SET "; 
			
			if(medStaff.getRoom() != null && medStaff.getRoom()  != "") {
				
				sql += "ROOM = ?, ";
				
			}
			
			if(medStaff.getMedClass() != null && medStaff.getMedClass()  != "") {
				
				sql += "MED_CLASS = ? ";
				
			}
			
			
			sql += "WHERE MED_ID = ?";
			
			ps = con.prepareStatement(sql);
			
			if(medStaff.getRoom() != null && medStaff.getRoom()  != "") {
                ps.setString(indx++, medStaff.getRoom());
			}
			if(medStaff.getMedClass() != null && medStaff.getMedClass()  != "") {
				ps.setString(indx++, medStaff.getMedClass());
			}
			
			ps.setLong(indx++, medStaff.getMedId());
			
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