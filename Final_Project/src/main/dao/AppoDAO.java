package main.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.exceptions.DAOException;
import main.model.Appo;
import main.model.Dates;
import main.model.MedStaff;
import main.model.User;

public class AppoDAO extends MainDAO {

	private final String SQL_FIND_ALL = "SELECT * FROM APPO_MED_PAT_DATE";
	private final String SQL_FIND_ALL_PATIENT_PAST_APPS_BY_NHS_NO = "SELECT * FROM APPO_MED_PAT_DATE A, DATES D, USER U, MED_STAFF M, USER U2 "
			+ " WHERE A.DATE_ID = D.DATE_ID " + " AND A.NHS_NO = U.NHS_NO " + " AND A.MED_ID = M.MED_ID "
			+ " AND M.MED_ID = U2.MED_ID " + " AND A.NHS_NO = ?" + " AND D.APPO_DATE < NOW() ";
	private final String SQL_FIND_ALL_PATIENT_REG_APPS_BY_MED_ID = "SELECT * FROM APPO_MED_PAT_DATE A, DATES D, USER U, MED_STAFF M, USER U2 "
			+ " WHERE A.DATE_ID = D.DATE_ID " + " AND A.NHS_NO = U.NHS_NO " + " AND A.MED_ID = M.MED_ID "
			+ " AND M.MED_ID = U2.MED_ID " + " AND A.MED_ID = ?" + " AND A.DIAG IS NOT NULL ";
	private final String SQL_FIND_ALL_PATIENT_NEXT_APPS_BY_MED_ID = "SELECT * FROM APPO_MED_PAT_DATE A, DATES D, USER U, MED_STAFF M, USER U2 "
			+ " WHERE A.DATE_ID = D.DATE_ID " + " AND A.NHS_NO = U.NHS_NO " + " AND A.MED_ID = M.MED_ID "
			+ " AND M.MED_ID = U2.MED_ID " + " AND A.MED_ID = ?" + " AND A.DIAG IS NULL ";
	private final String SQL_FIND_ALL_PATIENT_NEXT_APPS_BY_NHS_NO = "SELECT * FROM APPO_MED_PAT_DATE A, DATES D, USER U, MED_STAFF M, USER U2 "
			+ " WHERE A.DATE_ID = D.DATE_ID " + " AND A.NHS_NO = U.NHS_NO " + " AND A.MED_ID = M.MED_ID "
			+ " AND M.MED_ID = U2.MED_ID " + " AND A.NHS_NO = ?" + " AND D.APPO_DATE > NOW() ";
	private final String SQL_FIND_APPO = "SELECT * FROM APPO_MED_PAT_DATE A, DATES D, USER U, MED_STAFF M, USER U2 "
			+ " WHERE A.DATE_ID = D.DATE_ID " + " AND A.NHS_NO = U.NHS_NO " + " AND A.MED_ID = M.MED_ID "
			+ " AND M.MED_ID = U2.MED_ID " 
			+ " AND A.NHS_NO = ? AND A.DATE_ID = ? AND A.MED_ID = ? ";
	private final String SQL_DELETE_BY_DATE_ID_NHS_NO_MED_ID = "DELETE FROM APPO_MED_PAT_DATE WHERE DATE_ID = ? AND NHS_NO = ? AND MED_ID = ?";
	private final String SQL_DELETE_BY_NHS_NO = "DELETE FROM APPO_MED_PAT_DATE WHERE NHS_NO = ?";
	private final String SQL_DELETE_BY_MED_ID = "DELETE FROM APPO_MED_PAT_DATE WHERE MED_ID = ?";
	private final String SQL_INSERT = "INSERT INTO APPO_MED_PAT_DATE (DATE_ID, NHS_NO, MED_ID, REASON, DIAG, PRESC) VALUES (?, ?, ?, ?, ?, ?)";
	private final String SQL_FIND_MED_STAFF_WITH_LESS_APPOS = "SELECT COUNT(*) AS NO_APPOS "
			+ " FROM APPO_MED_PAT_DATE AS A, DATES AS D  "
			+ "WHERE D.APPO_DATE = ? "
			+ "AND A.DATE_ID = D.DATE_ID AND A.MED_ID = ?";
   private final String SQL_FIND_MED_STAFF_BY_DATE_ID = "SELECT M.MED_ID FROM MED_STAFF AS M  "
			+ "WHERE M.MED_CLASS = ? AND M.MED_ID NOT IN (SELECT A2.MED_ID FROM APPO_MED_PAT_DATE AS A2 WHERE A2.DATE_ID = ?);";
	
	
	public List<Appo> findAll() throws DAOException {

		List<Appo> list = new ArrayList<Appo>();
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_FIND_ALL);

			// get Appo data from database
			rs = ps.executeQuery();

			while (rs.next()) {
				Appo appo = new Appo();

				appo.getDate().setDateId(rs.getLong("DATE_ID"));
				appo.getPatient().setNhsNo(rs.getLong("NHS_NO"));
				appo.getMedStaff().setMedId(rs.getLong("MED_ID"));
				appo.setReason(rs.getString("REASON"));
				appo.setDiag(rs.getString("DIAG"));
				appo.setPresc(rs.getString("PRESC"));

				// store all data into a List
				list.add(appo);
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

	public List<Appo> findAllPatientPastAppoByNhsNo(Long nhsNo) throws DAOException {

		List<Appo> list = new ArrayList<Appo>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		int idx = 1;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_FIND_ALL_PATIENT_PAST_APPS_BY_NHS_NO);
			
			ps.setLong(idx, nhsNo);

			// get Appo data from database
			rs = ps.executeQuery();

			while (rs.next()) {
				Appo appo = new Appo();
				
				Dates date = new Dates();
				
				date.setDateId(rs.getLong("D.DATE_ID"));
				date.setAppoDate(rs.getDate("D.APPO_DATE"));
				date.setAppoTime(rs.getString("D.APPO_TIME"));
				
				User patient = new User();
				patient.setNhsNo(rs.getLong("U.NHS_NO"));
				patient.setName(rs.getString("U.NAME"));
				patient.setSurname(rs.getString("U.SURNAME"));
				patient.setAddress(rs.getString("U.ADDRESS"));
				patient.setTelephone(rs.getLong("U.TELEPHONE"));
				patient.setMobilephone(rs.getLong("U.MOBILEPHONE"));
				patient.setGender(rs.getString("U.GENDER"));
				patient.setEthnicity(rs.getString("U.ETHNICITY"));
				patient.setDob(rs.getDate("U.DOB"));
				patient.setMedId(rs.getLong("U.MED_ID"));
				patient.setCreatedDate(rs.getDate("U.CREATED_DATE"));
				patient.setModifiedDate(rs.getDate("U.MODIFIED_DATE"));
				
				MedStaff medStaff = new MedStaff();
				// Parte de User, por que medStaff es un user ampliado
				medStaff.setNhsNo(rs.getLong("U2.NHS_NO"));
				medStaff.setName(rs.getString("U2.NAME"));
				medStaff.setSurname(rs.getString("U2.SURNAME"));
				medStaff.setAddress(rs.getString("U2.ADDRESS"));
				medStaff.setTelephone(rs.getLong("U2.TELEPHONE"));
				medStaff.setMobilephone(rs.getLong("U2.MOBILEPHONE"));
				medStaff.setGender(rs.getString("U2.GENDER"));
				medStaff.setEthnicity(rs.getString("U2.ETHNICITY"));
				medStaff.setDob(rs.getDate("U2.DOB"));
				medStaff.setCreatedDate(rs.getDate("U2.CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("U2.MODIFIED_DATE"));
				
				// Parte de MedStaff					
				medStaff.setMedId(rs.getLong("M.MED_ID"));
				medStaff.setRoom(rs.getString("M.ROOM"));
				medStaff.setMedClass(rs.getString("M.MED_CLASS"));
				medStaff.setCreatedDate(rs.getDate("M.CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("M.MODIFIED_DATE"));

				appo.setDate(date);
				appo.setPatient(patient);
				appo.setMedStaff(medStaff);
								
				appo.setReason(rs.getString("A.REASON"));
				appo.setDiag(rs.getString("A.DIAG"));
				appo.setPresc(rs.getString("A.PRESC"));

				// store all data into a List
				list.add(appo);
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
	
	public List<Appo> findAllPatientRegAppoByMedId(Long medId) throws DAOException {

		List<Appo> list = new ArrayList<Appo>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		int idx = 1;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_FIND_ALL_PATIENT_REG_APPS_BY_MED_ID);
			
			ps.setLong(idx, medId);

			// get Appo data from database
			rs = ps.executeQuery();

			while (rs.next()) {
				Appo appo = new Appo();
				
				Dates date = new Dates();
				
				date.setDateId(rs.getLong("D.DATE_ID"));
				date.setAppoDate(rs.getDate("D.APPO_DATE"));
				date.setAppoTime(rs.getString("D.APPO_TIME"));
				
				User patient = new User();
				patient.setNhsNo(rs.getLong("U.NHS_NO"));
				patient.setName(rs.getString("U.NAME"));
				patient.setSurname(rs.getString("U.SURNAME"));
				patient.setAddress(rs.getString("U.ADDRESS"));
				patient.setTelephone(rs.getLong("U.TELEPHONE"));
				patient.setMobilephone(rs.getLong("U.MOBILEPHONE"));
				patient.setGender(rs.getString("U.GENDER"));
				patient.setEthnicity(rs.getString("U.ETHNICITY"));
				patient.setDob(rs.getDate("U.DOB"));
				patient.setMedId(rs.getLong("U.MED_ID"));
				patient.setCreatedDate(rs.getDate("U.CREATED_DATE"));
				patient.setModifiedDate(rs.getDate("U.MODIFIED_DATE"));
				
				MedStaff medStaff = new MedStaff();
				// Parte de User, por que medStaff es un user ampliado
				medStaff.setNhsNo(rs.getLong("U2.NHS_NO"));
				medStaff.setName(rs.getString("U2.NAME"));
				medStaff.setSurname(rs.getString("U2.SURNAME"));
				medStaff.setAddress(rs.getString("U2.ADDRESS"));
				medStaff.setTelephone(rs.getLong("U2.TELEPHONE"));
				medStaff.setMobilephone(rs.getLong("U2.MOBILEPHONE"));
				medStaff.setGender(rs.getString("U2.GENDER"));
				medStaff.setEthnicity(rs.getString("U2.ETHNICITY"));
				medStaff.setDob(rs.getDate("U2.DOB"));
				medStaff.setCreatedDate(rs.getDate("U2.CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("U2.MODIFIED_DATE"));
				
				// Parte de MedStaff					
				medStaff.setMedId(rs.getLong("M.MED_ID"));
				medStaff.setRoom(rs.getString("M.ROOM"));
				medStaff.setMedClass(rs.getString("M.MED_CLASS"));
				medStaff.setCreatedDate(rs.getDate("M.CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("M.MODIFIED_DATE"));

				appo.setDate(date);
				appo.setPatient(patient);
				appo.setMedStaff(medStaff);
								
				appo.setReason(rs.getString("A.REASON"));
				appo.setDiag(rs.getString("A.DIAG"));
				appo.setPresc(rs.getString("A.PRESC"));

				// store all data into a List
				list.add(appo);
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
	
	public List<Appo> findAllPatientNextAppoByNhsNo(Long nhsNo) throws DAOException {

		List<Appo> list = new ArrayList<Appo>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		int idx = 1;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_FIND_ALL_PATIENT_NEXT_APPS_BY_NHS_NO);
			
			ps.setLong(idx, nhsNo);

			// get Appo data from database
			rs = ps.executeQuery();

			while (rs.next()) {
				Appo appo = new Appo();
				
				Dates date = new Dates();
				
				date.setDateId(rs.getLong("D.DATE_ID"));
				date.setAppoDate(rs.getDate("D.APPO_DATE"));
				date.setAppoTime(rs.getString("D.APPO_TIME"));
				
				User patient = new User();
				patient.setNhsNo(rs.getLong("U.NHS_NO"));
				patient.setName(rs.getString("U.NAME"));
				patient.setSurname(rs.getString("U.SURNAME"));
				patient.setAddress(rs.getString("U.ADDRESS"));
				patient.setTelephone(rs.getLong("U.TELEPHONE"));
				patient.setMobilephone(rs.getLong("U.MOBILEPHONE"));
				patient.setGender(rs.getString("U.GENDER"));
				patient.setEthnicity(rs.getString("U.ETHNICITY"));
				patient.setDob(rs.getDate("U.DOB"));
				patient.setMedId(rs.getLong("U.MED_ID"));
				patient.setCreatedDate(rs.getDate("U.CREATED_DATE"));
				patient.setModifiedDate(rs.getDate("U.MODIFIED_DATE"));
				
				MedStaff medStaff = new MedStaff();
				// Parte de User, por que medStaff es un user ampliado
				medStaff.setNhsNo(rs.getLong("U2.NHS_NO"));
				medStaff.setName(rs.getString("U2.NAME"));
				medStaff.setSurname(rs.getString("U2.SURNAME"));
				medStaff.setAddress(rs.getString("U2.ADDRESS"));
				medStaff.setTelephone(rs.getLong("U2.TELEPHONE"));
				medStaff.setMobilephone(rs.getLong("U2.MOBILEPHONE"));
				medStaff.setGender(rs.getString("U2.GENDER"));
				medStaff.setEthnicity(rs.getString("U2.ETHNICITY"));
				medStaff.setDob(rs.getDate("U2.DOB"));
				medStaff.setCreatedDate(rs.getDate("U2.CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("U2.MODIFIED_DATE"));
				
				// Parte de MedStaff					
				medStaff.setMedId(rs.getLong("M.MED_ID"));
				medStaff.setRoom(rs.getString("M.ROOM"));
				medStaff.setMedClass(rs.getString("M.MED_CLASS"));
				medStaff.setCreatedDate(rs.getDate("M.CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("M.MODIFIED_DATE"));

				appo.setDate(date);
				appo.setPatient(patient);
				appo.setMedStaff(medStaff);
								
				appo.setReason(rs.getString("A.REASON"));
				appo.setDiag(rs.getString("A.DIAG"));
				appo.setPresc(rs.getString("A.PRESC"));

				// store all data into a List
				list.add(appo);
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
	
	public List<Appo> findAllPatientNextAppoByMedId(Long medId) throws DAOException {

		List<Appo> list = new ArrayList<Appo>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		int idx = 1;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_FIND_ALL_PATIENT_NEXT_APPS_BY_MED_ID);
			
			ps.setLong(idx, medId);

			// get Appo data from database
			rs = ps.executeQuery();

			while (rs.next()) {
				Appo appo = new Appo();
				
				Dates date = new Dates();
				
				date.setDateId(rs.getLong("D.DATE_ID"));
				date.setAppoDate(rs.getDate("D.APPO_DATE"));
				date.setAppoTime(rs.getString("D.APPO_TIME"));
				
				User patient = new User();
				patient.setNhsNo(rs.getLong("U.NHS_NO"));
				patient.setName(rs.getString("U.NAME"));
				patient.setSurname(rs.getString("U.SURNAME"));
				patient.setAddress(rs.getString("U.ADDRESS"));
				patient.setTelephone(rs.getLong("U.TELEPHONE"));
				patient.setMobilephone(rs.getLong("U.MOBILEPHONE"));
				patient.setGender(rs.getString("U.GENDER"));
				patient.setEthnicity(rs.getString("U.ETHNICITY"));
				patient.setDob(rs.getDate("U.DOB"));
				patient.setMedId(rs.getLong("U.MED_ID"));
				patient.setCreatedDate(rs.getDate("U.CREATED_DATE"));
				patient.setModifiedDate(rs.getDate("U.MODIFIED_DATE"));
				
				MedStaff medStaff = new MedStaff();
				// Parte de User, por que medStaff es un user ampliado
				medStaff.setNhsNo(rs.getLong("U2.NHS_NO"));
				medStaff.setName(rs.getString("U2.NAME"));
				medStaff.setSurname(rs.getString("U2.SURNAME"));
				medStaff.setAddress(rs.getString("U2.ADDRESS"));
				medStaff.setTelephone(rs.getLong("U2.TELEPHONE"));
				medStaff.setMobilephone(rs.getLong("U2.MOBILEPHONE"));
				medStaff.setGender(rs.getString("U2.GENDER"));
				medStaff.setEthnicity(rs.getString("U2.ETHNICITY"));
				medStaff.setDob(rs.getDate("U2.DOB"));
				medStaff.setCreatedDate(rs.getDate("U2.CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("U2.MODIFIED_DATE"));
				
				// Parte de MedStaff					
				medStaff.setMedId(rs.getLong("M.MED_ID"));
				medStaff.setRoom(rs.getString("M.ROOM"));
				medStaff.setMedClass(rs.getString("M.MED_CLASS"));
				medStaff.setCreatedDate(rs.getDate("M.CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("M.MODIFIED_DATE"));

				appo.setDate(date);
				appo.setPatient(patient);
				appo.setMedStaff(medStaff);
								
				appo.setReason(rs.getString("A.REASON"));
				appo.setDiag(rs.getString("A.DIAG"));
				appo.setPresc(rs.getString("A.PRESC"));

				// store all data into a List
				list.add(appo);
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

	public void delete(Appo appo) throws DAOException {

		PreparedStatement ps = null;
		int idx = 1;
		int result;
		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_DELETE_BY_DATE_ID_NHS_NO_MED_ID);

			ps.setLong(idx++, appo.getDate().getDateId());
			ps.setLong(idx++, appo.getPatient().getNhsNo());
			ps.setLong(idx++, appo.getMedStaff().getMedId());

			// get appo data from database
			result = ps.executeUpdate();
			
			if (result == 0) {
				throw new DAOException("No records found.");
			}

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
	
	/*
	 * Este método borra todos los registros en appo para un user
	 * se invocará cuando se desee eliminar un user del sistema
	 */
	public void deleteByPatient(User user) throws DAOException {

		PreparedStatement ps = null;
		int idx = 1;
		
		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_DELETE_BY_NHS_NO);
			
			ps.setLong(idx++, user.getNhsNo());			

			// get appo data from database
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
	
	/*
	 * Este método borra todos los registros en appo para un medstaff
	 * se invocará cuando se desee eliminar un medStaff del sistema
	 */
	public void deleteByMedStaff(MedStaff med) throws DAOException {

		PreparedStatement ps = null;
		int idx = 1;
		
		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_DELETE_BY_MED_ID);
			
			ps.setLong(idx++, med.getMedId());			

			// get appo data from database
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

	public int insert(Appo appo) throws DAOException {

		PreparedStatement ps = null;
		int idx = 1;
		int result = 0;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_INSERT);

			ps.setLong(idx++, appo.getDate().getDateId());
			ps.setLong(idx++, appo.getPatient().getNhsNo());
			ps.setLong(idx++, appo.getMedStaff().getMedId());
			ps.setString(idx++, appo.getReason());
			ps.setString(idx++, appo.getDiag());
			ps.setString(idx++, appo.getPresc());

			// get appo data from database
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

	
	public List<MedStaff> findAvailableMedStaffForDate(Long dateId, String medClass)  throws DAOException {

		List<MedStaff> list = new ArrayList<MedStaff>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		int idx = 1;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_FIND_MED_STAFF_BY_DATE_ID);
			
			ps.setString(idx++, medClass);
			ps.setLong(idx++, dateId);

			// get Appo data from database
			rs = ps.executeQuery();

			while (rs.next()) {
				
				
				MedStaff medStaff = new MedStaff();
				// Parte de User, por que medStaff es un user ampliado
				
				
				// Parte de MedStaff					
				medStaff.setMedId(rs.getLong("M.MED_ID"));
						

				// store all data into a List
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
	
	public int getNoApposAtThisDay(Long medId, Date appoDate)  throws DAOException {

		int docCount = 0;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int idx = 1;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_FIND_MED_STAFF_WITH_LESS_APPOS);
			
			ps.setLong(idx++, medId);
			ps.setDate(idx++, new java.sql.Date(appoDate.getTime()));

			// get Appo data from database
			rs = ps.executeQuery();

			while (rs.next()) {				
				
				docCount = rs.getInt("NO_APPOS");
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

		return docCount;
	}

	public Appo findAppo(Appo appoIds) throws DAOException {
		Appo result = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		int idx = 1;
		
		
		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_FIND_APPO);
			
			ps.setLong(idx++, appoIds.getPatient().getNhsNo());
			ps.setLong(idx++, appoIds.getDate().getDateId());
			ps.setLong(idx++, appoIds.getMedStaff().getMedId());

			// get Appo data from database
			rs = ps.executeQuery();
			

			if (rs.next()) {
				result = new Appo();
				
				Dates date = new Dates();
				date.setDateId(rs.getLong("D.DATE_ID"));
				date.setAppoDate(rs.getDate("D.APPO_DATE"));
				date.setAppoTime(rs.getString("D.APPO_TIME"));
				
				User patient = new User();
				patient.setNhsNo(rs.getLong("U.NHS_NO"));
				patient.setName(rs.getString("U.NAME"));
				patient.setSurname(rs.getString("U.SURNAME"));
				patient.setAddress(rs.getString("U.ADDRESS"));
				patient.setTelephone(rs.getLong("U.TELEPHONE"));
				patient.setMobilephone(rs.getLong("U.MOBILEPHONE"));
				patient.setGender(rs.getString("U.GENDER"));
				patient.setEthnicity(rs.getString("U.ETHNICITY"));
				patient.setDob(rs.getDate("U.DOB"));
				patient.setMedId(rs.getLong("U.MED_ID"));
				patient.setCreatedDate(rs.getDate("U.CREATED_DATE"));
				patient.setModifiedDate(rs.getDate("U.MODIFIED_DATE"));
				
				MedStaff medStaff = new MedStaff();
				// Parte de User, por que medStaff es un user ampliado
				medStaff.setNhsNo(rs.getLong("U2.NHS_NO"));
				medStaff.setName(rs.getString("U2.NAME"));
				medStaff.setSurname(rs.getString("U2.SURNAME"));
				medStaff.setAddress(rs.getString("U2.ADDRESS"));
				medStaff.setTelephone(rs.getLong("U2.TELEPHONE"));
				medStaff.setMobilephone(rs.getLong("U2.MOBILEPHONE"));
				medStaff.setGender(rs.getString("U2.GENDER"));
				medStaff.setEthnicity(rs.getString("U2.ETHNICITY"));
				medStaff.setDob(rs.getDate("U2.DOB"));
				medStaff.setCreatedDate(rs.getDate("U2.CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("U2.MODIFIED_DATE"));
				
				// Parte de MedStaff					
				medStaff.setMedId(rs.getLong("M.MED_ID"));
				medStaff.setRoom(rs.getString("M.ROOM"));
				medStaff.setMedClass(rs.getString("M.MED_CLASS"));
				medStaff.setCreatedDate(rs.getDate("M.CREATED_DATE"));
				medStaff.setModifiedDate(rs.getDate("M.MODIFIED_DATE"));

				result.setDate(date);
				result.setPatient(patient);
				result.setMedStaff(medStaff);
								
				result.setReason(rs.getString("A.REASON"));
				result.setDiag(rs.getString("A.DIAG"));
				result.setPresc(rs.getString("A.PRESC"));

				
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

		return result;
	}

	public int updateAppo(Appo appo) throws DAOException {
		
		int result = 0;
		int indx = 1;
		PreparedStatement ps = null;
		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			String sql = "UPDATE APPO_MED_PAT_DATE SET "; 
			
			if(appo.getDiag() != null && appo.getDiag() != "") {
				
				sql += "DIAG = ?, ";
			}
			if(appo.getPresc() != null && appo.getPresc() != "") {
				
				sql += "PRESC = ? ";	
			}
			
			
			sql += " WHERE DATE_ID = ? AND NHS_NO = ? AND MED_ID = ?";
			
			ps = con.prepareStatement(sql);
			
			if(appo.getDiag() != null && appo.getDiag() != "") {
                ps.setString(indx++, appo.getDiag());
			}
			if(appo.getPresc() != null && appo.getPresc() != "") {
				ps.setString(indx++, appo.getPresc());
			}
			
			
			ps.setLong(indx++, appo.getDate().getDateId());
			ps.setLong(indx++, appo.getPatient().getNhsNo());
			ps.setLong(indx++, appo.getMedStaff().getMedId());
			

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
