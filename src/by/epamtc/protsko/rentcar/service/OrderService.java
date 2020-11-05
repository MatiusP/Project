package by.epamtc.protsko.rentcar.service;

import by.epamtc.protsko.rentcar.bean.order.FinalRentActDTO;
import by.epamtc.protsko.rentcar.bean.order.OrderDTO;
import by.epamtc.protsko.rentcar.bean.order.OrderForClientAccept;
import by.epamtc.protsko.rentcar.bean.order.OrderForShowDTO;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    boolean createOrder(OrderDTO order) throws OrderServiceException;

    OrderForClientAccept createOrderForClientAccept(int carId, LocalDate startRent, LocalDate endRent) throws OrderServiceException;

    boolean acceptOrder(int orderId) throws OrderServiceException;

    boolean closeOrder(int orderId) throws OrderServiceException;

    boolean cancelOrder(int orderId) throws OrderServiceException;

    List<OrderForShowDTO> getAllOrders() throws OrderServiceException;

    List<OrderForShowDTO> getUserOrders(int userId) throws OrderServiceException;

    FinalRentActDTO getFinalRentAct(int orderId) throws OrderServiceException;

    boolean updateFinalRentAct(FinalRentActDTO rentAct) throws OrderServiceException;
}
