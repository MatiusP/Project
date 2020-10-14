package by.epamtc.protsko.rentcar.service.exception;

public class UserServiceException extends Exception {
	private static final long serialVersionUID = -1784781455174673691L;

	public UserServiceException() {
	}

	public UserServiceException(String message) {
		super(message);
	}

	public UserServiceException(Exception e) {
		super(e);
	}

	public UserServiceException(String message, Exception e) {
		super(message, e);
	}

}
