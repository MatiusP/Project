package by.epamtc.protsko.rentcar.service.exception;

public class ServiceException extends Exception {
	private static final long serialVersionUID = -1784781455174673691L;

	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Exception e) {
		super(e);
	}

	public ServiceException(String message, Exception e) {
		super(message, e);
	}

}
