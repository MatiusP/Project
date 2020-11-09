package by.epamtc.protsko.rentcar.service.impl;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.bean.order.*;
import by.epamtc.protsko.rentcar.dao.DAOFactory;
import by.epamtc.protsko.rentcar.dao.OrderDAO;
import by.epamtc.protsko.rentcar.dao.TransactionDAO;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;
import by.epamtc.protsko.rentcar.dto.OrderDTO;
import by.epamtc.protsko.rentcar.dto.OrderForShowDTO;
import by.epamtc.protsko.rentcar.service.FinalRentActService;
import by.epamtc.protsko.rentcar.service.OrderService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.FinalRentActServiceException;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;
import by.epamtc.protsko.rentcar.service.util.OrderUtil;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final DAOFactory factory = DAOFactory.getInstance();
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final OrderDAO orderDAO = factory.getOrderDAO();
    private final FinalRentActService finalRentActService = serviceFactory.getFinalRentActService();
    private final TransactionDAO transactionDAO = factory.getTransactionDAO();

    private static final String CAR_NOT_AVAILABLE_FOR_CREATE_ORDER_MESSAGE = "The car unavailable on specified dates.";
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

            finalRentActService.create(newOrderId);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        } catch (FinalRentActServiceException e) {
            throw new OrderServiceException(e);
        }
        return true;
    }

    @Override
    public OrderForClientAccept createOrderForClientAccept(int carId, final LocalDate startRent, final LocalDate endRent)
            throws OrderServiceException {
        final Period rentPeriod = startRent.until(endRent);
        OrderForClientAccept orderForAccept;

        OrderUtil.checkCorrectRentPeriod(startRent, endRent);
        int lengthRentPeriod = OrderUtil.getLengthRentPeriod(rentPeriod.toString()) + 1;
        Car selectedCar = OrderUtil.getSelectedCar(carId);

        if (!selectedCar.isAvailableToRent() && !OrderUtil.isOrderAvailableForCreate(carId, startRent, endRent)) {
            throw new OrderServiceException(CAR_NOT_AVAILABLE_FOR_CREATE_ORDER_MESSAGE);
        }

        double totalPrice = getTotalPrice(lengthRentPeriod, selectedCar.getPricePerDay());

        orderForAccept = new OrderForClientAccept();
        orderForAccept.setCarBrand(selectedCar.getBrand());
        orderForAccept.setCarModel(selectedCar.getModel());
        orderForAccept.setCarVin(selectedCar.getVin());
        orderForAccept.setStartRent(convertLocalDateToDate(startRent));
        orderForAccept.setEndRent(convertLocalDateToDate(endRent));
        orderForAccept.setRentPeriodLength(lengthRentPeriod);
        orderForAccept.setTotalPrice(totalPrice);

        return orderForAccept;
    }

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
                isOrderAccepted = transactionDAO.acceptOrderTransaction(orderId, carId);
            }
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
        return isOrderAccepted;
    }

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
            return transactionDAO.closeOrderTransaction(orderId, carId);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
    }

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
            return transactionDAO.cancelOrderTransaction(orderId, carId);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
    }

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
