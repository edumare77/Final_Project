package main.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.exceptions.DAOException;
import main.model.Dates;

public class DatesDAO extends MainDAO {
	
	private final String SQL_FIND_ALL = "SELECT * FROM DATES";
	private final String SQL_FIND_ALL_DATETIME_BY_DAY_AND_MEDCLASS = "SELECT * FROM DATES AS D "
			+ "WHERE D.APPO_DATE = ? "
			+ "	AND "
			+ "(SELECT COUNT(*) FROM MED_STAFF WHERE MED_CLASS = ?) > "
			+ "(SELECT COUNT(A.DATE_ID) AS NO_DATES "
			+ "	FROM APPO_MED_PAT_DATE AS A, DATES AS D2, MED_STAFF AS M2 "
			+ " WHERE D2.APPO_DATE = ? "
			+ " AND	A.DATE_ID = D.DATE_ID "
			+ " AND A.MED_ID = M2.MED_ID AND M2.MED_CLASS = ?)";
	private final String SQL_DELETE_BY_DATE_ID = "DELETE FROM DATES WHERE MED_ID = ?";
	private final String SQL_INSERT = "INSERT INTO DATES (DATE_ID, APPO_DATE, APPO_TIME) VALUES (?, ?, ?)";
	
	
	public List<Dates> findAll() throws DAOException {
		
		List<Dates> list = new ArrayList<Dates>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			
			ps = con.prepareStatement(SQL_FIND_ALL); 
	
			//get Dates data from database
			rs =  ps.executeQuery();		
		
			while(rs.next()){
				Dates dates = new Dates();
				
				dates.setDateId(rs.getLong("DATE_ID"));
				dates.setAppoDate(rs.getDate("APPO_DATE"));
				dates.setAppoTime(rs.getString("APPO_TIME"));
				
		
				//store all data into a List
				list.add(dates);
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
	
	public List<Dates> findAllDatesByDayAndMedClass(java.util.Date day, String medClass) throws DAOException {
		
		List<Dates> list = new ArrayList<Dates>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		int idx = 1;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			
			ps = con.prepareStatement(SQL_FIND_ALL_DATETIME_BY_DAY_AND_MEDCLASS); 
			
			ps.setDate(idx++, new Date(day.getTime()));
			ps.setString(idx++, medClass);
			ps.setDate(idx++, new Date(day.getTime()));
			ps.setString(idx++, medClass);
			
	
			//get Dates data from database
			rs =  ps.executeQuery();	
			
		
			while(rs.next()){
				Dates dates = new Dates();
				
				dates.setDateId(rs.getLong("DATE_ID"));
				dates.setAppoDate(rs.getDate("APPO_DATE"));
				dates.setAppoTime(rs.getString("APPO_TIME"));				
		
				//store all data into a List
				list.add(dates);
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
	
	public void delete(Dates dates) throws DAOException {
		
		PreparedStatement ps = null;
		int idx = 1;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			ps = con.prepareStatement(SQL_DELETE_BY_DATE_ID);
			
			ps.setLong(idx++, dates.getDateId());
	
			//get dates data from database
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
	
	public int insert(Dates dates) throws DAOException {
		
		PreparedStatement ps = null;
		int idx = 1;
		int result = 0;
		
		try {
		
			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			
			ps = con.prepareStatement(SQL_INSERT);
			
			ps.setLong(idx++, dates.getDateId());
			ps.setDate(idx++, (Date) dates.getAppoDate());
			ps.setString(idx++, dates.getAppoTime());
	
			//get dates data from database
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