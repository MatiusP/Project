package by.epamtc.protsko.rentcar.service.exception;

/**
 * The class {@link CarServiceException} defines an exception a car service layer can throw
 *
 * @author Matvey Protsko
 */
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
