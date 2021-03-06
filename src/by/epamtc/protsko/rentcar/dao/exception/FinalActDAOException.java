package by.epamtc.protsko.rentcar.dao.exception;

/**
 * The class {@code FinalActDAOException} defines an exception a final rent act dao layer can throw
 *
 * @author Matvey Protsko
 */

public class FinalActDAOException extends Exception {
    private static final long serialVersionUID = 7320510735278329377L;

    public FinalActDAOException() {
    }

    public FinalActDAOException(String message) {
        super(message);
    }

    public FinalActDAOException(Exception e) {
        super(e);
    }

    public FinalActDAOException(String message, Exception e) {
        super(message, e);
    }
}
