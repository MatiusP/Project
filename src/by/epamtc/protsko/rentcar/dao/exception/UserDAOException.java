package by.epamtc.protsko.rentcar.dao.exception;

/**
 * The class {@code UserDAOException} defines an exception a user dao layer can throw
 *
 * @author Matvey Protsko
 */

public class UserDAOException extends Exception {
    private static final long serialVersionUID = -2301459191280741654L;

    public UserDAOException() {
    }

    public UserDAOException(String message) {
        super(message);
    }

    public UserDAOException(Exception e) {
        super(e);
    }

    public UserDAOException(String message, Exception e) {
        super(message, e);
    }
}
