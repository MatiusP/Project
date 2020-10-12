package by.epamtc.protsko.rentcar.dao.exception;

public class CarDAOException extends Exception {
    private static final long serialVersionUID = -1690738948930100900L;

    public CarDAOException() {
    }

    public CarDAOException(String message) {
        super(message);
    }

    public CarDAOException(Exception e) {
        super(e);
    }

    public CarDAOException(String message, Exception e) {
        super(message, e);
    }
}
