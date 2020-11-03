package by.epamtc.protsko.rentcar.service.exception;

public class CarServiceException extends Exception {
    private static final long serialVersionUID = 6955560639302806012L;

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
