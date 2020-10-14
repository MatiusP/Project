package by.epamtc.protsko.rentcar.service.exception;

public class CarServiceException extends Exception {

    public CarServiceException() {
    }

    public CarServiceException(String message) {
        super(message);
    }

    public CarServiceException(Exception e) {
        super(e);
    }

    public CarServiceException(String message, Exception e) {
        super(message, e);
    }
}
