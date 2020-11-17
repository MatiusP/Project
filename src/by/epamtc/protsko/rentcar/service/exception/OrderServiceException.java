package by.epamtc.protsko.rentcar.service.exception;

/**
 * The class {@link OrderServiceException} defines an exception a order service layer can throw
 *
 * @author Matvey Protsko
 */
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
