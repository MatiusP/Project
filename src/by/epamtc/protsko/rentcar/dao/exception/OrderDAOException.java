package by.epamtc.protsko.rentcar.dao.exception;

/**
 * The class {@code OrderDAOException} defines an exception a order dao layer can throw
 *
 * @author Matvey Protsko
 */

public class OrderDAOException extends Exception {
    private static final long serialVersionUID = -7553949608919449484L;

    public OrderDAOException() {
        super();
    }

    public OrderDAOException(String message) {
        super(message);
    }

    public OrderDAOException(Exception e) {
        super(e);
    }

    public OrderDAOException(String message, Exception e) {
        super(message, e);
    }
}
