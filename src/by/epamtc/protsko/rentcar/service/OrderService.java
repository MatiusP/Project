package by.epamtc.protsko.rentcar.service;

import by.epamtc.protsko.rentcar.dto.OrderDTO;
import by.epamtc.protsko.rentcar.dto.OrderForClientAcceptDTO;
import by.epamtc.protsko.rentcar.dto.OrderForShowDTO;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import java.time.LocalDate;
import java.util.List;

/**
 * This interface provides methods for order data business logic
 * and providing access to orders's information.
 *
 * @author Matvey Protsko
 */

public interface OrderService {

    /**
     * Method {@code add} provides validation entered order's data from UI.
     *
     * @param order {@link OrderDTO} contains entered user's order data value.
     * @return true if entered order's data is valid and has been adding to the database.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer or
     *                               in business logic.
     */
    boolean add(OrderDTO order) throws OrderServiceException;

    /**
     * Method {@code createOrderForClientAccept} provides validation entered order's data from UI
     * and build {@link OrderForClientAcceptDTO} object for user checking and accept.
     *
     * @param carId     - id of the car which was selected by the user for rent.
     * @param startRent - start of rental period.
     * @param endRent   - end of rental period.
     * @return {@link OrderForClientAcceptDTO} object which contains user's entered rental information
     * for checking and accept.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer or
     *                               in business logic.
     */
    OrderForClientAcceptDTO createOrderForClientAccept(int carId, LocalDate startRent, LocalDate endRent) throws OrderServiceException;

    /**
     * Method {@code accept} is for accept user's rental order.
     *
     * @param orderId - id of the order we want to accept.
     * @return true if the order was successfully accepted, false -
     * if order has not been accepted.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer or
     *                               in business logic.
     */
    boolean accept(int orderId) throws OrderServiceException;

    /**
     * Method {@code close} is for close user's rental order.
     *
     * @param orderId - id of the order we want to close.
     * @return true if the order was successfully closed, false -
     * if order has not been closed.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer or
     *                               in business logic.
     */
    boolean close(int orderId) throws OrderServiceException;

    /**
     * Method {@code cancel} is for cancel user's rental order.
     *
     * @param orderId - id of the order we want to cancel.
     * @return true if the order was successfully canceled, false -
     * if order has not been canceled.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer or
     *                               in business logic.
     */
    boolean cancel(int orderId) throws OrderServiceException;

    /**
     * Method {@code findAll} provides business logic for finding
     * all orders in system.
     *
     * @return List of {@link OrderForShowDTO}objects, whose contains
     * all information about order.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer or
     *                               in business logic.
     */
    List<OrderForShowDTO> findAll() throws OrderServiceException;

    /**
     * Method {@code findByUserId} provides business logic for finding
     * an user's orders.
     *
     * @param userId - id of the user whose orders we are looking.
     * @return List of {@link OrderForShowDTO}, whose contains all user orders.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO}
     *                               layer or in business logic.
     */
    List<OrderForShowDTO> findByUserId(int userId) throws OrderServiceException;
}
