package main.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import main.exceptions.DAOException;
import main.model.Auth;

public class AuthDAO extends MainDAO {

	private final String SQL_FIND_ALL = "SELECT * FROM AUTH";
	private final String SQL_DELETE_BY_USERNAME = "DELETE FROM AUTH WHERE USERNAME = ?";
	private final String SQL_DELETE_BY_NHS_NO = "DELETE FROM AUTH WHERE NHS_NO = ?";
	private final String SQL_INSERT = "INSERT INTO AUTH (USERNAME, PASSWORD, NHS_NO, USER_GROUP, CREATED_DATE, MODIFIED_DATE) VALUES (?, ?, ?, ?, NOW(), NOW())";
	private final String SQL_FIND_BY_USERNAME = "SELECT * FROM AUTH WHERE USERNAME = ?";

	public List<Auth> findAll() throws DAOException  {

		List<Auth> list = new ArrayList<Auth>();
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_FIND_ALL);

			// get Auth data from database
			rs = ps.executeQuery();

			while (rs.next()) {
				Auth auth = new Auth();

				auth.setUserName(rs.getString("USERNAME"));
				auth.setPassword(rs.getString("PASSWORD"));
				auth.setNhsNo(rs.getLong("NHS_NO"));
				auth.getUserGroup().setUserGroup(rs.getString("USER_GROUP"));
				auth.setCreatedDate(rs.getDate("CREATED_DATE"));
				auth.setModifiedDate(rs.getDate("MODIFIED_DATE"));

				// store all data into an
				list.add(auth);
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

	public void deleteByUsername(Auth auth) throws DAOException {

		PreparedStatement ps = null;
		int idx = 1;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_DELETE_BY_USERNAME);

			ps.setString(idx++, auth.getUserName());

			// get auth data from database
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
	
	public void deleteByNhsNo(Auth auth) throws DAOException {

		PreparedStatement ps = null;
		int idx = 1;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_DELETE_BY_NHS_NO);

			ps.setLong(idx++, auth.getNhsNo());

			// get auth data from database
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

	public int insert(Auth auth) throws DAOException {

		PreparedStatement ps = null;
		int idx = 1;
		int result = 0;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}

			ps = con.prepareStatement(SQL_INSERT);

			ps.setString(idx++, auth.getUserName());
			ps.setString(idx++, auth.getPassword());
			if (auth.getNhsNo() == null) {
				ps.setNull(idx++, Types.BIGINT);
			} else {
				ps.setLong(idx++, auth.getNhsNo());
			}
			
			ps.setString(idx++, auth.getUserGroup().getUserGroup());

			// get auth data from database
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
		System.out.println("auth inserted");

		return result;
	}

	public Auth findByUserName(String userName) throws DAOException {

		Auth auth = new Auth();
		// Auth emptyAuth = new Auth() ;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			// SQL_FIND_BY_USERNAME = "SELECT * FROM AUTH WHERE USERNAME = ?";
			ps = con.prepareStatement(SQL_FIND_BY_USERNAME);
			ps.setString(1, userName);

			// get Auth data from database
			rs = ps.executeQuery();

			if (rs.next()) {

				auth.setUserName(rs.getString("USERNAME"));
				auth.setPassword(rs.getString("PASSWORD"));
				auth.setNhsNo(rs.getLong("NHS_NO"));
				auth.getUserGroup().setUserGroup(rs.getString("USER_GROUP"));
				auth.setCreatedDate(rs.getDate("CREATED_DATE"));
				auth.setModifiedDate(rs.getDate("MODIFIED_DATE"));

				// store all data into the new object Auth

				System.out.println(auth.getNhsNo());

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
		if (userName.equals(auth.getUserName())) {

			return auth;

		} else {

			return null;
		}
	}

	public String updatePassword(String username, String newPass) throws DAOException {

		int i = 0;
		PreparedStatement ps = null;
		try {

			if (con == null || con.isClosed()) {
				con = getConnection();
			}
			String sql = "UPDATE AUTH SET PASSWORD = ? WHERE USERNAME = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, newPass);
			ps.setString(2, username);
			// System.out.println("hasta aqui bien");

			i = ps.executeUpdate();

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
		if (i > 0) {
			return "updated";
		} else {
			return "invalid";
		}
	}

}
