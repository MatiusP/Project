package by.epamtc.protsko.rentcar.service;

import by.epamtc.protsko.rentcar.bean.order.FinalRentActDTO;
import by.epamtc.protsko.rentcar.bean.order.OrderDTO;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import java.util.List;

public interface OrderService {

    boolean createOrder(OrderDTO order) throws OrderServiceException;

    boolean cancelOrder(int orderId) throws OrderServiceException;

    boolean acceptOrder(int orderId) throws OrderServiceException;

    boolean closeOrder(int orderId) throws OrderServiceException;

    List<OrderDTO> getAllOrders() throws OrderServiceException;

    List<OrderDTO> getUserOrders(int userId) throws OrderServiceException;

    FinalRentActDTO getFinalRentAct(int orderId) throws OrderServiceException;

    boolean updateFinalRentAct(FinalRentActDTO rentAct) throws OrderServiceException;
}
