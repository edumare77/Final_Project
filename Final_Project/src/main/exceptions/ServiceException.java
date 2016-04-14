package main.exceptions;

public class ServiceException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 892921076881101392L;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String error) {
		super(error);
	}
	
	public ServiceException(String error, Exception ex) {
		super(error, ex);
	}
}
