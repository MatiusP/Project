package by.epamtc.protsko.rentcar.service.exception;

/**
 * The class {@link FinalRentActServiceException} defines an exception a car
 * FinalRentAct service layer can throw.
 *
 * @author Matvey Protsko
 */
public class FinalRentActServiceException extends Exception {
    private static final long serialVersionUID = -3766994513650552079L;

    public FinalRentActServiceException() {
    }

    public FinalRentActServiceException(String message) {
        super(message);
    }

    public FinalRentActServiceException(Exception e) {
        super(e);
    }

    public FinalRentActServiceException(String message, Exception e) {
        super(message, e);
    }
}
