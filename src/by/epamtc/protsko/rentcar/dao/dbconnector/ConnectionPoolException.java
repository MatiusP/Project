package by.epamtc.protsko.rentcar.dao.dbconnector;

/**
 * The class {@code ConnectionPoolException} defines an exception a connection pool can throw
 *
 * @author Matvey Protsko
 */

public class ConnectionPoolException extends RuntimeException {

    private static final long serialVersionUID = -4771111713482544208L;

    public ConnectionPoolException() {
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Exception e) {
        super(e);
    }

    public ConnectionPoolException(String message, Exception e) {
        super(message, e);
    }
}
