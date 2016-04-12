package main.exceptions;

public class DAOException extends Exception {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2047456728360484607L;

	public DAOException() {
		super();
	}
	
	public DAOException(String error) {
		super(error);
	}
	
	public DAOException(String error, Exception ex) {
		super(error, ex);
	}
}
