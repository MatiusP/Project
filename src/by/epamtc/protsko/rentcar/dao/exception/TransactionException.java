package by.epamtc.protsko.rentcar.dao.exception;

public class TransactionException extends RuntimeException {
    private static final long serialVersionUID = -4075511044144634347L;

    public TransactionException() {
        super();
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(Exception e) {
        super(e);
    }

    public TransactionException(String message, Exception e) {
        super(message, e);
    }
}
