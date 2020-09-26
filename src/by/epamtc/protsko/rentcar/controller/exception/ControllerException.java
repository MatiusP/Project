package by.epamtc.protsko.rentcar.controller.exception;

public class ControllerException extends Exception {
	private static final long serialVersionUID = -8975099996083160471L;

	public ControllerException() {
	}

	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(Exception e) {
		super(e);
	}

	public ControllerException(String message, Exception e) {
		super(message, e);
	}
}
