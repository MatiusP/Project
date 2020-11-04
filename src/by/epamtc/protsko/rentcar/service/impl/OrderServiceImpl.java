package by.epamtc.protsko.rentcar.service.impl;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.bean.order.*;
import by.epamtc.protsko.rentcar.dao.CarDAO;
import by.epamtc.protsko.rentcar.dao.DAOFactory;
import by.epamtc.protsko.rentcar.dao.OrderDAO;
import by.epamtc.protsko.rentcar.dao.exception.CarDAOException;
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
    private final CarDAO carDAO = factory.getCarDAO();
    private static final String ORDER_ACCEPTED_VALUE = "Accepted";
    private static final String ORDER_NOT_ACCEPTED_VALUE = "Not accepted";
    private static final String ORDER_CLOSED_VALUE = "Closed";
    private static final String ORDER_NOT_CLOSED_VALUE = "Open";

    @Override
    public boolean createOrder(OrderDTO order) throws OrderServiceException {




        return false;
    }

    @Override
    public OrderForClientAccept createOrderForClientAccept(int carId, Date startRent, Date endRent) throws OrderDAOException {
        final LocalDate orderStartRent = convertDateToLocalDate(startRent);
        final LocalDate orderEndRent = convertDateToLocalDate(endRent);
        final Period rentPeriod = orderStartRent.until(orderEndRent);
        double totalPrice;
        OrderForClientAccept orderForAccept = null;
        Car currentCar = null;
        Order order = null;

        OrderUtil.checkCorrectRentPeriod(orderStartRent, orderEndRent);
        int lengthRentPeriod = OrderUtil.getLengthRentPeriod(rentPeriod.toString());


        try {
            final List<Car> car = carDAO.findCar("id=" + carId);

            if (car.isEmpty()) {
                throw new OrderDAOException("Car not found. Please, select another car");
            }

            currentCar = car.get(0);
            if (!currentCar.isAvailableToRent()) {
                final List<Order> carOrders = orderDAO.getCarOrders(carId);

                for (Order carOrder : carOrders) {
                    order = carOrder;
                    if (!order.isOrderClosed()) {
                        if (!orderStartRent.isAfter(convertDateToLocalDate(order.getEndRent()))
                                || !orderEndRent.isBefore(convertDateToLocalDate(order.getStartRent()))) {
                            throw new OrderDAOException("Car is not available for the selected period");
                        }
                    }
                }
            }

            if (lengthRentPeriod < 4) {
                totalPrice = currentCar.getPricePerDay() * 0.95;
            }
            if (lengthRentPeriod > 3 & lengthRentPeriod < 7) {
                totalPrice = currentCar.getPricePerDay() * 0.90;
            } else {
                totalPrice = currentCar.getPricePerDay() * 0.85;
            }

            orderForAccept = new OrderForClientAccept();
            orderForAccept.setCarBrand(currentCar.getBrand());
            orderForAccept.setCarModel(currentCar.getModel());
            orderForAccept.setCarVin(currentCar.getVin());
            orderForAccept.setStartRent(startRent);
            orderForAccept.setEndRent(endRent);
            orderForAccept.setTotalPrice(totalPrice);
        } catch (CarDAOException e) {
            throw new OrderDAOException(e);
        }
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
        final boolean isOrderAccept = orderForShow.isOrderAccepted();
        final boolean isOrderClosed = orderForShow.isOrderClosed();

        OrderForShowDTO order = new OrderForShowDTO();

        order.setOrderId(orderForShow.getOrderId());
        order.setOrderDate(orderForShow.getOrderDate());
        order.setOrderStartRent(orderForShow.getOrderStartRent());
        order.setOrderEndRent(orderForShow.getOrderEndRent());
        order.setOrderTotalPrice(orderForShow.getOrderTotalPrice());
        order.setOrderCarBrand(orderForShow.getOrderCarBrand());
        order.setOrderCarModel(orderForShow.getOrderCarModel());
        order.setOrderCarVin(orderForShow.getOrderCarVin());
        order.setOrderUserId(orderForShow.getOrderUserId());
        if (isOrderAccept) {
            order.setIsOrderAccepted(ORDER_ACCEPTED_VALUE);
        } else {
            order.setIsOrderAccepted(ORDER_NOT_ACCEPTED_VALUE);
        }
        if (isOrderClosed) {
            order.setIsOrderClosed(ORDER_CLOSED_VALUE);
        } else {
            order.setIsOrderClosed(ORDER_NOT_CLOSED_VALUE);
        }

        return order;
    }

    private Order buildOrderFromOrderDTO(OrderDTO orderDTO) {
        final String isOrderAccept = orderDTO.getIsOrderAccepted();
        final String isOrderClosed = orderDTO.getIsOrderClosed();

        Order order = new Order();

        order.setId(orderDTO.getId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStartRent(orderDTO.getStartRent());
        order.setEndRent(orderDTO.getEndRent());
        order.setTotalPrice(Double.parseDouble(orderDTO.getTotalPrice()));
        order.setUserId(Integer.parseInt(orderDTO.getUserId()));
        order.setCarId(Integer.parseInt(orderDTO.getCarId()));
        if (isOrderAccept.equalsIgnoreCase(ORDER_ACCEPTED_VALUE)) {
            order.setOrderAccepted(true);
        }
        if (isOrderClosed.equalsIgnoreCase(ORDER_CLOSED_VALUE)) {
            order.setOrderClosed(true);
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

    private final LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
