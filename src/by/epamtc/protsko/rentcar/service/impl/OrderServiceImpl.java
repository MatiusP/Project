package by.epamtc.protsko.rentcar.service.impl;

import by.epamtc.protsko.rentcar.bean.order.FinalRentActDTO;
import by.epamtc.protsko.rentcar.bean.order.Order;
import by.epamtc.protsko.rentcar.bean.order.OrderDTO;
import by.epamtc.protsko.rentcar.service.OrderService;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    public boolean createOrder(OrderDTO order) throws OrderServiceException {
        return false;
    }

    @Override
    public boolean cancelOrder(int orderId) throws OrderServiceException {
        return false;
    }

    @Override
    public boolean acceptOrder(int orderId) throws OrderServiceException {
        return false;
    }

    @Override
    public boolean closeOrder(int orderId) throws OrderServiceException {
        return false;
    }

    @Override
    public List<OrderDTO> getAllOrders() throws OrderServiceException {
        return null;
    }

    @Override
    public List<OrderDTO> getUserOrders(int userId) throws OrderServiceException {
        return null;
    }

    @Override
    public FinalRentActDTO getFinalRentAct(int orderId) throws OrderServiceException {
        return null;
    }

    @Override
    public boolean updateFinalRentAct(FinalRentActDTO rentAct) throws OrderServiceException {
        return false;
    }

    private Order buildOrderFromOrderDTO(OrderDTO orderDTO) {
        Order order = new Order();


        return order;
    }
}
