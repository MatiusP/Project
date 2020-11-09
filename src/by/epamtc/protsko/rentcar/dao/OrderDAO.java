package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.bean.order.Order;
import by.epamtc.protsko.rentcar.bean.order.OrderForShow;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;

import java.util.List;

public interface OrderDAO {

    int add(Order order) throws OrderDAOException;

    boolean accept(int orderId) throws OrderDAOException;

    boolean close(int orderId) throws OrderDAOException;

    boolean cancel(int orderId) throws OrderDAOException;

    Order findByOrderId(int orderId) throws OrderDAOException;

    List<OrderForShow> findAll() throws OrderDAOException;

    List<OrderForShow> findByUserId(int userId) throws OrderDAOException;

    List<Order> findByCarId(int carId) throws OrderDAOException;
}
