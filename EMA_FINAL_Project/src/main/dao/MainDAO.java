package main.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MainDAO {

	private DataSource ds;
	protected Connection con;
	
	
	public MainDAO() {
		lookupDataSource();
	}
	
	private void lookupDataSource() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/gpsystem");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	protected Connection getConnection() {
		
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
}
