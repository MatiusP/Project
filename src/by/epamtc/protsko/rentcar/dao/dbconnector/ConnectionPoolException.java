package by.epamtc.protsko.rentcar.dao.dbconnector;

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
