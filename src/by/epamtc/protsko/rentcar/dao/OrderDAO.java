package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.entity.order.Order;
import by.epamtc.protsko.rentcar.entity.order.OrderForShow;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;

import java.util.List;

/**
 * This interface provides methods for working with order's data.
 *
 * @author Matvey Protsko
 */

public interface OrderDAO {

    /**
     * Method {@code add} adds a new order to database.
     *
     * @param order contains entered user's order data value.
     * @return added order id.
     * @throws OrderDAOException when problems with database access occur.
     */
    int add(Order order) throws OrderDAOException;

    /**
     * Method {@code accept} change order's parameter isOrderAccepted from
     * false to true.
     * This method is for confirming the user's order.
     *
     * @param orderId - id of the order we want to accept.
     * @return true if the order was successfully accepted, false -
     * if order has not been accepted.
     * @throws OrderDAOException when problems with database access occur.
     */
    boolean accept(int orderId) throws OrderDAOException;

    /**
     * Method {@code close} change order's parameter isOrderClosed from
     * false to true.
     * This method is for closing the user's order.
     *
     * @param orderId - id of the order we want to close.
     * @return true if the order was successfully closed, false -
     * if order has not been closed.
     * @throws OrderDAOException when problems with database access occur.
     */
    boolean close(int orderId) throws OrderDAOException;

    /**
     * Method {@code cancel} change order's parameter isOrderCanceled from
     * false to true.
     * This method is for canceling the user's order.
     *
     * @param orderId - id of the order we want to cancel.
     * @return true if the order was successfully canceled, false -
     * if order has not been canceled.
     * @throws OrderDAOException when problems with database access occur.
     */
    boolean cancel(int orderId) throws OrderDAOException;

    /**
     * Method {@code findByOrderId} finds a order by order id.
     *
     * @param orderId - id of the order we want to find in the system.
     * @return Order object which id matches orderId parameter value.
     * @throws OrderDAOException when problems with database access occur.
     */
    Order findByOrderId(int orderId) throws OrderDAOException;

    /**
     * Method {@code findAll} finds all orders in database.
     *
     * @return List of ordersForShow objects, whose contains all information about order.
     * @throws OrderDAOException when problems with database access occur.
     */
    List<OrderForShow> findAll() throws OrderDAOException;

    /**
     * Method {@code findByUserId} finds a order by user id.
     *
     * @param userId - id of the user whose orders we are looking.
     * @return List of ordersForShow, whose contains all user orders.
     * @throws OrderDAOException when problems with database access occur.
     */
    List<OrderForShow> findByUserId(int userId) throws OrderDAOException;

    /**
     * Method {@code findByCarId} finds a order by car id.
     *
     * @param carId - id of the car whose orders we are looking.
     * @return List of ordersForShow, whose contains all car orders.
     * @throws OrderDAOException when problems with database access occur.
     */
    List<Order> findByCarId(int carId) throws OrderDAOException;
}
