package by.epamtc.protsko.rentcar.service.impl;

import by.epamtc.protsko.rentcar.dao.*;
import by.epamtc.protsko.rentcar.dao.exception.CarDAOException;
import by.epamtc.protsko.rentcar.dao.exception.FinalActDAOException;
import by.epamtc.protsko.rentcar.dto.OrderForClientAcceptDTO;
import by.epamtc.protsko.rentcar.entity.car.Car;
import by.epamtc.protsko.rentcar.entity.order.*;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;
import by.epamtc.protsko.rentcar.dto.OrderDTO;
import by.epamtc.protsko.rentcar.dto.OrderForShowDTO;
import by.epamtc.protsko.rentcar.service.*;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;
import by.epamtc.protsko.rentcar.service.util.OrderUtil;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class implementation of {@link OrderService}. Methods execute business logic
 * for working with order layer.
 *
 * @author Matvey Protsko
 */

public class OrderServiceImpl implements OrderService {
    private static final DAOFactory daoFactory = DAOFactory.getInstance();
    private OrderDAO orderDAO = daoFactory.getOrderDAO();
    private CarDAO carDAO = daoFactory.getCarDAO();
    private FinalRentActDAO finalRentActDAO = daoFactory.getFinalRentActDAO();
    private static final String CAR_NOT_AVAILABLE_FOR_CREATE_ORDER_MESSAGE = "The car unavailable on specified dates.";
    private static final String CAR_ID_CRITERIA_NAME = "car_id=";
    private static final String NO_ORDERS_IN_DB_MESSAGE = "No any orders in database";
    private static final String NO_USER_ORDERS_MESSAGE = "User has no orders";
    private static final String ORDER_ALREADY_CANCELED_MESSAGE = "Order already canceled.";
    private static final String ORDER_ALREADY_ACCEPTED_MESSAGE = "Order already accepted";
    private static final String ORDER_ALREADY_CLOSED_MESSAGE = "Order already closed";
    private static final String ORDER_NOT_FOUND_MESSAGE = "Order not found.";
    private static final String ORDER_ACCEPTED_VALUE = "Accepted";
    private static final String ORDER_NOT_ACCEPTED_VALUE = "Not accepted";
    private static final String ORDER_CLOSED_VALUE = "Closed";
    private static final String ORDER_NOT_CLOSED_VALUE = "Open";
    private static final String ORDER_CANCELED_VALUE = "Canceled";
    private static final String ORDER_NOT_CANCELED_VALUE = "Actual";

    /**
     * Method {@code add} provides validation entered order's data from UI
     * and adding a new order.
     *
     * @param order {@link OrderDTO} contains entered user's order data value.
     * @return true if entered order's data is valid and has been adding to the database.
     * Otherwise method throw OrderServiceException.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer.
     */
    @Override
    public boolean add(OrderDTO order) throws OrderServiceException {
        final int carId = Integer.parseInt(order.getCarId());
        final LocalDate startRent = order.getStartRent();
        final LocalDate endRent = order.getEndRent();
        Order newOrder;

        try {
            Car selectedCar = OrderUtil.getSelectedCar(carId);

            if (!selectedCar.isAvailableToRent() && !OrderUtil.isOrderAvailableForCreate(carId, startRent, endRent)) {
                throw new OrderServiceException(CAR_NOT_AVAILABLE_FOR_CREATE_ORDER_MESSAGE);
            }
            newOrder = buildOrderFromOrderDTO(order);
            int newOrderId = orderDAO.add(newOrder);
            finalRentActDAO.create(newOrderId);

        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        } catch (FinalActDAOException e) {
            throw new OrderServiceException(e);
        }
        return true;
    }

    /**
     * Method {@code createOrderForClientAccept} provides validation entered order's data from UI
     * and build {@link OrderForClientAcceptDTO} object for user checking and accept.
     *
     * @param carId     - id of the car which was selected by the user for rent.
     * @param startRent - start of rental period.
     * @param endRent   - end of rental period.
     * @return {@link OrderForClientAcceptDTO} object which contains user's entered rental information
     * for checking and accept.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer or if selected
     *                               car is not available for rent for the selected period.
     */
    @Override
    public OrderForClientAcceptDTO createOrderForClientAccept(int carId, final LocalDate startRent, final LocalDate endRent)
            throws OrderServiceException {
        final Period rentPeriod = startRent.until(endRent);
        OrderForClientAcceptDTO orderForAccept;

        OrderUtil.checkCorrectRentPeriod(startRent, endRent);
        int lengthRentPeriod = OrderUtil.getLengthRentPeriod(rentPeriod.toString()) + 1;
        Car selectedCar = OrderUtil.getSelectedCar(carId);

        if (!selectedCar.isAvailableToRent() && !OrderUtil.isOrderAvailableForCreate(carId, startRent, endRent)) {
            throw new OrderServiceException(CAR_NOT_AVAILABLE_FOR_CREATE_ORDER_MESSAGE);
        }

        double totalPrice = getTotalPrice(lengthRentPeriod, selectedCar.getPricePerDay());

        orderForAccept = new OrderForClientAcceptDTO();
        orderForAccept.setCarBrand(selectedCar.getBrand());
        orderForAccept.setCarModel(selectedCar.getModel());
        orderForAccept.setCarVin(selectedCar.getVin());
        orderForAccept.setStartRent(convertLocalDateToDate(startRent));
        orderForAccept.setEndRent(convertLocalDateToDate(endRent));
        orderForAccept.setRentPeriodLength(lengthRentPeriod);
        orderForAccept.setTotalPrice(totalPrice);

        return orderForAccept;
    }

    /**
     * Method {@code accept} is for accept user's rental order.
     *
     * @param orderId - id of the order we want to accept.
     * @return true if the order was successfully accepted, false -
     * if order has not been accepted.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer or
     *                               in business logic.
     */
    @Override
    public boolean accept(int orderId) throws OrderServiceException {
        boolean isOrderAccepted = false;
        int carId;
        LocalDate startRent;
        LocalDate endRent;

        try {
            Order order = orderDAO.findByOrderId(orderId);
            if (order == null) {
                throw new OrderServiceException(ORDER_NOT_FOUND_MESSAGE);
            }
            carId = order.getCarId();
            startRent = order.getStartRent();
            endRent = order.getEndRent();

            if (order.isOrderCanceled()) {
                throw new OrderServiceException(ORDER_ALREADY_CANCELED_MESSAGE);
            }
            if (order.isOrderAccepted()) {
                throw new OrderServiceException(ORDER_ALREADY_ACCEPTED_MESSAGE);
            }
            Car selectedCar = OrderUtil.getSelectedCar(order.getCarId());

            if (!selectedCar.isAvailableToRent()) {
                if (!OrderUtil.isOrderAvailableForCreate(carId, startRent, endRent)) {
                    throw new OrderServiceException(CAR_NOT_AVAILABLE_FOR_CREATE_ORDER_MESSAGE);
                }
            }
            if (!selectedCar.isAvailableToRent()) {
                isOrderAccepted = orderDAO.accept(orderId);
            } else {
                isOrderAccepted = acceptOrderTransaction(orderId, carId);
            }
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
        return isOrderAccepted;
    }

    /**
     * Method {@code close} is for close user's rental order.
     *
     * @param orderId - id of the order we want to close.
     * @return true if the order was successfully closed, false -
     * if order has not been closed.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer or
     *                               in business logic.
     */
    @Override
    public boolean close(int orderId) throws OrderServiceException {
        int carId;

        try {
            Order order = orderDAO.findByOrderId(orderId);
            if (order == null) {
                throw new OrderServiceException(ORDER_NOT_FOUND_MESSAGE);
            }
            if (order.isOrderClosed()) {
                throw new OrderServiceException(ORDER_ALREADY_CLOSED_MESSAGE);
            }

            carId = order.getCarId();
            int carOrdersCount = OrderUtil.getCarOrdersCount(carId);

            if (carOrdersCount > 1) {
                return orderDAO.close(orderId);
            }
            return closeOrderTransaction(orderId, carId);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
    }

    /**
     * Method {@code cancel} is for cancel user's rental order.
     *
     * @param orderId - id of the order we want to cancel.
     * @return true if the order was successfully canceled, false -
     * if order has not been canceled.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer or
     *                               in business logic.
     */
    @Override
    public boolean cancel(int orderId) throws OrderServiceException {
        int carId;

        try {
            Order order = orderDAO.findByOrderId(orderId);
            if (order == null) {
                throw new OrderServiceException(ORDER_NOT_FOUND_MESSAGE);
            }
            if (order.isOrderCanceled()) {
                throw new OrderServiceException(ORDER_ALREADY_CANCELED_MESSAGE);
            }
            if (!order.isOrderAccepted()) {
                return orderDAO.cancel(orderId);
            }

            carId = order.getCarId();
            int carOrdersCount = OrderUtil.getCarOrdersCount(carId);

            if (carOrdersCount > 1) {
                return orderDAO.cancel(orderId);
            }
            return cancelOrderTransaction(orderId, carId);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
    }

    /**
     * Method {@code findAll} provides business logic for finding
     * all orders in system.
     *
     * @return List of {@link OrderForShowDTO} objects, whose contains
     * all information about order.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer or
     *                               in business logic.
     */
    @Override
    public List<OrderForShowDTO> findAll() throws OrderServiceException {
        List<OrderForShowDTO> allOrderList = new ArrayList<>();
        List<OrderForShow> allOrders;
        OrderForShowDTO order;

        try {
            allOrders = orderDAO.findAll();

            for (OrderForShow orderTmp : allOrders) {
                order = buildOrderForShowDTO(orderTmp);
                allOrderList.add(order);
            }

            if (allOrderList.isEmpty()) {
                throw new OrderServiceException(NO_ORDERS_IN_DB_MESSAGE);
            }
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
        return allOrderList;
    }

    /**
     * Method {@code findByUserId} provides business logic for finding
     * an user's orders.
     *
     * @param userId - id of the user whose orders we are looking.
     * @return List of {@link OrderForShowDTO}, whose contains all user orders.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO}
     *                               layer or in business logic.
     */
    @Override
    public List<OrderForShowDTO> findByUserId(int userId) throws OrderServiceException {
        List<OrderForShowDTO> userOrderList = new ArrayList<>();
        List<OrderForShow> allUserOrders;
        OrderForShowDTO order;

        try {
            allUserOrders = orderDAO.findByUserId(userId);

            for (OrderForShow orderTmp : allUserOrders) {
                order = buildOrderForShowDTO(orderTmp);
                userOrderList.add(order);
            }

            if (userOrderList.isEmpty()) {
                throw new OrderServiceException(NO_USER_ORDERS_MESSAGE);
            }
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
        return userOrderList;
    }

    private OrderForShowDTO buildOrderForShowDTO(OrderForShow orderForShow) {
        final boolean isOrderAccepted = orderForShow.isOrderAccepted();
        final boolean isOrderClosed = orderForShow.isOrderClosed();
        final boolean isOrderCanceled = orderForShow.isOrderCanceled();
        OrderForShowDTO order = new OrderForShowDTO();

        order.setOrderId(orderForShow.getOrderId());
        order.setOrderDate(orderForShow.getOrderDate());
        order.setOrderStartRent(orderForShow.getOrderStartRent());
        order.setOrderEndRent(orderForShow.getOrderEndRent());
        order.setOrderTotalPrice(orderForShow.getOrderTotalPrice());
        order.setOrderCarBrand(orderForShow.getOrderCarBrand());
        order.setOrderCarModel(orderForShow.getOrderCarModel());
        order.setOrderUserPassport(orderForShow.getOrderUserPassport());
        order.setOrderCarId(orderForShow.getOrderCarId());
        order.setOrderUserId(orderForShow.getOrderUserId());
        if (isOrderAccepted) {
            order.setIsOrderAccepted(ORDER_ACCEPTED_VALUE);
        } else {
            order.setIsOrderAccepted(ORDER_NOT_ACCEPTED_VALUE);
        }
        if (isOrderClosed) {
            order.setIsOrderClosed(ORDER_CLOSED_VALUE);
        } else {
            order.setIsOrderClosed(ORDER_NOT_CLOSED_VALUE);
        }
        if (isOrderCanceled) {
            order.setIsOrderCanceled(ORDER_CANCELED_VALUE);
        } else {
            order.setIsOrderCanceled(ORDER_NOT_CANCELED_VALUE);
        }
        return order;
    }

    private Order buildOrderFromOrderDTO(OrderDTO orderDTO) {
        final String isOrderAccepted = orderDTO.getIsOrderAccepted();
        final String isOrderClosed = orderDTO.getIsOrderClosed();
        final String isOrderCanceled = orderDTO.getIsOrderCanceled();

        Order order = new Order();

        order.setId(orderDTO.getId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStartRent(orderDTO.getStartRent());
        order.setEndRent(orderDTO.getEndRent());
        order.setTotalPrice(Double.parseDouble(orderDTO.getTotalPrice()));
        order.setUserId(Integer.parseInt(orderDTO.getUserId()));
        order.setCarId(Integer.parseInt(orderDTO.getCarId()));
        if (isOrderAccepted == null || isOrderAccepted.equalsIgnoreCase(ORDER_NOT_ACCEPTED_VALUE)) {
            order.setOrderAccepted(false);
        } else {
            order.setOrderAccepted(true);
        }
        if (isOrderClosed == null || isOrderClosed.equalsIgnoreCase(ORDER_NOT_CLOSED_VALUE)) {
            order.setOrderClosed(false);
        } else {
            order.setOrderClosed(true);
        }
        if (isOrderCanceled == null || isOrderCanceled.equalsIgnoreCase(ORDER_NOT_CANCELED_VALUE)) {
            order.setOrderCanceled(false);
        } else {
            order.setOrderCanceled(true);
        }
        return order;
    }

    private boolean acceptOrderTransaction(int orderId, int carId) throws OrderServiceException {
        Car car = null;
        try {
            List<Car> carList = daoFactory.getCarDAO().findBySearchCriteria(CAR_ID_CRITERIA_NAME + carId);
            car = carList.get(0);

            if (car.isAvailableToRent()) {
                car.setAvailableToRent(false);
                orderDAO.accept(orderId);
                carDAO.edit(car);
            }
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        } catch (CarDAOException e) {
            throw new OrderServiceException(e);
        }
        return true;
    }

    private boolean closeOrderTransaction(int orderId, int carId) throws OrderServiceException {
        Car car = null;
        int carOrdersCount = 0;

        try {
            List<Order> carOrders = orderDAO.findByCarId(carId);
            for (Order order : carOrders) {
                if (order.isOrderAccepted() & !order.isOrderClosed() & !order.isOrderCanceled())
                    carOrdersCount++;
            }
            if (carOrdersCount < 2) {
                orderDAO.close(orderId);
                List<Car> carList = daoFactory.getCarDAO().findBySearchCriteria(CAR_ID_CRITERIA_NAME + carId);
                car = carList.get(0);
                car.setAvailableToRent(true);
                carDAO.edit(car);
            }
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        } catch (CarDAOException e) {
            throw new OrderServiceException(e);
        }
        return true;
    }

    private boolean cancelOrderTransaction(int orderId, int carId) throws OrderServiceException {
        Car car = null;
        int carOrdersCount = 0;

        try {
            List<Order> carOrders = orderDAO.findByCarId(carId);
            for (Order order : carOrders) {
                if (order.isOrderAccepted() & !order.isOrderClosed() & !order.isOrderCanceled())
                    carOrdersCount++;
            }
            if (carOrdersCount < 2) {
                orderDAO.cancel(orderId);
                List<Car> carList = daoFactory.getCarDAO().findBySearchCriteria(CAR_ID_CRITERIA_NAME + carId);
                car = carList.get(0);
                car.setAvailableToRent(true);
                carDAO.edit(car);
            }
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        } catch (CarDAOException e) {
            throw new OrderServiceException(e);
        }
        return true;
    }


    private Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private double getTotalPrice(int lengthPeriod, double pricePerDay) {
        final double firstDiscount = 0.95;
        final double secondDiscount = 0.90;

        if (lengthPeriod > 3 & lengthPeriod < 7) {
            return pricePerDay * lengthPeriod * firstDiscount;
        }
        if (lengthPeriod > 7) {
            return pricePerDay * lengthPeriod * secondDiscount;
        }
        return pricePerDay * lengthPeriod;
    }
}
