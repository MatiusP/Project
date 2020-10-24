package by.epamtc.protsko.rentcar.service.exception;

public class OrderServiceException extends Exception {
    private static final long serialVersionUID = -8447589783494462751L;

    public OrderServiceException() {
    }

    public OrderServiceException(String message) {
        super(message);
    }

    public OrderServiceException(Exception e) {
        super(e);
    }

    public OrderServiceException(String message, Exception e) {
        super(message, e);
    }
}
