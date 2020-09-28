package by.epamtc.protsko.rentcar.dao.exception;

public class DAOException extends Exception {
    private static final long serialVersionUID = -2301459191280741654L;

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Exception e) {
        super(e);
    }

    public DAOException(String message, Exception e) {
        super(message, e);
    }
}
