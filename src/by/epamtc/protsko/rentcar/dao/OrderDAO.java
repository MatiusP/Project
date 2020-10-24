package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.bean.order.FinalRentAct;
import by.epamtc.protsko.rentcar.bean.order.Order;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;

import java.util.List;

public interface OrderDAO {

    boolean createOrder(Order order) throws OrderDAOException;

    boolean cancelOrder(int orderId) throws OrderDAOException;

    boolean acceptOrder(int orderId) throws OrderDAOException;

    boolean closeOrder(int orderId) throws OrderDAOException;

    List<Order> getAllOrders() throws OrderDAOException;

    List<Order> getUserOrders(int userId) throws OrderDAOException;

    FinalRentAct getFinalRentAct(int orderId) throws OrderDAOException;

    boolean updateFinalRentAct(FinalRentAct rentAct) throws OrderDAOException;
}
