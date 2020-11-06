package by.epamtc.protsko.rentcar.service.impl;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.bean.order.*;
import by.epamtc.protsko.rentcar.dao.DAOFactory;
import by.epamtc.protsko.rentcar.dao.OrderDAO;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;
import by.epamtc.protsko.rentcar.service.OrderService;
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
    private final OrderDAO orderDAO = factory.getOrderDAO();
    private static final String CAR_NOT_AVAILABLE_FOR_CREATE_ORDER = "The car is not available for the selected period";
    private static final String ORDER_ACCEPTED_VALUE = "Accepted";
    private static final String ORDER_NOT_ACCEPTED_VALUE = "Not accepted";
    private static final String ORDER_CLOSED_VALUE = "Closed";
    private static final String ORDER_NOT_CLOSED_VALUE = "Open";
    private static final String ORDER_CANCELED_VALUE = "Canceled";
    private static final String ORDER_NOT_CANCELED_VALUE = "Actual";

    @Override
    public boolean createOrder(OrderDTO order) throws OrderServiceException {
        final int carId = Integer.parseInt(order.getCarId());
        final LocalDate startRent = order.getStartRent();
        final LocalDate endRent = order.getEndRent();
        Order newOrder;

        try {
            Car selectedCar = OrderUtil.getSelectedCar(carId);

            if (!selectedCar.isAvailableToRent() && !OrderUtil.isOrderAvailableForCreate(carId, startRent, endRent)) {
                throw new OrderServiceException(CAR_NOT_AVAILABLE_FOR_CREATE_ORDER);
            }
            newOrder = buildOrderFromOrderDTO(order);
            return orderDAO.createOrder(newOrder);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
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
            throw new OrderServiceException(CAR_NOT_AVAILABLE_FOR_CREATE_ORDER);
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
    public boolean acceptOrder(int orderId) throws OrderServiceException {
        try {
            return orderDAO.acceptOrder(orderId);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
    }

    @Override
    public boolean closeOrder(int orderId) throws OrderServiceException {
        try {
            return orderDAO.closeOrder(orderId);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
    }

    @Override
    public boolean cancelOrder(int orderId) throws OrderServiceException {
        try {
            return orderDAO.cancelOrder(orderId);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
    }

    @Override
    public List<OrderForShowDTO> getAllOrders() throws OrderServiceException {
        final String NO_ORDERS_IN_DB_MESSAGE = "No any orders in database";
        List<OrderForShowDTO> allOrderList = new ArrayList<>();
        List<OrderForShow> allOrders;
        OrderForShowDTO order;

        try {
            allOrders = orderDAO.getAllOrders();

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
    public List<OrderForShowDTO> getUserOrders(int userId) throws OrderServiceException {
        final String NO_USER_ORDERS_MESSAGE = "User has no orders";
        List<OrderForShowDTO> userOrderList = new ArrayList<>();
        List<OrderForShow> allUserOrders;
        OrderForShowDTO order;

        try {
            allUserOrders = orderDAO.getUserOrders(userId);

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

    @Override
    public FinalRentActDTO getFinalRentAct(int orderId) throws OrderServiceException {
        FinalRentActDTO finalRentAct;

        try {
            final FinalRentAct act = orderDAO.getFinalRentAct(orderId);
            finalRentAct = buildFinalRentActDTO(act);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
        return finalRentAct;
    }

    @Override
    public boolean updateFinalRentAct(FinalRentActDTO rentAct) throws OrderServiceException {
        FinalRentAct finalRentAct = buildFinalRentAct(rentAct);

        try {
            return orderDAO.updateFinalRentAct(finalRentAct);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
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

    private FinalRentAct buildFinalRentAct(FinalRentActDTO finalRentActDTO) {

        FinalRentAct finalRentAct = new FinalRentAct();

        finalRentAct.setId(finalRentActDTO.getId());
        finalRentAct.setCostOverduePeriod(finalRentActDTO.getCostOverduePeriod());
        finalRentAct.setCostByFuel(finalRentActDTO.getCostByFuel());
        finalRentAct.setCostByMileage(finalRentActDTO.getCostByMileage());
        finalRentAct.setCostByParkingPenalty(finalRentActDTO.getCostByParkingPenalty());
        finalRentAct.setCostByPolicePenalty(finalRentActDTO.getCostByPolicePenalty());
        finalRentAct.setCostByDamagePenalty(finalRentActDTO.getCostByDamagePenalty());
        finalRentAct.setCostByOtherPenalty(finalRentActDTO.getCostByOtherPenalty());
        finalRentAct.setOrderId(finalRentActDTO.getOrderId());

        return finalRentAct;
    }

    private FinalRentActDTO buildFinalRentActDTO(FinalRentAct finalRentAct) {
        FinalRentActDTO finalAct = new FinalRentActDTO();

        finalAct.setId(finalRentAct.getId());
        finalAct.setCostOverduePeriod(finalRentAct.getCostOverduePeriod());
        finalAct.setCostByFuel(finalRentAct.getCostByFuel());
        finalAct.setCostByMileage(finalRentAct.getCostByMileage());
        finalAct.setCostByParkingPenalty(finalRentAct.getCostByParkingPenalty());
        finalAct.setCostByPolicePenalty(finalRentAct.getCostByPolicePenalty());
        finalAct.setCostByDamagePenalty(finalRentAct.getCostByDamagePenalty());
        finalAct.setCostByOtherPenalty(finalRentAct.getCostByOtherPenalty());
        finalAct.setOrderId(finalRentAct.getOrderId());

        return finalAct;
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
