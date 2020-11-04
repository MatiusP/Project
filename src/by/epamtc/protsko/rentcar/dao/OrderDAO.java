package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.bean.order.FinalRentAct;
import by.epamtc.protsko.rentcar.bean.order.Order;
import by.epamtc.protsko.rentcar.bean.order.OrderForShow;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;

import java.util.List;

public interface OrderDAO {

    boolean createOrder(Order order) throws OrderDAOException;

    boolean acceptOrder(int orderId) throws OrderDAOException;

    boolean closeOrder(int orderId) throws OrderDAOException;

    List<OrderForShow> getAllOrders() throws OrderDAOException;

    List<OrderForShow> getUserOrders(int userId) throws OrderDAOException;

    List<Order> getCarOrders(int carId) throws OrderDAOException;

    FinalRentAct getFinalRentAct(int orderId) throws OrderDAOException;

    boolean updateFinalRentAct(FinalRentAct rentAct) throws OrderDAOException;
}
