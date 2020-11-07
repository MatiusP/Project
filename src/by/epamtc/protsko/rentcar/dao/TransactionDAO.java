package by.epamtc.protsko.rentcar.dao;

public interface TransactionDAO {

    boolean acceptOrderTransaction(int orderId, int carId);

    boolean closeOrderTransaction(int orderId, int carId);

    boolean cancelOrderTransaction(int orderId, int carId);
}
